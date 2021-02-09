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
package proguard.classfile.kotlin;

import kotlinx.metadata.*;
import proguard.classfile.Clazz;
import proguard.classfile.VisitorAccepter;
import proguard.classfile.kotlin.visitors.*;
import proguard.util.*;

import java.util.List;


public class KotlinEffectMetadata
extends SimpleVisitorAccepter
implements VisitorAccepter
{
    public KmEffectType effectType;

    public KmEffectInvocationKind invocationKind;

    public KotlinEffectExpressionMetadata conclusionOfConditionalEffect;

    public List<KotlinEffectExpressionMetadata> constructorArguments;


    public KotlinEffectMetadata(KmEffectType           effectType,
                                KmEffectInvocationKind invocationKind)
    {
        this.effectType = effectType;
        this.invocationKind = invocationKind;
    }


    public void accept(Clazz                  clazz,
                       KotlinMetadata         kotlinMetadata,
                       KotlinFunctionMetadata kotlinFunctionMetadata,
                       KotlinContractMetadata kotlinContractMetadata,
                       KotlinEffectVisitor    kotlinEffectVisitor)
    {
        kotlinEffectVisitor.visitEffect(clazz,
                                        kotlinMetadata,
                                        kotlinFunctionMetadata,
                                        kotlinContractMetadata,
                                        this);
    }

    public void constructorArgumentAccept(Clazz                   clazz,
                                          KotlinEffectExprVisitor kotlinEffectExprVisitor)
    {
        for (KotlinEffectExpressionMetadata constructorArgument : constructorArguments)
        {
            kotlinEffectExprVisitor.visitConstructorArgExpression(clazz, this, constructorArgument);
        }
    }

    public void conclusionOfConditionalEffectAccept(Clazz                   clazz,
                                                    KotlinEffectExprVisitor kotlinEffectExprVisitor)
    {
        if (conclusionOfConditionalEffect != null)
        {
            kotlinEffectExprVisitor.visitConclusionExpression(clazz, this, conclusionOfConditionalEffect);
        }
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin contract effect";
    }
}
