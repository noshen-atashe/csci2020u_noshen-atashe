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
import proguard.classfile.*;
import proguard.classfile.kotlin.flags.KotlinTypeParameterFlags;
import proguard.classfile.kotlin.visitors.*;
import proguard.util.*;

import java.util.*;

public class KotlinTypeParameterMetadata
extends SimpleVisitorAccepter
implements VisitorAccepter
{
    public String name;

    public int id;

    public KmVariance variance;

    public List<KotlinTypeMetadata> upperBounds;

    public KotlinTypeParameterFlags flags;

    // Extensions.
    public List<KotlinMetadataAnnotation> annotations;


    public KotlinTypeParameterMetadata(int flags, String name, int id, KmVariance variance)
    {
        this.name     = name;
        this.id       = id;
        this.variance = variance;
        this.flags    = new KotlinTypeParameterFlags(flags);
    }


    public void accept(Clazz                      clazz,
                       KotlinClassKindMetadata    kotlinClassKindMetadata,
                       KotlinTypeParameterVisitor kotlinTypeParameterVisitor)
    {
        kotlinTypeParameterVisitor.visitClassTypeParameter(clazz, kotlinClassKindMetadata, this);
    }


    public void accept(Clazz                              clazz,
                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                       KotlinPropertyMetadata             kotlinPropertyMetadata,
                       KotlinTypeParameterVisitor         kotlinTypeParameterVisitor)
    {
        kotlinTypeParameterVisitor.visitPropertyTypeParameter(clazz, kotlinDeclarationContainerMetadata, kotlinPropertyMetadata, this);
    }


    public void accept(Clazz                      clazz,
                       KotlinMetadata             kotlinMetadata,
                       KotlinFunctionMetadata     kotlinFunctionMetadata,
                       KotlinTypeParameterVisitor kotlinTypeParameterVisitor)
    {
        kotlinTypeParameterVisitor.visitFunctionTypeParameter(clazz, kotlinMetadata, kotlinFunctionMetadata, this);
    }


    public void accept(Clazz                              clazz,
                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                       KotlinTypeAliasMetadata            kotlinPropertyMetadata,
                       KotlinTypeParameterVisitor         kotlinTypeParameterVisitor)
    {
        kotlinTypeParameterVisitor.visitAliasTypeParameter(clazz, kotlinDeclarationContainerMetadata, kotlinPropertyMetadata, this);
    }


    public void upperBoundsAccept(Clazz             clazz,
                                  KotlinTypeVisitor kotlinTypeVisitor)
    {
        for (KotlinTypeMetadata upperBound : upperBounds)
        {
            upperBound.accept(clazz, this, kotlinTypeVisitor);
        }
    }

    public void annotationsAccept(Clazz                   clazz,
                                  KotlinAnnotationVisitor kotlinAnnotationVisitor)
    {
        for (KotlinMetadataAnnotation annotation : annotations)
        {
            kotlinAnnotationVisitor.visitTypeParameterAnnotation(clazz, this, annotation);
        }
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin " +
               (flags.isReified ? "primary " : "") +
               "constructor";
    }
}
