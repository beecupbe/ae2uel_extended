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

package appeng.container.implementations;


import appeng.api.config.SecurityPermissions;
import appeng.api.config.Settings;
import appeng.api.config.Upgrades;
import appeng.api.config.YesNo;
import appeng.api.util.IConfigManager;
import appeng.container.guisync.GuiSync;
import appeng.container.slot.*;
import appeng.helpers.DualityInterface;
import appeng.helpers.DualityInterfacePatt;
import appeng.helpers.IInterfaceHost;
import appeng.tile.inventory.AppEngInternalOversizedInventory;
import appeng.util.Platform;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;


public class ContainerInterfacePatt extends ContainerUpgradeable implements IOptionalSlotHost {

    private final DualityInterfacePatt myDuality;

    @GuiSync(3)
    public YesNo bMode = YesNo.NO;

    @GuiSync(4)
    public YesNo iTermMode = YesNo.YES;

    @GuiSync(7)
    public int patternExpansions = 0;

    public ContainerInterfacePatt(final InventoryPlayer ip, final IInterfaceHost te) {
        super(ip, te.getInterfaceDualityPatt().getHost());

        this.myDuality = te.getInterfaceDualityPatt();

        for (int row = 0; row < 8; ++row) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new OptionalSlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, this.myDuality
                        .getPatterns(), this, x + row * 9, 8 + 18 * x, 25 + (18 * row), row, this.getInventoryPlayer()).setStackLimit(1));
            }
        }

        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 0, 186, 92));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 1, 204, 92));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 2, 222, 92));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 3, 186, 92 + 18));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 4, 204, 92 + 18));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 5, 222, 92 + 18));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 6, 186, 92 + 18 * 2));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 7, 204, 92 + 18 * 2));
        this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), 8, 222, 92 + 18 * 2));

    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId >= 0 && slotId < this.inventorySlots.size()) {
            if (this.inventorySlots.get(slotId) instanceof SlotOversized) {
                ((AppEngInternalOversizedInventory) ((SlotOversized) this.inventorySlots.get(slotId)).getItemHandler()).limitExtraction(true);
                ItemStack ret = super.slotClick(slotId, dragType, clickTypeIn, player);
                ((AppEngInternalOversizedInventory) ((SlotOversized) this.inventorySlots.get(slotId)).getItemHandler()).limitExtraction(false);
                return ret;
            }
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    protected int getHeight() {
        return 256;
    }

    @Override
    protected void setupConfig() {
        this.setupUpgrades();
    }

    @Override
    public int availableUpgrades() {
        return 4;
    }

    @Override
    public boolean isSlotEnabled(final int idx) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        this.verifyPermissions(SecurityPermissions.BUILD, false);

        if (patternExpansions != getPatternUpgrades()) {
            patternExpansions = getPatternUpgrades();
            this.myDuality.dropExcessPatterns();
        }
        super.detectAndSendChanges();
    }

    @Override
    public void onUpdate(final String field, final Object oldValue, final Object newValue) {
        super.onUpdate(field, oldValue, newValue);
        if (Platform.isClient() && field.equals("patternExpansions"))
            this.myDuality.dropExcessPatterns();
    }

    @Override
    protected void loadSettingsFromHost(final IConfigManager cm) {
        this.setBlockingMode();
        this.setInterfaceTerminalMode((YesNo) cm.getSetting(Settings.INTERFACE_TERMINAL));
    }

    public YesNo getBlockingMode() {
        return this.bMode;
    }

    private void setBlockingMode() {
        this.bMode = YesNo.NO;
    }

    public YesNo getInterfaceTerminalMode() {
        return this.iTermMode;
    }

    private void setInterfaceTerminalMode(final YesNo iTermMode) {
        this.iTermMode = iTermMode;
    }

    public int getPatternUpgrades() {
        return this.myDuality.getInstalledUpgrades(Upgrades.PATTERN_EXPANSION);
    }
}
