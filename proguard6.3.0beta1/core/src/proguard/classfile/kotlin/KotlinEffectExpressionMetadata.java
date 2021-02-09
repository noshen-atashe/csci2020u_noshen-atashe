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

import proguard.classfile.Clazz;
import proguard.classfile.VisitorAccepter;
import proguard.classfile.kotlin.flags.KotlinEffectExpressionFlags;
import proguard.classfile.kotlin.visitors.*;
import proguard.util.*;
import java.util.List;


public class KotlinEffectExpressionMetadata
extends SimpleVisitorAccepter
implements VisitorAccepter
{
    public int parameterIndex = -1;

    public boolean hasConstantValue = false;
    public Object constantValue; // May be intentionally null;

    public KotlinTypeMetadata typeOfIs;

    public List<KotlinEffectExpressionMetadata> andRightHandSides;

    public List<KotlinEffectExpressionMetadata> orRightHandSides;

    public KotlinEffectExpressionFlags flags;


    public void andRightHandSideAccept(Clazz                   clazz,
                                       KotlinEffectMetadata    kotlinEffectMetadata,
                                       KotlinEffectExprVisitor kotlinEffectExprVisitor)
    {
        for (KotlinEffectExpressionMetadata rhs : andRightHandSides)
        {
            kotlinEffectExprVisitor.visitAndRHSExpression(clazz, kotlinEffectMetadata, this, rhs);
        }
    }


    public void orRightHandSideAccept(Clazz                   clazz,
                                      KotlinEffectMetadata    kotlinEffectMetadata,
                                      KotlinEffectExprVisitor kotlinEffectExprVisitor)
    {
        for (KotlinEffectExpressionMetadata rhs : andRightHandSides)
        {
            kotlinEffectExprVisitor.visitOrRHSExpression(clazz, kotlinEffectMetadata, this, rhs);
        }
    }


    public void typeOfIsAccept(Clazz clazz, KotlinTypeVisitor kotlinTypeVisitor)
    {
        if (typeOfIs != null)
        {
            kotlinTypeVisitor.visitTypeOfIsExpression(clazz, this, typeOfIs);
        }
    }


    void setMetadataFlags(int flags)
    {
        this.flags = new KotlinEffectExpressionFlags(flags);
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin contract effect";
    }

}
