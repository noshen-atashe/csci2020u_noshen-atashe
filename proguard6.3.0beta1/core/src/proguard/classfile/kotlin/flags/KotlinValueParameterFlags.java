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
 * Flags for Kotlin value parameters.
 *
 * No valid visibility or modality flags.
 *
 * hasAnnotations is valid.
 */
public class KotlinValueParameterFlags extends KotlinFlags
{
    public KotlinCommonFlags common = new KotlinCommonFlags();


    protected List<KotlinFlags> getChildren()
    {
        return Arrays.asList(common);
    }

    /**
     * Signifies that the corresponding value parameter declares a default value. Note that the default value itself can be a complex
     * expression and is not available via metadata. Also note that in case of an override of a parameter with default value, the
     * parameter in the derived method does _not_ declare the default value ([DECLARES_DEFAULT_VALUE] == false), but the parameter is
     * still optional at the call site because the default value from the base method is used.
     */
    public boolean hasDefaultValue;

    /**
     * Signifies that the corresponding value parameter is `crossinline`.
     */
    public boolean isCrossInline;

    /**
     * Signifies that the corresponding value parameter is `noinline`.
     */
    public boolean isNoInline;

    public KotlinValueParameterFlags(int flags)
    {
        setFlags(flags);
    }


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.ValueParameter.DECLARES_DEFAULT_VALUE, new FlagValue(() -> hasDefaultValue, newValue -> hasDefaultValue = newValue));
        map.put(Flag.ValueParameter.IS_CROSSINLINE,         new FlagValue(() -> isCrossInline,   newValue -> isCrossInline = newValue));
        map.put(Flag.ValueParameter.IS_NOINLINE,            new FlagValue(() -> isNoInline,      newValue -> isNoInline = newValue));
        return map;
    }
}
