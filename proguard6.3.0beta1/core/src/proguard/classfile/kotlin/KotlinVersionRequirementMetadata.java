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
import proguard.classfile.kotlin.visitors.*;
import proguard.util.*;

public class KotlinVersionRequirementMetadata
extends SimpleVisitorAccepter
implements VisitorAccepter
{
    public KmVersionRequirementVersionKind kind;

    public KmVersionRequirementLevel level;

    public Integer errorCode;

    public String message;

    public int major;

    public int minor;

    public int patch;


    public void accept(Clazz                           clazz,
                       KotlinMetadata                  kotlinMetadata,
                       KotlinVersionRequirementVisitor kotlinVersionRequirementVisitor)
    {
        kotlinVersionRequirementVisitor.visitClassVersionRequirement(clazz,
                                                                     kotlinMetadata,
                                                                     this);
    }


    public void accept(Clazz                           clazz,
                       KotlinMetadata                  kotlinMetadata,
                       KotlinConstructorMetadata       kotlinConstructorMetadata,
                       KotlinVersionRequirementVisitor kotlinVersionRequirementVisitor)
    {
        kotlinVersionRequirementVisitor.visitConstructorVersionRequirement(clazz,
                                                                           kotlinMetadata,
                                                                           kotlinConstructorMetadata,
                                                                           this);
    }


    public void accept(Clazz                           clazz,
                       KotlinMetadata                  kotlinMetadata,
                       KotlinFunctionMetadata          kotlinFunctionMetadata,
                       KotlinVersionRequirementVisitor kotlinVersionRequirementVisitor)
    {
        kotlinVersionRequirementVisitor.visitFunctionVersionRequirement(clazz,
                                                                        kotlinMetadata,
                                                                        kotlinFunctionMetadata,
                                                                        this);
    }


    public void accept(Clazz                              clazz,
                       KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                       KotlinPropertyMetadata             kotlinPropertyMetadata,
                       KotlinVersionRequirementVisitor    kotlinVersionRequirementVisitor)
    {
        kotlinVersionRequirementVisitor.visitPropertyVersionRequirement(clazz,
                                                                        kotlinDeclarationContainerMetadata,
                                                                        kotlinPropertyMetadata,
                                                                        this);
    }


    public void accept(Clazz                           clazz,
                       KotlinMetadata                  kotlinMetadata,
                       KotlinTypeAliasMetadata         kotlinTypeAliasMetadata,
                       KotlinVersionRequirementVisitor kotlinVersionRequirementVisitor)
    {
        kotlinVersionRequirementVisitor.visitTypeAliasVersionRequirement(clazz,
                                                                         kotlinMetadata,
                                                                         kotlinTypeAliasMetadata,
                                                                         this);
    }


    // Implementations for Object.
    @Override
    public String toString()
    {
        return "Kotlin version req (" + major + "." + minor + "." + patch + ")";
    }
}
