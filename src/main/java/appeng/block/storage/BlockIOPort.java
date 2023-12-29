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

package appeng.block.storage;

import appeng.api.config.Upgrades;
import appeng.api.util.AEPartLocation;
import appeng.block.AEBaseTileBlock;
import appeng.core.sync.GuiBridge;
import appeng.tile.storage.TileIOPort;
import appeng.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class BlockIOPort extends AEBaseTileBlock {

    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockIOPort() {
        super(Material.IRON);
        setDefaultState(getDefaultState().withProperty(POWERED, false));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileIOPort te = this.getTileEntity(worldIn, pos);
        boolean powred = te != null && te.isActive();

        return super.getActualState(state, worldIn, pos)
                .withProperty(POWERED, powred);
    }

    @Override
    protected IProperty[] getAEStates() {
        return new IProperty[]{POWERED};
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        final TileIOPort te = this.getTileEntity(world, pos);
        if (te != null) {
            if (te.getInstalledUpgrades(Upgrades.REDSTONE) != 0) {
                te.updateRedstoneState();
            }
        }
    }

    @Override
    public boolean onActivated(final World w, final BlockPos pos, final EntityPlayer p, final EnumHand hand, final @Nullable ItemStack heldItem, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (p.isSneaking()) {
            return false;
        }

        final TileIOPort tg = this.getTileEntity(w, pos);
        if (tg != null) {
            if (Platform.isServer()) {
                Platform.openGUI(p, tg, AEPartLocation.fromFacing(side), GuiBridge.GUI_IOPORT);
            }
            return true;
        }
        return false;
    }
}