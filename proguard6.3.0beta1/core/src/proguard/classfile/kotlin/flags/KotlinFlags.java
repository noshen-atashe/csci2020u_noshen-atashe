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
 * KotlinFlags represent a collection of flags in the metadata.
 * When implementing this interface, it is only expected that you override getOwnProperties().
 * If you have any children you also need to implement getChildren()
 */
public abstract class KotlinFlags
{

    /**
     * @return The mapping between the flags specific to this KotlinFlags instance and their value
     */
    protected abstract Map<Flag,FlagValue> getOwnProperties();

    /**
     * @return The list of children KotlinFlag instances (such as common or visibility)
     */
    protected List<KotlinFlags> getChildren() {
        return Collections.emptyList();
    }

    protected final void setFlags(int flags){
        getChildren().forEach(it -> it.setFlags(flags));
        getOwnProperties().forEach((flag, flagValue) -> flagValue.set(flag.invoke(flags)));
    }

    protected final List<Flag> getFlags(){
        ArrayList<Flag> flags = new ArrayList<>();
        getChildren().forEach(it -> flags.addAll(it.getFlags()));
        getOwnProperties().forEach((flag, flagValue) -> {
            if (flagValue.get()) {flags.add(flag);}
        });
        return flags;
    }

    public final int asInt()
    {
        return FlagsKt.flagsOf(getFlags().toArray(new Flag[0]));
    }

}
