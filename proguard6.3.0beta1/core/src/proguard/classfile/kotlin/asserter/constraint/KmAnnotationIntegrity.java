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
package proguard.classfile.kotlin.asserter.constraint;

import proguard.classfile.Clazz;
import proguard.classfile.kotlin.*;
import proguard.classfile.kotlin.asserter.*;
import proguard.classfile.kotlin.visitors.*;

/**
 * This class checks the assumption: All properties need a JVM signature for their getter
 */
public class KmAnnotationIntegrity
extends    SimpleConstraintChecker
implements ConstraintChecker,
           KotlinTypeVisitor,
           KotlinTypeAliasVisitor,
           KotlinTypeParameterVisitor
{
    public static KotlinMetadataConstraint constraint()
    {
        KmAnnotationIntegrity kmAnnotationIntegrity = new KmAnnotationIntegrity();

        return KotlinMetadataConstraint.makeConstraint(
            kmAnnotationIntegrity,

            new MultiKotlinMetadataVisitor(
                new AllTypeVisitor(         kmAnnotationIntegrity),
                new AllTypeAliasVisitor(    kmAnnotationIntegrity),
                new AllTypeParameterVisitor(kmAnnotationIntegrity)
            ));
    }


    // Implementations for KotlinTypeVisitor.
    @Override
    public void visitAnyType(Clazz              clazz,
                             KotlinTypeMetadata type)
    {
        AssertUtil util = new AssertUtil("Type", clazz, null, reporter);

        if (!type.annotations.isEmpty())
        {
            type.annotations.forEach(
                antn ->
                {
                    util.reportIfNullReference(antn.referencedAnnotationClass,
                                               "annotation class");
                    antn.referencedArgumentMethods.values().forEach(util.reportIfNullReference("annotation method"));
                });
        }
    }


    // Implementations for KotlinTypeAliasVisitor.
    @Override
    public void visitTypeAlias(Clazz                              clazz,
                               KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                               KotlinTypeAliasMetadata            kotlinTypeAliasMetadata)
    {
        AssertUtil util = new AssertUtil("Type alias", clazz, null, reporter);

        if (!kotlinTypeAliasMetadata.annotations.isEmpty())
        {
            kotlinTypeAliasMetadata.annotations.forEach(
                antn ->
                {
                    util.reportIfNullReference(antn.referencedAnnotationClass,
                                               "annotation class");
                    antn.referencedArgumentMethods.values().forEach(util.reportIfNullReference("annotation method"));
                });

        }
    }


    // Implementations for KotlinTypeParameterVisitor.
    @Override
    public void visitAnyTypeParameter(Clazz                       clazz,
                                      KotlinTypeParameterMetadata kotlinTypeParameterMetadata)
    {
        AssertUtil util = new AssertUtil("Type alias", clazz, null, reporter);

        if (!kotlinTypeParameterMetadata.annotations.isEmpty())
        {
            kotlinTypeParameterMetadata.annotations.forEach(
                antn ->
                {
                    util.reportIfNullReference(antn.referencedAnnotationClass,
                                               "annotation class");
                    antn.referencedArgumentMethods.values().forEach(util.reportIfNullReference("annotation method"));
                });

        }
    }
}
