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

package appeng.container.implementations;


import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.implementations.IUpgradeableCellContainer;
import appeng.api.implementations.guiobjects.IPortableCell;
import appeng.api.networking.security.IActionHost;
import appeng.container.interfaces.IInventorySlotAware;
import appeng.container.slot.SlotRestrictedInput;
import appeng.core.AEConfig;
import appeng.core.localization.PlayerMessages;
import appeng.helpers.WirelessTerminalGuiObject;
import appeng.parts.automation.StackUpgradeInventory;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.Platform;
import appeng.util.inv.IAEAppEngInventory;
import appeng.util.inv.InvOperation;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;


public class ContainerMEPortableTerminal extends ContainerMEMonitorable implements IUpgradeableCellContainer, IAEAppEngInventory, IInventorySlotAware {

    protected final WirelessTerminalGuiObject wirelessTerminalGUIObject;
    private final int slot;
    private double powerMultiplier = 0.5;
    private int ticks = 0;

    protected AppEngInternalInventory upgrades;
    protected SlotRestrictedInput magnetSlot;

    public ContainerMEPortableTerminal(InventoryPlayer ip, WirelessTerminalGuiObject guiObject, boolean bindInventory) {
        super(ip, guiObject, guiObject, bindInventory);
        if (guiObject != null) {
            final int slotIndex = ((IInventorySlotAware) guiObject).getInventorySlot();
            if (!((IInventorySlotAware) guiObject).isBaubleSlot()) {
                this.lockPlayerInventorySlot(slotIndex);
            }
            this.slot = slotIndex;
        } else {
            this.slot = -1;
            this.lockPlayerInventorySlot(ip.currentItem);
        }

        this.bindPlayerInventory(ip, 0, 0);

        this.wirelessTerminalGUIObject = guiObject;
        this.upgrades = new StackUpgradeInventory(wirelessTerminalGUIObject.getItemStack(), this, 2);
        this.loadFromNBT();
        this.setupUpgrades();
    }

    public ContainerMEPortableTerminal(InventoryPlayer ip, IPortableCell guiObject) {
        super(ip, guiObject, guiObject, true);
        if (guiObject != null) {
            final int slotIndex = ((IInventorySlotAware) guiObject).getInventorySlot();
            if (!((IInventorySlotAware) guiObject).isBaubleSlot()) {
                this.lockPlayerInventorySlot(slotIndex);
            }
            this.slot = slotIndex;
        } else {
            this.slot = -1;
            this.lockPlayerInventorySlot(ip.currentItem);
        }
        this.wirelessTerminalGUIObject = (WirelessTerminalGuiObject) guiObject;
        this.upgrades = new StackUpgradeInventory(wirelessTerminalGUIObject.getItemStack(), this, 2);
        this.loadFromNBT();
        this.setupUpgrades();
    }

    @Override
    public void detectAndSendChanges() {
        if (Platform.isServer()) {

            final ItemStack currentItem;
            if (wirelessTerminalGUIObject.isBaubleSlot()) {
                currentItem = BaublesApi.getBaublesHandler(this.getPlayerInv().player).getStackInSlot(this.slot);
            } else {
                currentItem = this.slot < 0 ? this.getPlayerInv().getCurrentItem() : this.getPlayerInv().getStackInSlot(this.slot);
            }

            if (currentItem.isEmpty()) {
                this.setValidContainer(false);
            } else if (!this.wirelessTerminalGUIObject.getItemStack().isEmpty() && currentItem != this.wirelessTerminalGUIObject.getItemStack()) {
                if (ItemStack.areItemsEqual(this.wirelessTerminalGUIObject.getItemStack(), currentItem)) {
                    if (wirelessTerminalGUIObject.isBaubleSlot()) {
                        BaublesApi.getBaublesHandler(this.getPlayerInv().player).setStackInSlot(this.slot, this.wirelessTerminalGUIObject.getItemStack());
                    } else {
                        this.getPlayerInv().setInventorySlotContents(this.slot, this.wirelessTerminalGUIObject.getItemStack());
                    }
                } else {
                    this.setValidContainer(false);
                }
            }

            // drain 1 ae t
            this.ticks++;
            if (this.ticks > 10) {
                double ext = this.wirelessTerminalGUIObject.extractAEPower(this.getPowerMultiplier() * this.ticks, Actionable.MODULATE, PowerMultiplier.CONFIG);
                if (ext < this.getPowerMultiplier() * this.ticks) {
                    if (Platform.isServer() && this.isValidContainer()) {
                        this.getPlayerInv().player.sendMessage(PlayerMessages.DeviceNotPowered.get());
                    }

                    this.setValidContainer(false);
                }
                this.ticks = 0;
            }

            if (!this.wirelessTerminalGUIObject.rangeCheck()) {
                if (Platform.isServer() && this.isValidContainer()) {
                    this.getPlayerInv().player.sendMessage(PlayerMessages.OutOfRange.get());
                }

                this.setValidContainer(false);
            } else {
                this.setPowerMultiplier(AEConfig.instance().wireless_getDrainRate(this.wirelessTerminalGUIObject.getRange()));
            }

            super.detectAndSendChanges();
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId >= 0 && slotId < this.inventorySlots.size()) {
            if (clickTypeIn == ClickType.PICKUP && dragType == 1) {
                if (this.inventorySlots.get(slotId) == magnetSlot) {
                    ItemStack itemStack = magnetSlot.getStack();
                    if (!magnetSlot.getStack().isEmpty()) {
                        NBTTagCompound tag = itemStack.getTagCompound();
                        if (tag == null) {
                            tag = new NBTTagCompound();
                        }
                        if (tag.hasKey("enabled")) {
                            boolean e = tag.getBoolean("enabled");
                            tag.setBoolean("enabled", !e);
                        } else {
                            tag.setBoolean("enabled", false);
                        }
                        magnetSlot.getStack().setTagCompound(tag);
                        magnetSlot.onSlotChanged();
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    protected IActionHost getActionHost() {
        return this.wirelessTerminalGUIObject;
    }

    private double getPowerMultiplier() {
        return this.powerMultiplier;
    }

    void setPowerMultiplier(final double powerMultiplier) {
        this.powerMultiplier = powerMultiplier;
    }

    @Override
    public int availableUpgrades() {
        return 1;
    }

    @Override
    public void setupUpgrades() {
        if (wirelessTerminalGUIObject != null) {
            for (int upgradeSlot = 0; upgradeSlot < availableUpgrades(); upgradeSlot++) {
                this.magnetSlot = new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.UPGRADES, upgrades, upgradeSlot, 206, 135 + upgradeSlot * 18, this.getInventoryPlayer());
                this.magnetSlot.setNotDraggable();
                this.addSlotToContainer(magnetSlot);
            }
        }
    }

    @Override
    public void saveChanges() {
        if (Platform.isServer()) {
            NBTTagCompound tag = new NBTTagCompound();
            this.upgrades.writeToNBT(tag, "upgrades");

            this.wirelessTerminalGUIObject.saveChanges(tag);
        }
    }

    protected void loadFromNBT() {
        NBTTagCompound data = wirelessTerminalGUIObject.getItemStack().getTagCompound();
        if (data != null) {
            upgrades.readFromNBT(wirelessTerminalGUIObject.getItemStack().getTagCompound().getCompoundTag("upgrades"));
        }
    }

    @Override
    public void onChangeInventory(IItemHandler inv, int slot, InvOperation mc, ItemStack removedStack, ItemStack newStack) {

    }

    @Override
    public int getInventorySlot() {
        return wirelessTerminalGUIObject.getInventorySlot();
    }

    @Override
    public boolean isBaubleSlot() {
        return wirelessTerminalGUIObject.isBaubleSlot();
    }
}
