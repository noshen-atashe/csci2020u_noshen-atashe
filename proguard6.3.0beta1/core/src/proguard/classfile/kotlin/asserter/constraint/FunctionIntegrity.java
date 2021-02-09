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

import proguard.classfile.*;
import proguard.classfile.kotlin.*;
import proguard.classfile.kotlin.asserter.*;
import proguard.classfile.kotlin.visitors.*;

/**
 * This class checks the assumption: All functions need a JVM signature
 */
public class FunctionIntegrity
extends      SimpleConstraintChecker
implements   ConstraintChecker,
             KotlinFunctionVisitor,
             KotlinValueParameterVisitor
{
    public static KotlinMetadataConstraint constraint()
    {
        return KotlinMetadataConstraint.makeFromFunction(new FunctionIntegrity());
    }

    // Implementations for KotlinFunctionVisitor.
    @Override
    public void visitAnyFunction(Clazz                  clazz,
                                 KotlinMetadata         kotlinMetadata,
                                 KotlinFunctionMetadata kotlinFunctionMetadata)
    {
        AssertUtil util = new AssertUtil("Function", clazz, kotlinMetadata, reporter);

        if (kotlinFunctionMetadata.jvmSignature == null)
        {
            reporter.report(new MyMissingMetadataError(clazz,
                                                       kotlinMetadata));
        }

        if (kotlinFunctionMetadata.referencedMethodClass == null)
        {
            reporter.report(new MyMissingReferenceError("method class",
                                                        clazz,
                                                        kotlinMetadata));
        }

        if (kotlinFunctionMetadata.referencedMethod == null)
        {
            reporter.report(new MyMissingReferenceError("method",
                                                        clazz,
                                                        kotlinMetadata));
        }

        if (kotlinFunctionMetadata.referencedMethod      != null &&
            kotlinFunctionMetadata.referencedMethodClass != null)
        {
            util.reportIfMethodDangling(kotlinFunctionMetadata.referencedMethodClass,
                                        kotlinFunctionMetadata.referencedMethod,
                                        "referenced method");
        }

        if (kotlinFunctionMetadata.referencedDefaultMethod      != null &&
            kotlinFunctionMetadata.referencedDefaultMethodClass != null)
        {
            util.reportIfMethodDangling(
                kotlinFunctionMetadata.referencedDefaultMethodClass,
                kotlinFunctionMetadata.referencedDefaultMethod,
                "referenced default method");
        }

        if (kotlinFunctionMetadata.referencedDefaultImplementationMethod      != null &&
            kotlinFunctionMetadata.referencedDefaultImplementationMethodClass != null)
        {
            util.reportIfMethodDangling(
                kotlinFunctionMetadata.referencedDefaultImplementationMethodClass,
                kotlinFunctionMetadata.referencedDefaultImplementationMethod,
                "referenced default implementation method");
        }

        // If any parameter has a hasDefault flag, then there should be a corresponding $default method.
        hasDefaults = false;
        kotlinFunctionMetadata.valueParametersAccept(clazz, kotlinMetadata, this);
        if (hasDefaults)
        {
            boolean hasDefaultMethod =
                kotlinFunctionMetadata.referencedDefaultMethod != null &&
                kotlinFunctionMetadata.referencedDefaultMethod.getName(kotlinFunctionMetadata.referencedDefaultMethodClass)
                    .equals(kotlinFunctionMetadata.referencedMethod.getName(kotlinFunctionMetadata.referencedMethodClass) + "$default");

            if (!hasDefaultMethod)
            {
                reporter.report(new MyMissingReferenceError(
                    kotlinFunctionMetadata.name + "$default method [" + kotlinFunctionMetadata.jvmSignature + "]",
                    clazz,
                    kotlinMetadata));
            }
        }

        // If the function is non-abstract and in an interface,
        // then there must be a default implementation in the $DefaultImpls class.
        if (!kotlinFunctionMetadata.flags.modality.isAbstract &&
            kotlinMetadata.k == KotlinConstants.METADATA_KIND_CLASS)
        {
            KotlinClassKindMetadata kotlinClassKindMetadata = (KotlinClassKindMetadata)kotlinMetadata;
            if (kotlinClassKindMetadata.flags.isInterface)
            {
                util.reportIfNullReference(kotlinFunctionMetadata.referencedDefaultImplementationMethod, "default implementation method");
                util.reportIfNullReference(kotlinFunctionMetadata.referencedDefaultImplementationMethodClass, "default implementation method class");
            }
        }

    }

    // Implementations for KotlinValueParameterVisitor.

    private boolean hasDefaults = false;

    @Override
    public void visitAnyValueParameter(Clazz                         clazz,
                                       KotlinValueParameterMetadata  kotlinValueParameterMetadata)
    {
        hasDefaults |= kotlinValueParameterMetadata.flags.hasDefaultValue;
    }

    private static class MyMissingMetadataError
    extends MissingMetadataError
    {
        MyMissingMetadataError(Clazz          clazz,
                               KotlinMetadata kotlinMetadata)
        {
            super("Function", "JVM signature", clazz, kotlinMetadata);
        }
    }

    private static class MyMissingReferenceError
    extends MissingReferenceError
    {
        MyMissingReferenceError(String         missingPart,
                                Clazz          clazz,
                                KotlinMetadata kotlinMetadata)
        {
            super("Function", missingPart, clazz, kotlinMetadata);
        }
    }
}
