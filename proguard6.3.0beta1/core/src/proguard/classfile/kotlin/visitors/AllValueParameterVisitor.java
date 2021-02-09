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
package proguard.classfile.kotlin.visitors;

import proguard.classfile.Clazz;
import proguard.classfile.kotlin.*;

/**
 * This KotlinMetadataVisitor visits all ValueParameters that it finds down the tree of the visit Kotlin Metadata.
 *
 * @author Tim Van Den Broecke
 */
public class AllValueParameterVisitor
implements KotlinMetadataVisitor,

           // Implementation interfaces.
           KotlinConstructorVisitor,
           KotlinPropertyVisitor,
           KotlinFunctionVisitor
{
    private final KotlinValueParameterVisitor delegate;

    public AllValueParameterVisitor(KotlinValueParameterVisitor delegate)
    {
        this.delegate = delegate;
    }


    // Implementations for KotlinMetadataVisitor.
    @Override
    public void visitAnyKotlinMetadata(Clazz clazz, KotlinMetadata kotlinMetadata) {}

    @Override
    public void visitKotlinDeclarationContainerMetadata(Clazz                              clazz,
                                                        KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata)
    {
        kotlinDeclarationContainerMetadata.functionsAccept(clazz, this);
        kotlinDeclarationContainerMetadata.accept(clazz,
                                                  new AllKotlinPropertiesVisitor(this));
    }

    @Override
    public void visitKotlinClassMetadata(Clazz clazz, KotlinClassKindMetadata kotlinClassKindMetadata)
    {
        kotlinClassKindMetadata.constructorsAccept(clazz, this);
        visitKotlinDeclarationContainerMetadata(clazz, kotlinClassKindMetadata);
    }

    @Override
    public void visitKotlinSyntheticClassMetadata(Clazz clazz,
                                                  KotlinSyntheticClassKindMetadata kotlinSyntheticClassKindMetadata)
    {
        kotlinSyntheticClassKindMetadata.functionsAccept(clazz, this);
    }


    // Implementations for KotlinConstructorVisitor.
    @Override
    public void visitConstructor(Clazz                     clazz,
                                 KotlinClassKindMetadata   kotlinClassKindMetadata,
                                 KotlinConstructorMetadata kotlinConstructorMetadata)
    {
        delegate.onNewFunctionStart();
        kotlinConstructorMetadata.valueParametersAccept(clazz, kotlinClassKindMetadata, delegate);
    }


    // Implementations for KotlinPropertyVisitor.
    @Override
    public void visitAnyProperty(Clazz                              clazz,
                                 KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                 KotlinPropertyMetadata             kotlinPropertyMetadata)
    {
        delegate.onNewFunctionStart();
        kotlinPropertyMetadata.setterParametersAccept(clazz, kotlinDeclarationContainerMetadata, delegate);
    }


    // Implementations for KotlinFunctionVisitor.
    @Override
    public void visitAnyFunction(Clazz                  clazz,
                                 KotlinMetadata         kotlinMetadata,
                                 KotlinFunctionMetadata kotlinFunctionMetadata)
    {
        delegate.onNewFunctionStart();
        kotlinFunctionMetadata.valueParametersAccept(clazz, kotlinMetadata, delegate);
    }
}
