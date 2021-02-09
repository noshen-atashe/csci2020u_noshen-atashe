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
import proguard.classfile.util.SimplifiedVisitor;
import proguard.classfile.visitor.*;

import java.util.function.Consumer;

public class AssertUtil
{
    private final String         parentElement;
    private final Clazz          clazz;
    private final KotlinMetadata kotlinMetadata;
    private final Reporter       reporter;


    public AssertUtil(String         parentElement,
                      Clazz          clazz,
                      KotlinMetadata kotlinMetadata,
                      Reporter       reporter)
    {
        this.parentElement  = parentElement;
        this.clazz          = clazz;
        this.kotlinMetadata = kotlinMetadata;
        this.reporter       = reporter;
    }


    public Consumer<Object> reportIfNullReference(String checkedElement)
    {
        return metadataElement ->
               reportIfNullReference(metadataElement, checkedElement);
    }


    public Consumer<Field> reportIfFieldDangling(Clazz  checkedClass,
                                                 String checkedElement)
    {
        return field ->
               reportIfFieldDangling(checkedClass, field, checkedElement);
    }


    public void reportIfNullReference(Object checkedElement,
                                      String checkedElementName)
    {
        if (checkedElement == null)
        {
            reporter.report(new MissingReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    public void reportIfFieldDangling(Clazz  checkedClass,
                                      Field  field,
                                      String checkedElementName)
    {
        ExactMemberMatcher match = new ExactMemberMatcher(field);

        checkedClass.accept(new AllFieldVisitor(match));

        if (!match.memberMatched)
        {
            reporter.report(new InvalidReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    public void reportIfMethodDangling(Clazz  checkedClass,
                                       Method method,
                                       String checkedElementName)
    {
        ExactMemberMatcher match = new ExactMemberMatcher(method);

        checkedClass.accept(new AllMethodVisitor(match));

        if (!match.memberMatched)
        {
            reporter.report(new InvalidReferenceError(parentElement,
                                                      checkedElementName,
                                                      clazz,
                                                      kotlinMetadata));
        }
    }


    // Small helper classes.

    private static class ExactMemberMatcher
    extends    SimplifiedVisitor
    implements MemberVisitor
    {
        private final Member  memberToMatch;

        boolean memberMatched;

        ExactMemberMatcher(Member memberToMatch)
        {
            this.memberToMatch = memberToMatch;
        }


        // Implementations for MemberVisitor.
        @Override
        public void visitAnyMember(Clazz clazz, Member member)
        {
            if (member == memberToMatch)
            {
                memberMatched = true;
            }
        }
    }
}
