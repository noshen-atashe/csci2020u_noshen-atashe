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

//TODO how to visit abbreviated types?
public interface KotlinTypeVisitor
{
    void visitAnyType(Clazz              clazz,
                      KotlinTypeMetadata kotlinTypeMetadata);


    // Type nest.

    default void visitTypeUpperBound(Clazz              clazz,
                                     KotlinTypeMetadata boundedType,
                                     KotlinTypeMetadata upperBound)
    {
        visitAnyType(clazz, upperBound);
    }

    default void visitAbbreviation(Clazz              clazz,
                                   KotlinTypeMetadata kotlinTypeMetadata,
                                   KotlinTypeMetadata abbreviation)
    {
        visitAnyType(clazz, abbreviation);
    }

    default void visitParameterUpperBound(Clazz                       clazz,
                                          KotlinTypeParameterMetadata boundedTypeParameter,
                                          KotlinTypeMetadata          upperBound)
    {
        visitAnyType(clazz, upperBound);
    }

    default void visitTypeOfIsExpression(Clazz                          clazz,
                                         KotlinEffectExpressionMetadata kotlinEffectExprMetadata,
                                         KotlinTypeMetadata             typeOfIs)
    {
        visitAnyType(clazz, typeOfIs);
    }

    default void visitTypeArgument(Clazz              clazz,
                                   KotlinTypeMetadata kotlinTypeMetadata,
                                   KotlinTypeMetadata typeArgument)
    {
        visitAnyType(clazz, typeArgument);
    }

    default void visitStarProjection(Clazz              clazz,
                                     KotlinTypeMetadata typeWithStarArg)
    {
        // By default, ignore star projection visits.
    }

    default void visitOuterClass(Clazz              clazz,
                                 KotlinTypeMetadata innerClass,
                                 KotlinTypeMetadata outerClass)
    {
        visitAnyType(clazz, outerClass);
    }


    // Regular Kotlin class.

    default void visitSuperType(Clazz                   clazz,
                                KotlinClassKindMetadata kotlinMetadata,
                                KotlinTypeMetadata      kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitConstructorValParamType(Clazz                              clazz,
                                              KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                              KotlinConstructorMetadata          kotlinConstructorMetadata,
                                              KotlinValueParameterMetadata       kotlinValueParameterMetadata,
                                              KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitConstructorValParamVarArgType(Clazz                              clazz,
                                                    KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                                    KotlinConstructorMetadata          kotlinConstructorMetadata,
                                                    KotlinValueParameterMetadata       kotlinValueParameterMetadata,
                                                    KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }


    // Property.

    default void visitPropertyType(Clazz                             clazz,
                                  KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                  KotlinPropertyMetadata             kotlinPropertyMetadata,
                                  KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitPropertyReceiverType(Clazz                             clazz,
                                          KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                          KotlinPropertyMetadata             kotlinPropertyMetadata,
                                          KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitPropertyValParamType(Clazz                             clazz,
                                          KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                          KotlinPropertyMetadata             kotlinPropertyMetadata,
                                          KotlinValueParameterMetadata       kotlinValueParameterMetadata,
                                          KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitPropertyValParamVarArgType(Clazz                             clazz,
                                                KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                                KotlinPropertyMetadata             kotlinPropertyMetadata,
                                                KotlinValueParameterMetadata       kotlinValueParameterMetadata,
                                                KotlinTypeMetadata                 kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    // Function.

    default void visitFunctionReturnType(Clazz                 clazz,
                                        KotlinMetadata         kotlinMetadata,
                                        KotlinFunctionMetadata kotlinFunctionMetadata,
                                        KotlinTypeMetadata     kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitFunctionReceiverType(Clazz                 clazz,
                                          KotlinMetadata         kotlinMetadata,
                                          KotlinFunctionMetadata kotlinFunctionMetadata,
                                          KotlinTypeMetadata     kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitFunctionValParamType(Clazz                       clazz,
                                          KotlinMetadata               kotlinMetadata,
                                          KotlinFunctionMetadata       kotlinFunctionMetadata,
                                          KotlinValueParameterMetadata kotlinValueParameterMetadata,
                                          KotlinTypeMetadata           kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }

    default void visitFunctionValParamVarArgType(Clazz                       clazz,
                                                KotlinMetadata               kotlinMetadata,
                                                KotlinFunctionMetadata       kotlinFunctionMetadata,
                                                KotlinValueParameterMetadata kotlinValueParameterMetadata,
                                                KotlinTypeMetadata           kotlinTypeMetadata)
    {
        visitAnyType(clazz, kotlinTypeMetadata);
    }


    // Type Alias.

    default void visitAliasUnderlyingType(Clazz                             clazz,
                                         KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                         KotlinTypeAliasMetadata            kotlinTypeAliasMetadata,
                                         KotlinTypeMetadata                 underlyingType)
    {
        visitAnyType(clazz, underlyingType);
    }

    default void visitAliasExpandedType(Clazz                             clazz,
                                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                       KotlinTypeAliasMetadata            kotlinTypeAliasMetadata,
                                       KotlinTypeMetadata                 expandedType)
    {
        visitAnyType(clazz, expandedType);
    }
}
