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
package proguard.classfile.kotlin.visitors.filter;

import proguard.classfile.Clazz;
import proguard.classfile.kotlin.*;
import proguard.classfile.kotlin.visitors.KotlinMetadataVisitor;
import proguard.util.Predicate;

/**
 * Delegate to another {@link KotlinMetadataVisitor} if the predicate returns true,
 * or if there's no predicate.
 *
 * Note: only for KotlinClassKindMetadata i.e. does not visit synthetic classes.
 *
 * For example, visit only abstract classes:
 *
 * programClassPool.classesAccept(
 *     new ClazzToKotlinMetadataVisitor(
 *     new KotlinClassKindFilter(
 *         clazz -> clazz.flags.isAbstract,
 *         new MyOtherKotlinMetadataVisitor())));
 */
public class KotlinClassKindFilter
implements   KotlinMetadataVisitor
{
    private final Predicate<KotlinClassKindMetadata> predicate;
    private final KotlinMetadataVisitor              kotlinMetadataVisitor;

    public KotlinClassKindFilter(KotlinMetadataVisitor kotlinMetadataVisitor)
    {
        this(__ -> true, kotlinMetadataVisitor);
    }

    public KotlinClassKindFilter(Predicate<KotlinClassKindMetadata> predicate, KotlinMetadataVisitor kotlinMetadataVisitor) {
        this.predicate             = predicate;
        this.kotlinMetadataVisitor = kotlinMetadataVisitor;
    }

    @Override
    public void visitKotlinClassMetadata(Clazz clazz, KotlinClassKindMetadata kotlinClassKindMetadata)
    {
        if (this.predicate.test(kotlinClassKindMetadata))
        {
            this.kotlinMetadataVisitor.visitKotlinClassMetadata(clazz, kotlinClassKindMetadata);
        }
    }

    @Override
    public void visitAnyKotlinMetadata(Clazz clazz, KotlinMetadata kotlinMetadata) {}
}
