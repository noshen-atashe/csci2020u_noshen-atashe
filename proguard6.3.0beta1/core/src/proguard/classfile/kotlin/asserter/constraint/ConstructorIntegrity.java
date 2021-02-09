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
import proguard.classfile.kotlin.visitors.KotlinConstructorVisitor;
import proguard.classfile.visitor.*;

public class   ConstructorIntegrity
    extends    SimpleConstraintChecker
    implements KotlinConstructorVisitor,
               ConstraintChecker
{
    public static KotlinMetadataConstraint constraint()
    {
        return KotlinMetadataConstraint.makeFromConstructor(new ConstructorIntegrity());
    }


    public void visitConstructor(Clazz                     clazz,
                                 KotlinClassKindMetadata   kotlinClassKindMetadata,
                                 KotlinConstructorMetadata kotlinConstructorMetadata)
    {
        AssertUtil util = new AssertUtil("Constructor", clazz, kotlinClassKindMetadata, reporter);

        if (!kotlinClassKindMetadata.flags.isAnnotationClass)
        {
            if (kotlinConstructorMetadata.referencedMethod == null)
            {
                reporter.report(new MyMissingReferenceError("method",
                                                            clazz,
                                                            kotlinClassKindMetadata));
            }
            else
            {
                util.reportIfMethodDangling(clazz, kotlinConstructorMetadata.referencedMethod, "constructor method");
            }
        }
    }

    private static class MyMissingReferenceError
    extends MissingReferenceError
    {
        MyMissingReferenceError(String         missingPart,
                                Clazz          clazz,
                                KotlinMetadata kotlinMetadata)
        {
            super("Constructor", missingPart, clazz, kotlinMetadata);
        }
    }
}
