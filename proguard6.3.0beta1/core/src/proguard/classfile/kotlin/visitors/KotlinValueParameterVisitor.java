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

public interface KotlinValueParameterVisitor
{
    void visitAnyValueParameter(Clazz                        clazz,
                                KotlinValueParameterMetadata kotlinValueParameterMetadata);

    default void visitConstructorValParameter(Clazz                        clazz,
                                              KotlinClassKindMetadata      kotlinClassKindMetadata,
                                              KotlinConstructorMetadata    kotlinConstructorMetadata,
                                              KotlinValueParameterMetadata kotlinValueParameterMetadata)
    {
        visitAnyValueParameter(clazz, kotlinValueParameterMetadata);
    }

    default void visitFunctionValParameter(Clazz                        clazz,
                                           KotlinMetadata               kotlinMetadata,
                                           KotlinFunctionMetadata       kotlinFunctionMetadata,
                                           KotlinValueParameterMetadata kotlinValueParameterMetadata)
    {
        visitAnyValueParameter(clazz, kotlinValueParameterMetadata);
    }

    /**
     * Visit a value parameter of the property setter, if it has one.
     */
    default void visitPropertyValParameter(Clazz                              clazz,
                                           KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                           KotlinPropertyMetadata             kotlinPropertyMetadata,
                                           KotlinValueParameterMetadata       kotlinValueParameterMetadata)
    {
        visitAnyValueParameter(clazz, kotlinValueParameterMetadata);
    }

    default void onNewFunctionStart() {}
}
