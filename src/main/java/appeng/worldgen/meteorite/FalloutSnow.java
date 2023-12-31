/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2015, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.worldgen.meteorite;


import appeng.api.definitions.IBlockDefinition;
import net.minecraft.init.Blocks;


public class FalloutSnow extends FalloutCopy {
    private static final double SNOW_THRESHOLD = 0.7;
    private static final double ICE_THRESHOLD = 0.5;
    private final MeteoriteBlockPutter putter;

    public FalloutSnow(final IMeteoriteWorld w, final int x, final int y, final int z, final MeteoriteBlockPutter putter, final IBlockDefinition skyStoneDefinition) {
        super(w, x, y, z, putter, skyStoneDefinition);
        this.putter = putter;
    }

    @Override
    public int adjustCrater() {
        return 2;
    }

    @Override
    public void getOther(final IMeteoriteWorld w, final int x, final int y, final int z, final double a) {
        if (a > SNOW_THRESHOLD) {
            this.putter.put(w, x, y, z, Blocks.SNOW);
        } else if (a > ICE_THRESHOLD) {
            this.putter.put(w, x, y, z, Blocks.ICE);
        }
    }
}