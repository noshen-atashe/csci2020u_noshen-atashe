/*
 * ProGuard -- shrinking, optimization, obfuscation, and preverification
 *             of Java bytecode.
 *
 * Copyright (c) 2002-2019 Guardsquare NV
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package proguard.classfile.kotlin.visitors.filter;

import proguard.classfile.Clazz;
import proguard.classfile.kotlin.*;
import proguard.classfile.kotlin.visitors.KotlinFunctionVisitor;
import proguard.util.Predicate;

/**
 * Delegate to another {@link KotlinFunctionVisitor} if the predicate returns true.
 *
 * For example, visit only abstract functions:
 *
 * kotlinMetadata.functionsAccept(clazz,
 *     new KotlinFunctionFilter(fun -> fun.flags.isAbstract,
 *                              new MyOtherKotlinFunctionVisitor()));
 */
public class KotlinFunctionFilter
implements   KotlinFunctionVisitor
{
    private final KotlinFunctionVisitor             kotlinFunctionVisitor;
    private final Predicate<KotlinFunctionMetadata> predicate;

    public KotlinFunctionFilter(Predicate<KotlinFunctionMetadata> predicate,
                                KotlinFunctionVisitor             kotlinFunctionVisitor)
    {
        this.kotlinFunctionVisitor = kotlinFunctionVisitor;
        this.predicate             = predicate;
    }


    // Implementations for KotlinFunctionVisitor.
    @Override
    public void visitAnyFunction(Clazz                  clazz,
                                 KotlinMetadata         kotlinMetadata,
                                 KotlinFunctionMetadata kotlinFunctionMetadata)
    {}

    @Override
    public void visitFunction(Clazz                              clazz,
                              KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                              KotlinFunctionMetadata             kotlinFunctionMetadata)
    {
        if (predicate.test(kotlinFunctionMetadata))
        {
            kotlinFunctionMetadata.accept(clazz, kotlinDeclarationContainerMetadata, kotlinFunctionVisitor);
        }
    }

    @Override
    public void visitSyntheticFunction(Clazz                            clazz,
                                       KotlinSyntheticClassKindMetadata kotlinSyntheticClassKindMetadata,
                                       KotlinFunctionMetadata           kotlinFunctionMetadata)
    {
        if (predicate.test(kotlinFunctionMetadata))
        {
            kotlinFunctionMetadata.accept(clazz, kotlinSyntheticClassKindMetadata, kotlinFunctionVisitor);
        }
    }
}
