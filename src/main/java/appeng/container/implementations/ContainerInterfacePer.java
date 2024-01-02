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
import appeng.container.slot.IOptionalSlotHost;
import appeng.container.slot.SlotFake;
import appeng.container.slot.SlotOversized;
import appeng.helpers.DualityInterface;
import appeng.helpers.DualityInterfacePer;
import appeng.helpers.IInterfaceHost;
import appeng.util.Platform;
import net.minecraft.entity.player.InventoryPlayer;


public class ContainerInterfacePer extends ContainerUpgradeable implements IOptionalSlotHost {

    private final DualityInterfacePer myDuality;

    @GuiSync(3)
    public YesNo bMode = YesNo.NO;

    @GuiSync(4)
    public YesNo iTermMode = YesNo.YES;

    @GuiSync(7)
    public int patternExpansions = 0;

    public ContainerInterfacePer(final InventoryPlayer ip, final IInterfaceHost te) {
        super(ip, te.getInterfaceDualityPer().getHost());

        this.myDuality = te.getInterfaceDualityPer();

        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotFake(this.myDuality.getConfig(), x, 8 + 18 * x, 23));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotFake(this.myDuality.getConfig(), x + 9, 8 + 18 * x, 59));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotFake(this.myDuality.getConfig(), x + 18, 8 + 18 * x, 95));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotFake(this.myDuality.getConfig(), x + 27, 8 + 18 * x, 131));
        }

        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), x, 8 + 18 * x, 23 + 18));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), x + 9, 8 + 18 * x, 59 + 18));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), x + 18, 8 + 18 * x, 95 + 18));
        }
        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTSPER / 4; x++) {
            this.addSlotToContainer(new SlotOversized(this.myDuality.getStorage(), x + 27, 8 + 18 * x, 131 + 18));
        }

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
        this.setBlockingMode((YesNo) cm.getSetting(Settings.BLOCK));
        this.setInterfaceTerminalMode((YesNo) cm.getSetting(Settings.INTERFACE_TERMINAL));
    }

    public YesNo getBlockingMode() {
        return this.bMode;
    }

    private void setBlockingMode(final YesNo bMode) {
        this.bMode = bMode;
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
