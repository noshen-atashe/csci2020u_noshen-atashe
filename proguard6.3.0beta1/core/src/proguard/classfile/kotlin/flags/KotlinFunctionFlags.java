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
 * Flags for Kotlin functions.
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
public class KotlinFunctionFlags extends KotlinFlags
{

    public KotlinCommonFlags     common     = new KotlinCommonFlags();
    public KotlinVisibilityFlags visibility = new KotlinVisibilityFlags();
    public KotlinModalityFlags   modality   = new KotlinModalityFlags();

    protected List<KotlinFlags> getChildren()
    {
        return Arrays.asList(common,visibility,modality);
    }

    /**
     * A member kind flag, signifying that the corresponding function is explicitly declared in the containing class.
     */
    public boolean isDeclaration;

    /**
     * A member kind flag, signifying that the corresponding function exists in the containing class because a function with a suitable
     * signature exists in a supertype. This flag is not written by the Kotlin compiler and its effects are unspecified.
     */
    public boolean isFakeOverride;

    /**
     * A member kind flag, signifying that the corresponding function exists in the containing class because it has been produced
     * by interface delegation (delegation "by").
     */
    public boolean isDelegation;

    /**
     * A member kind flag, signifying that the corresponding function exists in the containing class because it has been synthesized
     * by the compiler and has no declaration in the source code.
     */
    public boolean isSynthesized;

    /**
     * Signifies that the corresponding function is `operator`.
     */
    public boolean isOperator;

    /**
     * Signifies that the corresponding function is `infix`.
     */
    public boolean isInfix;

    /**
     * Signifies that the corresponding function is `inline`.
     */
    public boolean isInline;

    /**
     * Signifies that the corresponding function is `tailrec`.
     */
    public boolean isTailrec;

    /**
     * Signifies that the corresponding function is `external`.
     */
    public boolean isExternal;

    /**
     * Signifies that the corresponding function is `suspend`.
     */
    public boolean isSuspend;

    /**
     * Signifies that the corresponding function is `expect`.
     */
    public boolean isExpect;

    public KotlinFunctionFlags(int flags)
    {
        setFlags(flags);
    }


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.Function.IS_DECLARATION,   new FlagValue(() -> isDeclaration,  newValue -> isDeclaration = newValue));
        map.put(Flag.Function.IS_FAKE_OVERRIDE, new FlagValue(() -> isFakeOverride, newValue -> isFakeOverride = newValue));
        map.put(Flag.Function.IS_DELEGATION,    new FlagValue(() -> isDelegation,   newValue -> isDelegation = newValue));
        map.put(Flag.Function.IS_SYNTHESIZED,   new FlagValue(() -> isSynthesized,  newValue -> isSynthesized = newValue));
        map.put(Flag.Function.IS_OPERATOR,      new FlagValue(() -> isOperator,     newValue -> isOperator = newValue));
        map.put(Flag.Function.IS_INFIX,         new FlagValue(() -> isInfix,        newValue -> isInfix = newValue));
        map.put(Flag.Function.IS_INLINE,        new FlagValue(() -> isInline,       newValue -> isInline = newValue));
        map.put(Flag.Function.IS_TAILREC,       new FlagValue(() -> isTailrec,      newValue -> isTailrec = newValue));
        map.put(Flag.Function.IS_EXTERNAL,      new FlagValue(() -> isExternal,     newValue -> isExternal = newValue));
        map.put(Flag.Function.IS_SUSPEND,       new FlagValue(() -> isSuspend,      newValue -> isSuspend = newValue));
        map.put(Flag.Function.IS_EXPECT,        new FlagValue(() -> isExpect,       newValue -> isExpect = newValue));
        return map;
    }

}
