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

package appeng.client.gui.implementations;


import appeng.container.implementations.ContainerWirelessPatternTerminal;
import appeng.helpers.WirelessTerminalGuiObject;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.InventoryPlayer;


public class GuiWirelessPatternTerminal extends GuiPatternTerm {

    public GuiWirelessPatternTerminal(final InventoryPlayer inventoryPlayer, final WirelessTerminalGuiObject te) {
        super(inventoryPlayer, te, new ContainerWirelessPatternTerminal(inventoryPlayer, te));
        this.setReservedSpace(81);
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture("guis/wirelessupgrades.png");
        Gui.drawModalRectWithCustomSizedTexture(offsetX + 198, offsetY + 127, 0, 0, 32, 32, 32, 32);
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
    }
}
