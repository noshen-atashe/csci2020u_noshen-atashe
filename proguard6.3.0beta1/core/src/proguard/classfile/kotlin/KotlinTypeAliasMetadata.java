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

import proguard.classfile.*;
import proguard.classfile.kotlin.flags.*;
import proguard.classfile.kotlin.visitors.*;
import proguard.util.*;

import java.util.*;

public class KotlinTypeAliasMetadata
extends SimpleVisitorAccepter
implements VisitorAccepter
{
    public String name;

    public KotlinTypeAliasFlags flags;

    public List<KotlinTypeParameterMetadata> typeParameters;

    // Right-hand side of alias declaration.
    public KotlinTypeMetadata underlyingType;

    // Core type.
    public KotlinTypeMetadata expandedType;

    public KotlinVersionRequirementMetadata versionRequirement;

    // The container where the alias is declared.
    public KotlinDeclarationContainerMetadata referencedDeclarationContainer;

    public List<KotlinMetadataAnnotation> annotations;


    public KotlinTypeAliasMetadata(int flags, String name)
    {
        this.name = name;
        this.flags = new KotlinTypeAliasFlags(flags);
    }


    public void accept(Clazz                              clazz,
                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                       KotlinTypeAliasVisitor             kotlinTypeAliasVisitor)
    {
        kotlinTypeAliasVisitor.visitTypeAlias(clazz,
                                              kotlinDeclarationContainerMetadata,
                                              this);
    }


    public void typeParametersAccept(Clazz                              clazz,
                                     KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                     KotlinTypeParameterVisitor         kotlinTypeParameterVisitor)
    {
        for (KotlinTypeParameterMetadata typeParameter : typeParameters)
        {
            typeParameter.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinTypeParameterVisitor);
        }
    }


    public void underlyingTypeAccept(Clazz                              clazz,
                                     KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                     KotlinTypeVisitor                  kotlinTypeVisitor)
    {

            //TODO unusual?
//            underlyingType.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinTypeVisitor);
            kotlinTypeVisitor.visitAliasUnderlyingType(clazz, kotlinDeclarationContainerMetadata, this, underlyingType);
    }


    public void expandedTypeAccept(Clazz                              clazz,
                                   KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                   KotlinTypeVisitor                  kotlinTypeVisitor)
    {
            //TODO unusual?
//            underlyingType.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinTypeVisitor);
            kotlinTypeVisitor.visitAliasExpandedType(clazz, kotlinDeclarationContainerMetadata, this, expandedType);
    }


    public void versionRequirementAccept(Clazz                           clazz,
                                         KotlinMetadata                  kotlinMetadata,
                                         KotlinVersionRequirementVisitor kotlinVersionRequirementVisitor)
    {
        if (versionRequirement != null)
        {
            versionRequirement.accept(clazz, kotlinMetadata, this, kotlinVersionRequirementVisitor);
        }
    }

    public void annotationsAccept(Clazz                   clazz,
                                  KotlinAnnotationVisitor kotlinAnnotationVisitor)
    {
        for (KotlinMetadataAnnotation annotation : annotations)
        {
            kotlinAnnotationVisitor.visitTypeAliasAnnotation(clazz, this, annotation);
        }
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin type alias (" + name + ")"; //TODO "(name -> underlying/exapanded)"
    }
}
