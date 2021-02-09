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
import proguard.classfile.kotlin.visitors.*;

import java.util.*;

/**
 * This class is named after Kotlin's own naming scheme. A declaration container is a type that
 * can define functions, properties and delegated properties, and that can also define type aliases.
 */
public abstract class KotlinDeclarationContainerMetadata
extends KotlinMetadata
{
    public List<KotlinPropertyMetadata> properties;

    public List<KotlinFunctionMetadata> functions;

    public List<KotlinTypeAliasMetadata> typeAliases;

    public String ownerClassName;
    public Clazz  ownerReferencedClass;

    // Extensions.
    public List<KotlinPropertyMetadata> localDelegatedProperties;


    public KotlinDeclarationContainerMetadata(int    k,
                                              int[]  mv,
                                              int[]  bv,
                                              int    xi,
                                              String xs,
                                              String pn)
    {
        super(k, mv, bv, xi, xs, pn);
    }


    public void propertiesAccept(Clazz clazz, KotlinPropertyVisitor kotlinPropertyVisitor)
    {
        for (KotlinPropertyMetadata property : properties)
        {
            property.accept(clazz, this, kotlinPropertyVisitor);
        }
    }


    //TODO currently unclear whether these are separate from `properties` or if they are additional info about some of them
    public void delegatedPropertiesAccept(Clazz clazz, KotlinPropertyVisitor kotlinPropertyVisitor)
    {
        for (KotlinPropertyMetadata localDelegatedProperty : localDelegatedProperties)
        {
            localDelegatedProperty.acceptAsDelegated(clazz, this, kotlinPropertyVisitor);
        }
    }


    public void functionsAccept(Clazz clazz, KotlinFunctionVisitor kotlinFunctionVisitor)
    {
        for (KotlinFunctionMetadata function : functions)
        {
            function.accept(clazz, this, kotlinFunctionVisitor);
        }
    }


    public void typeAliasesAccept(Clazz clazz, KotlinTypeAliasVisitor kotlinTypeAliasVisitor)
    {
        for (KotlinTypeAliasMetadata typeAlias : typeAliases)
        {
            typeAlias.accept(clazz, this, kotlinTypeAliasVisitor);
        }
    }
}
