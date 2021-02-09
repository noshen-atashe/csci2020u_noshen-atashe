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
import proguard.classfile.kotlin.visitors.KotlinPropertyVisitor;

/**
 * This class checks the assumption: All properties need a JVM signature for their getter
 */
public class PropertyIntegrity
extends    SimpleConstraintChecker
implements ConstraintChecker,
           KotlinPropertyVisitor
{
    public static KotlinMetadataConstraint constraint()
    {
        return KotlinMetadataConstraint.makeFromProperty(new PropertyIntegrity());
    }


    // Implementations for KotlinPropertyVisitor.
    @Override
    public void visitAnyProperty(Clazz                              clazz,
                                 KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata,
                                 KotlinPropertyMetadata             kotlinPropertyMetadata)
    {
        AssertUtil util = new AssertUtil("Property", clazz, kotlinDeclarationContainerMetadata, reporter);

        if (kotlinPropertyMetadata.backingFieldSignature == null &&
            kotlinPropertyMetadata.getterSignature       == null &&
            kotlinPropertyMetadata.setterSignature       == null)
        {
            reporter.report(new MyMissingMetadataError("accessor",
                                                       clazz,
                                                       kotlinDeclarationContainerMetadata));
        }

        if (kotlinPropertyMetadata.backingFieldSignature  != null)
        {
            util.reportIfNullReference(kotlinPropertyMetadata.referencedBackingFieldClass,
                                             "backing field class");

            util.reportIfNullReference(kotlinPropertyMetadata.referencedBackingField,
                                             "backing field");

            if (kotlinPropertyMetadata.referencedBackingFieldClass != null &&
                kotlinPropertyMetadata.referencedBackingField      != null)
            {
                util.reportIfFieldDangling(kotlinPropertyMetadata.referencedBackingFieldClass,
                                                 kotlinPropertyMetadata.referencedBackingField,
                                                 "backing field");
            }
        }

        if (kotlinPropertyMetadata.getterSignature != null)
        {
            util.reportIfNullReference(kotlinPropertyMetadata.referencedGetterMethod,
                                       "getter");
        }

        if (kotlinPropertyMetadata.setterSignature != null)
        {
            util.reportIfNullReference(kotlinPropertyMetadata.referencedSetterMethod,
                                       "setter");
        }

        if (kotlinPropertyMetadata.syntheticMethodForAnnotations != null)
        {
            util.reportIfNullReference(kotlinPropertyMetadata.referencedSyntheticMethodClass,
                                       "synthetic annotations method class");

            util.reportIfNullReference(kotlinPropertyMetadata.referencedSyntheticMethodForAnnotations,
                                       "synthetic annotations method");


            if (kotlinPropertyMetadata.referencedSyntheticMethodClass          != null &&
                kotlinPropertyMetadata.referencedSyntheticMethodForAnnotations != null)
            {
                util.reportIfMethodDangling(kotlinPropertyMetadata.referencedSyntheticMethodClass,
                                            kotlinPropertyMetadata.referencedSyntheticMethodForAnnotations,
                                            "synthetic annotations method");
            }
        }
    }


    // Small helper classes.

    private static class MyMissingMetadataError
    extends MissingMetadataError
    {
        MyMissingMetadataError(String                             missingElement,
                               Clazz                              clazz,
                               KotlinDeclarationContainerMetadata kotlinDeclarationContainerMetadata)
        {
            super("Property", missingElement, clazz, kotlinDeclarationContainerMetadata);
        }
    }
}
