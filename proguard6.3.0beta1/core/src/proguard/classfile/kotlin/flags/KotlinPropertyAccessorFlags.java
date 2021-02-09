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
package proguard.classfile.kotlin.flags;

import kotlinx.metadata.*;

import java.util.*;

/**
 * Flags for Kotlin property accessors (getters/setters for properties).
 *
 * Valid common flags:
 *   - hasAnnotations
 *   - isInternal
 *   - isPrivate
 *   - isProtected
 *   - isPublic
 *   - isPrivateToThis
 *   - isLocal
 *   - isFinal
 *   - isOpen
 *   - isAbstract
 *   - isSealed
 */
public class KotlinPropertyAccessorFlags extends KotlinFlags
{

    public KotlinCommonFlags     common     = new KotlinCommonFlags();
    public KotlinVisibilityFlags visibility = new KotlinVisibilityFlags();
    public KotlinModalityFlags   modality   = new KotlinModalityFlags();

    protected List<KotlinFlags> getChildren()
    {
        return Arrays.asList(common,visibility,modality);
    }


    /**
     * Signifies that the corresponding property is not default, i.e. it has a body and/or annotations in the source code.
     */
    public boolean isDefault;

    /**
     * Signifies that the corresponding property is `external`.
     */
    public boolean isExternal;

    /**
     * Signifies that the corresponding property is `inline`.
     */
    public boolean isInline;

    public KotlinPropertyAccessorFlags(int flags)
    {
        setFlags(flags);
    }


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.PropertyAccessor.IS_NOT_DEFAULT, new FlagValue(() -> isDefault,  newValue -> isDefault = newValue).negation());
        map.put(Flag.PropertyAccessor.IS_EXTERNAL,    new FlagValue(() -> isExternal, newValue -> isExternal = newValue));
        map.put(Flag.PropertyAccessor.IS_INLINE,      new FlagValue(() -> isInline,   newValue -> isInline = newValue));
        return map;
    }

}
