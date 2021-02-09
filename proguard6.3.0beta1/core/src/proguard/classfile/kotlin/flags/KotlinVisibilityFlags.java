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

public class KotlinVisibilityFlags extends KotlinFlags
{
    // Valid for: class, constructor, function, synthetic function, property (including getter + setter), typeAlias

    /**
     * For top-level declarations : signifies visibility everywhere in the same module
     * For class/interface members: signifies visibility everywhere in the same module
     *                              to users who can has access to the declaring class
     */
    public boolean isInternal;

    /**
     * For top-level declarations: visible only inside the file containing the declaration
     * For class/interface members: visible only within the class
     */
    public boolean isPrivate;

    /**
     * For class/interface members: private + visible in subclasses
     */
    public boolean isProtected;

    /**
     * For top-level declarations: visible everywhere
     * For class/interface members: visible to everywhere to users who can access the
     *                              declaring class
     */
    public boolean isPublic;

    /**
     * For class/interface members: visible only on the same instance of the declaring class
     */
    public boolean isPrivateToThis;

    /**
     * Signifies that the declaration is declared inside a code block, not visible from outside
     */
    public boolean isLocal;


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.IS_INTERNAL,        new FlagValue(() -> isInternal, newValue -> isInternal = newValue));
        map.put(Flag.IS_LOCAL,           new FlagValue(() -> isLocal, newValue -> isLocal = newValue));
        map.put(Flag.IS_PRIVATE,         new FlagValue(() -> isPrivate, newValue -> isPrivate = newValue));
        map.put(Flag.IS_PRIVATE_TO_THIS, new FlagValue(() -> isPrivateToThis, newValue -> isPrivateToThis = newValue));
        map.put(Flag.IS_PROTECTED,       new FlagValue(() -> isProtected, newValue -> isProtected = newValue));
        map.put(Flag.IS_PUBLIC,          new FlagValue(() -> isPublic, newValue -> isPublic = newValue));
        return map;
    }

}
