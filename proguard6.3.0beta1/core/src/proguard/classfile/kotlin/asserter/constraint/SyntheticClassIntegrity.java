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
import proguard.classfile.kotlin.visitors.KotlinMetadataVisitor;

import static proguard.classfile.kotlin.KotlinConstants.*;

public class SyntheticClassIntegrity
extends      SimpleConstraintChecker
implements   KotlinMetadataVisitor,
             ConstraintChecker
{

    public void visitAnyKotlinMetadata(Clazz clazz, KotlinMetadata kotlinMetadata) {}

    public static KotlinMetadataConstraint constraint()
    {
        return KotlinMetadataConstraint.make(new SyntheticClassIntegrity());
    }

    @Override
    public void visitKotlinSyntheticClassMetadata(Clazz                            clazz,
                                                  KotlinSyntheticClassKindMetadata kotlinSyntheticClassKindMetadata)
    {
        switch (kotlinSyntheticClassKindMetadata.kind)
        {
            case DEFAULT_IMPLS:
                checkSuffix(clazz, kotlinSyntheticClassKindMetadata, DEFAULT_IMPLEMENTATIONS_SUFFIX, reporter);
                break;
            case WHEN_MAPPINGS:
                checkSuffix(clazz, kotlinSyntheticClassKindMetadata, WHEN_MAPPINGS_SUFFIX, reporter);
                break;
            case LAMBDA:
                // Other synthetic classes are created from lambdas.
                try {
                    Integer.parseInt(
                        kotlinSyntheticClassKindMetadata.referencedClass.getName().substring(
                            kotlinSyntheticClassKindMetadata.referencedClass.getName().lastIndexOf("$") + 1));
                }
                catch (NumberFormatException e)
                {
                    reporter.report(new KotlinMetadataError(clazz, kotlinSyntheticClassKindMetadata)
                    {
                        public String errorDescription()
                        {
                            return "Synthetic lambda inner classname is not an integer.";
                        }
                    });
                }

                if (kotlinSyntheticClassKindMetadata.functions.isEmpty())
                {
                    reporter.report(new KotlinMetadataError(clazz, kotlinSyntheticClassKindMetadata)
                    {
                        public String errorDescription()
                                                            {
                                                               return "Synthetic class has no functions";
                                                                                                         }
                    });
                }
                else if (kotlinSyntheticClassKindMetadata.functions.size() > 1)
                {
                    reporter.report(new KotlinMetadataError(clazz, kotlinSyntheticClassKindMetadata)
                    {
                        public String errorDescription()
                        {
                            return "Synthetic class has multiple functions";
                        }
                    });
                }
                break;
            case UNKNOWN:
        }
    }

    // Helper methods.

    private static void checkSuffix(Clazz                            clazz,
                                    KotlinSyntheticClassKindMetadata kotlinSyntheticClassKindMetadata,
                                    String                           suffix,
                                    Reporter                         reporter)
    {
        if (!kotlinSyntheticClassKindMetadata.referencedClass.getName().endsWith(suffix))
        {
            reporter.report(new KotlinMetadataError(clazz, kotlinSyntheticClassKindMetadata)
            {
                public String errorDescription()
                {
                    return "Synthetic class name does not end with " + suffix;
                }
            });
        }
    }
}
