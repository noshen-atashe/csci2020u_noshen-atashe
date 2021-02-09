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

public class KotlinModalityFlags extends KotlinFlags
{
    // Valid for: class, constructor, function, synthetic function, property (including getter + setter)

    /**
     * Signifies the declaration is 'final'
     */
    public boolean isFinal;

    /**
     * Signifies the declaration is 'open'
     */
    public boolean isOpen;

    /**
     * Signifies the declaration is 'abstract'
     */
    public boolean isAbstract;

    /**
     * Signifies the declaration is 'sealed'
     */
    public boolean isSealed;


    protected Map<Flag, FlagValue> getOwnProperties()
    {
        HashMap<Flag, FlagValue> map = new HashMap<>();
        map.put(Flag.IS_FINAL,    new FlagValue(() -> isFinal,    newValue -> isFinal = newValue));
        map.put(Flag.IS_OPEN,     new FlagValue(() -> isOpen,     newValue -> isOpen = newValue));
        map.put(Flag.IS_ABSTRACT, new FlagValue(() -> isAbstract, newValue -> isAbstract = newValue));
        map.put(Flag.IS_SEALED,   new FlagValue(() -> isSealed,   newValue -> isSealed = newValue));
        return map;
    }

}
