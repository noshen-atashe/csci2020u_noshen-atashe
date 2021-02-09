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

import kotlinx.metadata.jvm.*;
import proguard.classfile.*;
import proguard.classfile.kotlin.flags.*;
import proguard.classfile.kotlin.visitors.*;
import proguard.classfile.visitor.MemberVisitor;
import proguard.util.*;

import java.util.*;

public class KotlinPropertyMetadata
extends    SimpleVisitorAccepter
implements VisitorAccepter
{
    public String name;


    public List<KotlinTypeParameterMetadata> typeParameters;

    public KotlinTypeMetadata receiverType;

    public List<KotlinValueParameterMetadata> setterParameters;

    public KotlinTypeMetadata type;

    public KotlinVersionRequirementMetadata versionRequirement;

    public KotlinPropertyFlags flags;

    public KotlinPropertyAccessorFlags getterFlags;

    public KotlinPropertyAccessorFlags setterFlags;

    // Extensions.
    public JvmFieldSignature  backingFieldSignature;
    // Store the class where the referencedBackingField is declared.
    public Clazz              referencedBackingFieldClass;
    public Field              referencedBackingField;
    public JvmMethodSignature getterSignature;
    public Method             referencedGetterMethod;
    public JvmMethodSignature setterSignature;
    public Method             referencedSetterMethod;

    public JvmMethodSignature syntheticMethodForAnnotations;

    public Clazz              referencedSyntheticMethodClass;
    public Method             referencedSyntheticMethodForAnnotations;


    public KotlinPropertyMetadata(int    flags,
                                  String name,
                                  int    getterFlags,
                                  int    setterFlags)
    {
        this.name        = name;
        this.flags       = new KotlinPropertyFlags(flags);
        this.getterFlags = new KotlinPropertyAccessorFlags(getterFlags);
        this.setterFlags = new KotlinPropertyAccessorFlags(setterFlags);
    }


    public void accept(Clazz                              clazz,
                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                       KotlinPropertyVisitor              kotlinPropertyVisitor)
    {
        kotlinPropertyVisitor.visitProperty(clazz, kotlinDeclarationContainerMetadata, this);
    }


    void acceptAsDelegated(Clazz                              clazz,
                           KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                           KotlinPropertyVisitor              kotlinPropertyVisitor)
    {
        kotlinPropertyVisitor.visitDelegatedProperty(clazz, kotlinDeclarationContainerMetadata, this);
    }


    public void typeAccept(Clazz                              clazz,
                           KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                           KotlinTypeVisitor                  kotlinTypeVisitor)
    {
        //TODO unusual?
//        type.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinTypeVisitor);
        kotlinTypeVisitor.visitPropertyType(clazz, kotlinDeclarationContainerMetadata, this, type);
    }


    public void receiverTypeAccept(Clazz                              clazz,
                                   KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                   KotlinTypeVisitor                  kotlinTypeVisitor)
    {
        if (receiverType != null)
        {
            //TODO unusual?
//        receiverType.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinTypeVisitor);
            kotlinTypeVisitor.visitPropertyReceiverType(clazz,
                                                        kotlinDeclarationContainerMetadata,
                                                        this,
                                                        receiverType);
        }
    }


    public void setterParametersAccept(Clazz                              clazz,
                                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                       KotlinValueParameterVisitor        kotlinValueParameterVisitor)
    {
        for (KotlinValueParameterMetadata setterParameter : setterParameters)
        {
            setterParameter.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinValueParameterVisitor);
        }
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


    public void versionRequirementAccept(Clazz                              clazz,
                                         KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                         KotlinVersionRequirementVisitor    kotlinVersionRequirementVisitor)
    {
        if (versionRequirement != null)
        {
            versionRequirement.accept(clazz, kotlinDeclarationContainerMetadata, this, kotlinVersionRequirementVisitor);
        }
    }


    //TODO add different ones when they appear necessary.
    public void referencedSetterMethodAccept(Clazz clazz, MemberVisitor methodVisitor)
    {
        if (referencedSetterMethod!= null)
        {
            referencedSetterMethod.accept(clazz, methodVisitor);
        }
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin " +
               (flags.isDelegated ? "delegated " : "") +
               "property (" + name + " | " +
               (backingFieldSignature != null ? "b" : "") +
               (flags.hasGetter ? "g" + (getterFlags.isDefault ? "" : "+") : "") +
               (flags.hasSetter ? "s" + (setterFlags.isDefault ? "" : "+") : "") +
               ")";
    }
}
