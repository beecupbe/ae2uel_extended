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


import appeng.client.gui.AEBaseGui;
import appeng.client.gui.widgets.GuiNumberBox;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.container.implementations.ContainerPriority;
import appeng.core.AEConfig;
import appeng.core.AELog;
import appeng.core.localization.GuiText;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketSwitchGuis;
import appeng.core.sync.packets.PacketValueConfig;
import appeng.helpers.IPriorityHost;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import java.io.IOException;


public class GuiPriority extends AEBaseGui {

    private GuiNumberBox priority;
    private GuiTabButton originalGuiBtn;

    private GuiButton plus1;
    private GuiButton plus10;
    private GuiButton plus100;
    private GuiButton plus1000;
    private GuiButton plus10000;
    private GuiButton plus5;
    private GuiButton plus50;
    private GuiButton plus500;
    private GuiButton plus5000;
    private GuiButton plus50000;
    private GuiButton minus1;
    private GuiButton minus10;
    private GuiButton minus100;
    private GuiButton minus1000;
    private GuiButton minus10000;
    private GuiButton minus5;
    private GuiButton minus50;
    private GuiButton minus500;
    private GuiButton minus5000;
    private GuiButton minus50000;
    private GuiButton clearAll;

    private GuiBridge OriginalGui;

    public GuiPriority(final InventoryPlayer inventoryPlayer, final IPriorityHost te) {
        super(new ContainerPriority(inventoryPlayer, te));
    }

    @Override
    public void initGui() {
        super.initGui();

        final int a = AEConfig.instance().priorityByStacksAmounts(0);
        final int b = AEConfig.instance().priorityByStacksAmounts(1);
        final int c = AEConfig.instance().priorityByStacksAmounts(2);
        final int d = AEConfig.instance().priorityByStacksAmounts(3);

        //Button plus
        this.buttonList.add(this.plus1 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 35, 35, 20, "+" + a));
        this.buttonList.add(this.plus10 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 60, 35, 20, "+" + b));
        this.buttonList.add(this.plus100 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 85, 35, 20, "+" + c));
        this.buttonList.add(this.plus1000 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 110, 35, 20, "+" + d));
        this.buttonList.add(this.plus10000 = new GuiButton(0, this.guiLeft + 20, this.guiTop + 135, 35, 20, "+" + 10000));
        //Button plus
        this.buttonList.add(this.plus5 = new GuiButton(0, this.guiLeft + 55, this.guiTop + 35, 35, 20, "+" + 5));
        this.buttonList.add(this.plus50 = new GuiButton(0, this.guiLeft + 55, this.guiTop + 60, 35, 20, "+" + 50));
        this.buttonList.add(this.plus500 = new GuiButton(0, this.guiLeft + 55, this.guiTop + 85, 35, 20, "+" + 500));
        this.buttonList.add(this.plus5000 = new GuiButton(0, this.guiLeft + 55, this.guiTop + 110, 35, 20, "+" + 5000));
        this.buttonList.add(this.plus50000 = new GuiButton(0, this.guiLeft + 55, this.guiTop + 135, 35, 20, "+" + 50000));
        //Button minus
        this.buttonList.add(this.minus1 = new GuiButton(0, this.guiLeft + 90, this.guiTop + 35, 35, 20, "-" + a));
        this.buttonList.add(this.minus10 = new GuiButton(0, this.guiLeft + 90, this.guiTop + 60, 35, 20, "-" + b));
        this.buttonList.add(this.minus100 = new GuiButton(0, this.guiLeft + 90, this.guiTop + 85, 35, 20, "-" + c));
        this.buttonList.add(this.minus1000 = new GuiButton(0, this.guiLeft + 90, this.guiTop + 110, 35, 20, "-" + d));
        this.buttonList.add(this.minus10000 = new GuiButton(0, this.guiLeft + 90, this.guiTop + 135, 35, 20, "-" + 10000));
        //Button minus
        this.buttonList.add(this.minus5 = new GuiButton(0, this.guiLeft + 125, this.guiTop + 35, 35, 20, "-" + 5));
        this.buttonList.add(this.minus50 = new GuiButton(0, this.guiLeft + 125, this.guiTop + 60, 35, 20, "-" + 50));
        this.buttonList.add(this.minus500 = new GuiButton(0, this.guiLeft + 125, this.guiTop + 85, 35, 20, "-" + 500));
        this.buttonList.add(this.minus5000 = new GuiButton(0, this.guiLeft + 125, this.guiTop + 110, 35, 20, "-" + 5000));
        this.buttonList.add(this.minus50000 = new GuiButton(0, this.guiLeft + 125, this.guiTop + 135, 35, 20, "-" + 50000));
        this.buttonList.add(this.clearAll = new GuiButton(0, this.guiLeft + 20, this.guiTop + 160, 140, 20, "" + GuiText.ClearAll.getLocal()));

        final ContainerPriority con = ((ContainerPriority) this.inventorySlots);
        final ItemStack myIcon = con.getPriorityHost().getItemStackRepresentation();
        this.OriginalGui = con.getPriorityHost().getGuiBridge();

        if (this.OriginalGui != null && !myIcon.isEmpty()) {
            this.buttonList.add(this.originalGuiBtn = new GuiTabButton(this.guiLeft + 154, this.guiTop, myIcon, myIcon.getDisplayName(), this.itemRender));
        }

        //Button String
        this.priority = new GuiNumberBox(this.fontRenderer, this.guiLeft + 57, this.guiTop + 22, 80, this.fontRenderer.FONT_HEIGHT, Long.class);
        this.priority.setEnableBackgroundDrawing(false);
        this.priority.setMaxStringLength(16);
        this.priority.setTextColor(0xFFFFFF);
        this.priority.setVisible(true);
        this.priority.setFocused(true);
        ((ContainerPriority) this.inventorySlots).setTextField(this.priority);
    }

    @Override
    public void drawFG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.fontRenderer.drawString(GuiText.Priority.getLocal(), 8, 6, 4210752);
    }

    @Override
    public void drawBG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.bindTexture("guis/priority.png");
        this.xSize = 256;
        this.ySize = 256;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        this.priority.drawTextBox();
    }

    @Override
    protected void actionPerformed(final GuiButton btn) throws IOException {
        super.actionPerformed(btn);

        if (btn == this.originalGuiBtn) {
            NetworkHandler.instance().sendToServer(new PacketSwitchGuis(this.OriginalGui));
        }
        //Button +-
        final boolean isPlus =
                btn == this.plus1 ||
                        btn == this.plus10 ||
                        btn == this.plus100 ||
                        btn == this.plus1000 ||
                        btn == this.plus10000 ||
                        btn == this.plus5 ||
                        btn == this.plus50 ||
                        btn == this.plus500 ||
                        btn == this.plus5000 ||
                        btn == this.plus50000;
        final boolean isMinus =
                btn == this.minus1 ||
                        btn == this.minus10 ||
                        btn == this.minus100 ||
                        btn == this.minus1000 ||
                        btn == this.minus10000 ||
                        btn == this.minus5 ||
                        btn == this.minus50 ||
                        btn == this.minus500 ||
                        btn == this.minus5000 ||
                        btn == this.minus50000;
        final boolean clear = btn == this.clearAll;
        if (isPlus || isMinus) {
            this.addQty(this.getQty(btn));
        } else if (clear) {
            this.clearText();
        }
    }
    private void addQty(final int i) {
        try {
            String out = this.priority.getText();

            boolean fixed = false;
            while (out.startsWith("0") && out.length() > 1) {
                out = out.substring(1);
                fixed = true;
            }

            if (fixed) {
                this.priority.setText(out);
            }

            if (out.isEmpty()) {
                out = "0";
            }
            if (out.startsWith("-")) {
                this.priority.setText("0");
            }

            long result = Long.parseLong(out);
            result += i;

            this.priority.setText(out = Long.toString(result));

            NetworkHandler.instance().sendToServer(new PacketValueConfig("PriorityHost.Priority", out));
        } catch (final NumberFormatException e) {
            // nope..
            this.priority.setText("0");
        } catch (final IOException e) {
            AELog.debug(e);
        }
    }

    private void clearText() {
        try {
            String out = this.priority.getText();
            this.priority.setText("0");
            out = "0";

            NetworkHandler.instance().sendToServer(new PacketValueConfig("PriorityHost.Priority", out));
        } catch (final NumberFormatException e) {
            // :thinking:
            this.priority.setText("0");
        } catch (final IOException e) {
            AELog.debug(e);
        }
    }
    @Override
    protected void keyTyped(final char character, final int key) throws IOException {
        if (!this.checkHotbarKeys(key)) {
            if ((key == 211 || key == 205 || key == 203 || key == 14 || character == '-' || Character.isDigit(character)) && this.priority
                    .textboxKeyTyped(character, key)) {
                try {
                    String out = this.priority.getText();

                    boolean fixed = false;
                    while (out.startsWith("0") && out.length() > 1) {
                        out = out.substring(1);
                        fixed = true;
                    }

                    if (fixed) {
                        this.priority.setText(out);
                    }

                    if (out.isEmpty()) {
                        out = "0";
                    }

                    NetworkHandler.instance().sendToServer(new PacketValueConfig("PriorityHost.Priority", out));
                } catch (final IOException e) {
                    AELog.debug(e);
                }
            } else {
                super.keyTyped(character, key);
            }
        }
    }

    protected String getBackground() {
        return "guis/priority.png";
    }
}
