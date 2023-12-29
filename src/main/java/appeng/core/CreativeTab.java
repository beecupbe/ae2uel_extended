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

package appeng.core;


import appeng.api.AEApi;
import appeng.api.definitions.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Optional;


public final class CreativeTab extends CreativeTabs {
    public static CreativeTab instance = null;

    public CreativeTab() {
        super("appliedenergistics2");
    }

    static void init() {
        instance = new CreativeTab();
    }

    @Override
    public ItemStack getIcon() {
        return this.createIcon();
    }

    @Override
    public ItemStack createIcon() {
        final IDefinitions definitions = AEApi.instance().definitions();
        final IBlocks blocks = definitions.blocks();

        return this.findFirst(blocks.controller());
    }

    private ItemStack findFirst(final IItemDefinition... choices) {
        for (final IItemDefinition definition : choices) {
            Optional<ItemStack> maybeIs = definition.maybeStack(1);
            if (maybeIs.isPresent()) {
                return maybeIs.get();
            }
        }

        return new ItemStack(Blocks.CHEST);
    }
}