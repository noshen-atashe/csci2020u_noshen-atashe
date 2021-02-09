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
package proguard.classfile.kotlin.asserter.constraint;

import proguard.classfile.Clazz;
import proguard.classfile.kotlin.*;
import proguard.classfile.kotlin.asserter.*;
import proguard.classfile.kotlin.visitors.*;

/**
 * This class checks the assumption: All functions need a JVM signature
 */
public class ClassIntegrity
extends    SimpleConstraintChecker
implements ConstraintChecker,
           KotlinMetadataVisitor

{
    private final KotlinMetadataVisitor myCompanionObjectFlagChecker = new MyCompanionObjectFlagChecker();

    public static KotlinMetadataConstraint constraint()
    {
        return KotlinMetadataConstraint.make(new ClassIntegrity());
    }


    // Implementations for KotlinMetadataVisitor.
    @Override
    public void visitAnyKotlinMetadata(Clazz clazz, KotlinMetadata kotlinMetadata) {}

    @Override
    public void visitKotlinClassMetadata(Clazz                   clazz,
                                         KotlinClassKindMetadata kotlinClassKindMetadata)
    {
        AssertUtil util = new AssertUtil("Class", clazz, kotlinClassKindMetadata, reporter);

        util.reportIfNullReference(kotlinClassKindMetadata.referencedClass, "referenced class");

        if (kotlinClassKindMetadata.companionObjectName != null)
        {
            util.reportIfNullReference(kotlinClassKindMetadata.referencedCompanionClass, "companion");

            kotlinClassKindMetadata.companionAccept(myCompanionObjectFlagChecker);
        }

        if (kotlinClassKindMetadata.superTypes.isEmpty())
        {
            reporter.report(new MyMissingMetadataError("super types",
                                                       clazz,
                                                       kotlinClassKindMetadata));
        }

        kotlinClassKindMetadata.referencedEnumEntries
            .forEach(util.reportIfFieldDangling(clazz,
                                                "enum entries"));

        kotlinClassKindMetadata.referencedNestedClasses
            .forEach(util.reportIfNullReference("enum entries"));

        kotlinClassKindMetadata.referencedSealedSubClasses
            .forEach(util.reportIfNullReference("sealed subclasses"));
    }


    // Small helper classes.
    private class MyCompanionObjectFlagChecker
    implements KotlinMetadataVisitor
    {
        // Implementations for KotlinFunctionVisitor.
        @Override
        public void visitAnyKotlinMetadata(Clazz clazz, KotlinMetadata kotlinMetadata) {}

        @Override
        public void visitKotlinClassMetadata(Clazz                   clazz,
                                             KotlinClassKindMetadata kotlinClassKindMetadata)
        {
            if (!kotlinClassKindMetadata.flags.isCompanionObject)
            {
                reporter.report(new UnexpectedFlagError("Companion class '"+clazz.getName()+"'",
                                                        "isCompanionObject",
                                                        false,
                                                        clazz,
                                                        kotlinClassKindMetadata));
            }
        }
    }


    private static class MyMissingMetadataError
    extends MissingMetadataError
    {
        MyMissingMetadataError(String                  missingElement,
                               Clazz                   clazz,
                               KotlinClassKindMetadata kotlinClassKindMetadata)
        {
            super("Class", missingElement, clazz, kotlinClassKindMetadata);
        }
    }
}
