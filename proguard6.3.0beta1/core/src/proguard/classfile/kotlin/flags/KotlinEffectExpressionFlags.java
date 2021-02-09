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

import kotlinx.metadata.Flag;

import java.util.*;

/**
 * Flags for Kotlin types.
 *
 * No valid common flags.
 */
public class KotlinEffectExpressionFlags extends KotlinFlags
{
    /**
     * Signifies that the corresponding effect expression should be negated to compute the proposition or the conclusion of an effect.
     */
    public boolean isNegated;

    /**
     * Signifies that the corresponding effect expression checks whether a value of some variable is `null`.
     */
    public boolean isNullCheckPredicate;

    public KotlinEffectExpressionFlags(int flags)
    {
        setFlags(flags);
    }


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.EffectExpression.IS_NEGATED,              new FlagValue(() -> isNegated,            newValue -> isNegated = newValue));
        map.put(Flag.EffectExpression.IS_NULL_CHECK_PREDICATE, new FlagValue(() -> isNullCheckPredicate, newValue -> isNullCheckPredicate = newValue));
        return map;
    }

}
