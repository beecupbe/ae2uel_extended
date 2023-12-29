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

package appeng.block.solars;


import appeng.block.AEBaseTileBlock;
import appeng.core.AEConfig;
import appeng.core.localization.GuiText;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public final class BlockPanelPerfect extends AEBaseTileBlock {

    public BlockPanelPerfect() {
        super(Material.IRON);
        this.setHardness(6F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add(GuiText.PassiveGenerationDay.getLocal() + " " + AEConfig.instance().getPerfectSolarPanelGenPerTick() + " AE/t");
        tooltip.add(GuiText.PassiveGenerationNight.getLocal() + " " + AEConfig.instance().getPerfectSolarPanelGenPerTickNight() + " AE/t");
        tooltip.add(GuiText.MaxSolarCapacity.getLocal() + " " + AEConfig.instance().getPerfectSolarPanelCapacity() + " AE");
        tooltip.add(GuiText.UseNetToolToControlSolars.getLocal());
        super.addInformation(stack, player, tooltip, advanced);
    }
}
