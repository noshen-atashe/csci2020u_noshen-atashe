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
package proguard.classfile.kotlin.asserter;

import proguard.classfile.*;
import proguard.classfile.kotlin.KotlinMetadata;
import proguard.classfile.kotlin.visitors.*;

/**
 * Implementations of this class represent a conceptual constraint on KotlinMetadata.
 *
 * A KotlinMetadataConstraint is checked in the context of ClassPools and a specific KotlinMetadata
 * instance, and should report its findings to the passed Reporter.
 */
@FunctionalInterface
public interface KotlinMetadataConstraint
{
    void check(Reporter       reporter,
               Clazz          clazz,
               KotlinMetadata metadata,
               ClassPool      programClassPool,
               ClassPool      libraryClassPool);


    // Small helper methods.

    /**
     * Create a new KotlinMetadataConstraint based on the passed ConstraintChecker.
     *
     * Before executing the check, set the reporter and ClassPool for context.
     */
    static KotlinMetadataConstraint makeConstraint(ConstraintChecker     checker,
                                                   KotlinMetadataVisitor checkingVisitor)
    {
        return (reporter, clazz, metadata, programClassPool, libClassPool) ->
               {
                   checker.setReporter  (reporter);
                   checker.setClassPools(programClassPool, libClassPool);

                   try
                   {
                       metadata.accept(clazz, checkingVisitor);
                   }
                   catch (Exception e)
                   {
                       reporter.report(new KotlinMetadataError(clazz, metadata)
                       {
                           public String errorDescription()
                           {
                               return "Encountered unexpected Exception when checking constraint: "+e.getMessage();
                           }
                       });
                   }
               };
    }


    static <T extends ConstraintChecker & KotlinMetadataVisitor> KotlinMetadataConstraint make(T visitor)
    {
        return makeConstraint(visitor, visitor);
    }

    static <T extends ConstraintChecker & KotlinTypeVisitor> KotlinMetadataConstraint makeFromType(T visitor)
    {
        return makeConstraint(visitor, new AllTypeVisitor(visitor));
    }

    static <T extends ConstraintChecker & KotlinValueParameterVisitor> KotlinMetadataConstraint makeFromValueParameter(T visitor)
    {
        return makeConstraint(visitor, new AllValueParameterVisitor(visitor));
    }

    static <T extends ConstraintChecker & KotlinPropertyVisitor> KotlinMetadataConstraint makeFromProperty(T visitor)
    {
        return makeConstraint(visitor, new AllKotlinPropertiesVisitor(visitor));
    }

    static <T extends ConstraintChecker & KotlinFunctionVisitor> KotlinMetadataConstraint makeFromFunction(T visitor)
    {
        return makeConstraint(visitor, new AllFunctionsVisitor(visitor));
    }

    static <T extends ConstraintChecker & KotlinConstructorVisitor> KotlinMetadataConstraint makeFromConstructor(T visitor)
    {
        return makeConstraint(visitor, new AllConstructorsVisitor(visitor));
    }

}
