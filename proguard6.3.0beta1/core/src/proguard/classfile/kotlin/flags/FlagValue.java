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

import java.util.function.*;

/**
 * A value of a flag, which is a mutable boolean
 */
public class FlagValue
{
    public final Supplier<Boolean> getter;
    public final Consumer<Boolean> setter;


    public FlagValue(Supplier<Boolean> getter, Consumer<Boolean> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }


    public void set(boolean newValue)
    {
        setter.accept(newValue);
    }


    public boolean get()
    {
        return getter.get();
    }


    /**
     * @return a FlagValue which behaves as a transparant negation to the original flag
     */
    public FlagValue negation()
    {
        FlagValue outer = this;
        return new FlagValue(getter, setter)
        {
            public void set(boolean newValue)
            {
                outer.set(!newValue);
            }


            public boolean get()
            {
                return !outer.get();
            }
        };
    }
}
