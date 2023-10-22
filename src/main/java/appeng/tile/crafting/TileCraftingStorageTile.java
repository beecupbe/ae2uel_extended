/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
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

package appeng.tile.crafting;


import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import appeng.block.crafting.BlockCraftingUnit;
import net.minecraft.item.ItemStack;

import java.util.Optional;


public class TileCraftingStorageTile extends TileCraftingTile {
    private static final int KILO_SCALAR = 1024;

    @Override
    protected ItemStack getItemFromTile(final Object obj) {
        final IBlocks blocks = AEApi.instance().definitions().blocks();
        final int storage = ((TileCraftingTile) obj).getStorageBytes() / KILO_SCALAR;

        Optional<ItemStack> is = switch (storage) {
            case 1 -> blocks.craftingStorage1k().maybeStack(1);
            case 4 -> blocks.craftingStorage4k().maybeStack(1);
            case 16 -> blocks.craftingStorage16k().maybeStack(1);
            case 64 -> blocks.craftingStorage64k().maybeStack(1);
            case 1024 -> blocks.craftingStorage1mb().maybeStack(1);
            case 4096 -> blocks.craftingStorage4mb().maybeStack(1);
            case 16384 -> blocks.craftingStorage16mb().maybeStack(1);
            case 65536 -> blocks.craftingStorage64mb().maybeStack(1);
            case 262144 -> blocks.craftingStorage256mb().maybeStack(1);
            case 1048576 -> blocks.craftingStorage1gb().maybeStack(1);
            case 2097151 -> blocks.craftingStorage15gb().maybeStack(1);
            default -> Optional.empty();
        };

        return is.orElseGet(() -> super.getItemFromTile(obj));
    }

    @Override
    public boolean isAccelerator() {
        return false;
    }

    @Override
    public boolean isAcceleratorx4() {
        return false;
    }
    @Override
    public boolean isAcceleratorx16() {
        return false;
    }
    @Override
    public boolean isAcceleratorx64() {
        return false;
    }
    @Override
    public boolean isAcceleratorx128() {
        return false;
    }
    @Override
    public boolean isAcceleratorx256() {
        return false;
    }
    @Override
    public boolean isAcceleratorx512() {
        return false;
    }
    @Override
    public boolean isAcceleratorx1024() {
        return false;
    }
    @Override
    public boolean isAcceleratorCreative() {
        return false;
    }



    @Override
    public boolean isStorage() {
        return true;
    }

    @Override
    public int getStorageBytes() {
        if (this.world == null || this.notLoaded() || this.isInvalid()) {
            return 0;
        }

        final BlockCraftingUnit unit = (BlockCraftingUnit) this.world.getBlockState(this.pos).getBlock();
        return switch (unit.type) {
            default -> KILO_SCALAR;
            case STORAGE_4K -> 4 * KILO_SCALAR;
            case STORAGE_16K -> 16 * KILO_SCALAR;
            case STORAGE_64K -> 64 * KILO_SCALAR;
            case STORAGE_1MB -> 1024 * KILO_SCALAR;
            case STORAGE_4MB -> 4096 * KILO_SCALAR;
            case STORAGE_16MB -> 16384 * KILO_SCALAR;
            case STORAGE_64MB -> 65536 * KILO_SCALAR;
            case STORAGE_256MB -> 262144 * KILO_SCALAR;
            case STORAGE_1GB -> 1048576 * KILO_SCALAR;
            case STORAGE_15GB -> Integer.MAX_VALUE;
        };
    }
}
