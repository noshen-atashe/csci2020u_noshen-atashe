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
import proguard.classfile.kotlin.visitors.KotlinMetadataVisitor;
import proguard.classfile.visitor.ClassVisitor;

/**
 * Initializes the kotlin metadata for each Kotlin class. After initialization, all
 * info from the annotation is represented in the Clazz's `kotlinMetadata` field. All
 * arrays in kotlinMetadata are initialized, even if empty.
 */
public class ReferencedKotlinMetadataVisitor
implements   ClassVisitor
{
    private final KotlinMetadataVisitor kotlinMetadataVisitor;

    public ReferencedKotlinMetadataVisitor(KotlinMetadataVisitor kotlinMetadataVisitor)
    {
        this.kotlinMetadataVisitor = kotlinMetadataVisitor;
    }

    // Implementations for ClassVisitor.

    @Override
    public void visitProgramClass(ProgramClass programClass)
    {
        programClass.kotlinMetadataAccept(kotlinMetadataVisitor);
    }

    @Override
    public void visitLibraryClass(LibraryClass libraryClass)
    {
        libraryClass.kotlinMetadataAccept(kotlinMetadataVisitor);
    }
}
