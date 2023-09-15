package appeng.client.gui.implementations;

import appeng.api.config.ActionItems;
import appeng.api.config.ItemSubstitution;
import appeng.api.config.Settings;
import appeng.api.storage.ITerminalHost;
import appeng.client.gui.widgets.GuiImgButton;
import appeng.client.gui.widgets.GuiTabButton;
import appeng.container.implementations.ContainerExpandedProcessingPatternTerm;
import appeng.container.interfaces.IJEIGhostIngredients;
import appeng.container.slot.AppEngSlot;
import appeng.container.slot.SlotFake;
import appeng.core.AELog;
import appeng.core.localization.GuiText;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketInventoryAction;
import appeng.core.sync.packets.PacketValueConfig;
import appeng.helpers.InventoryAction;
import appeng.util.item.AEItemStack;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;


public class GuiExpandedProcessingPatternTerm extends GuiMEMonitorable implements IJEIGhostIngredients {
    private static final String BACKGROUND_EXPANDED_PROCESSING_MODE = "guis/pattern_processing_expanded.png";

    private static final String SUBSITUTION_DISABLE = "0";
    private static final String SUBSITUTION_ENABLE = "1";

    private static final String CRAFTMODE_CRFTING = "1";
    private static final String CRAFTMODE_PROCESSING = "0";

    private GuiTabButton tabCraftButton;
    private GuiTabButton tabProcessButton;
    private GuiImgButton substitutionsEnabledBtn;
    private GuiImgButton substitutionsDisabledBtn;
    private GuiImgButton encodeBtn;
    private GuiImgButton clearBtn;
    private GuiImgButton x2Btn;
    private GuiImgButton x3Btn;
    private GuiImgButton plusOneBtn;
    private GuiImgButton divTwoBtn;
    private GuiImgButton divThreeBtn;
    private GuiImgButton minusOneBtn;
    private GuiImgButton maxCountBtn;
    public Map<IGhostIngredientHandler.Target<?>, Object> mapTargetSlot = new HashMap<>();

    public GuiExpandedProcessingPatternTerm(final InventoryPlayer inventoryPlayer, final ITerminalHost te) {
        super(inventoryPlayer, te, new ContainerExpandedProcessingPatternTerm(inventoryPlayer, te));
        this.setReservedSpace(81);
    }

    @Override
    protected void actionPerformed(final GuiButton btn) {
        super.actionPerformed(btn);

        try {

            if (this.tabCraftButton == btn || this.tabProcessButton == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.CraftMode", this.tabProcessButton == btn ? CRAFTMODE_CRFTING : CRAFTMODE_PROCESSING));
            }

            if (this.encodeBtn == btn) {
                if (isShiftKeyDown()) {
                    NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.Encode", "2"));
                } else {
                    NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.Encode", "1"));
                }
            }

            if (this.clearBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.Clear", "1"));
            }

            if (this.x2Btn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.MultiplyByTwo", "1"));
            }

            if (this.x3Btn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.MultiplyByThree", "1"));
            }

            if (this.divTwoBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.DivideByTwo", "1"));
            }

            if (this.divThreeBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.DivideByThree", "1"));
            }

            if (this.plusOneBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.IncreaseByOne", "1"));
            }

            if (this.minusOneBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.DecreaseByOne", "1"));
            }

            if (this.maxCountBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.MaximizeCount", "1"));
            }

            if (this.substitutionsEnabledBtn == btn || this.substitutionsDisabledBtn == btn) {
                NetworkHandler.instance().sendToServer(new PacketValueConfig("PatternTerminal.Substitute", this.substitutionsEnabledBtn == btn ? SUBSITUTION_DISABLE : SUBSITUTION_ENABLE));
            }
        } catch (final IOException e) {
            AELog.error(e);
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        this.tabCraftButton = new GuiTabButton(this.guiLeft + 173, this.guiTop + this.ySize - 177, new ItemStack(Blocks.CRAFTING_TABLE), GuiText.CraftingPattern.getLocal(), this.itemRender);
        this.buttonList.add(this.tabCraftButton);

        this.tabProcessButton = new GuiTabButton(this.guiLeft + 173, this.guiTop + this.ySize - 177, new ItemStack(Blocks.FURNACE), GuiText.ProcessingPattern.getLocal(), this.itemRender);
        this.buttonList.add(this.tabProcessButton);

        this.substitutionsEnabledBtn = new GuiImgButton(this.guiLeft + 84, this.guiTop + this.ySize - 163, Settings.ACTIONS, ItemSubstitution.ENABLED);
        this.substitutionsEnabledBtn.setHalfSize(true);
        this.buttonList.add(this.substitutionsEnabledBtn);

        this.substitutionsDisabledBtn = new GuiImgButton(this.guiLeft + 84, this.guiTop + this.ySize - 163, Settings.ACTIONS, ItemSubstitution.DISABLED);
        this.substitutionsDisabledBtn.setHalfSize(true);
        this.buttonList.add(this.substitutionsDisabledBtn);

        this.clearBtn = new GuiImgButton(this.guiLeft + 74, this.guiTop + this.ySize - 163, Settings.ACTIONS, ActionItems.CLOSE);
        this.clearBtn.setHalfSize(true);
        this.buttonList.add(this.clearBtn);

        this.x3Btn = new GuiImgButton(this.guiLeft + 131, this.guiTop + this.ySize - 158, Settings.ACTIONS, ActionItems.MULTIPLY_BY_THREE);
        this.x3Btn.setHalfSize(true);
        this.buttonList.add(this.x3Btn);

        this.x2Btn = new GuiImgButton(this.guiLeft + 131, this.guiTop + this.ySize - 148, Settings.ACTIONS, ActionItems.MULTIPLY_BY_TWO);
        this.x2Btn.setHalfSize(true);
        this.buttonList.add(this.x2Btn);

        this.plusOneBtn = new GuiImgButton(this.guiLeft + 131, this.guiTop + this.ySize - 110, Settings.ACTIONS, ActionItems.INCREASE_BY_ONE);
        this.plusOneBtn.setHalfSize(true);
        this.buttonList.add(this.plusOneBtn);

        this.divThreeBtn = new GuiImgButton(this.guiLeft + 87, this.guiTop + this.ySize - 158, Settings.ACTIONS, ActionItems.DIVIDE_BY_THREE);
        this.divThreeBtn.setHalfSize(true);
        this.buttonList.add(this.divThreeBtn);

        this.divTwoBtn = new GuiImgButton(this.guiLeft + 87, this.guiTop + this.ySize - 148, Settings.ACTIONS, ActionItems.DIVIDE_BY_TWO);
        this.divTwoBtn.setHalfSize(true);
        this.buttonList.add(this.divTwoBtn);

        this.minusOneBtn = new GuiImgButton(this.guiLeft + 87, this.guiTop + this.ySize - 110, Settings.ACTIONS, ActionItems.DECREASE_BY_ONE);
        this.minusOneBtn.setHalfSize(true);
        this.buttonList.add(this.minusOneBtn);

        //this.maxCountBtn = new GuiImgButton( this.guiLeft + 128, this.guiTop + this.ySize - 108, Settings.ACTIONS, ActionItems.MAX_COUNT );
        //this.maxCountBtn.setHalfSize( true );
        //this.buttonList.add( this.maxCountBtn );

        this.encodeBtn = new GuiImgButton(this.guiLeft + 147, this.guiTop + this.ySize - 142, Settings.ACTIONS, ActionItems.ENCODE);
        this.buttonList.add(this.encodeBtn);
    }

    @Override
    public void drawFG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.tabCraftButton.visible = false;
        this.tabProcessButton.visible = true;
        this.substitutionsEnabledBtn.visible = false;
        this.substitutionsDisabledBtn.visible = false;
        this.x2Btn.visible = true;
        this.x3Btn.visible = true;
        this.divTwoBtn.visible = true;
        this.divThreeBtn.visible = true;
        this.plusOneBtn.visible = true;
        this.minusOneBtn.visible = true;
        //this.maxCountBtn.visible = true;

        super.drawFG(offsetX, offsetY, mouseX, mouseY);
        this.fontRenderer.drawString(GuiText.PatternTerminal.getLocal(), 8, this.ySize - 96 + 2 - this.getReservedSpace(), 4210752);
    }

    @Override
    protected String getBackground() {
        return BACKGROUND_EXPANDED_PROCESSING_MODE;
    }

    @Override
    protected void repositionSlot(final AppEngSlot s) {
        final int offsetPlayerSide = s.isPlayerSide() ? 5 : 3;

        s.yPos = s.getY() + this.ySize - 78 - offsetPlayerSide;
    }

    @Override
    public List<IGhostIngredientHandler.Target<?>> getPhantomTargets(Object ingredient) {
        if (!(ingredient instanceof ItemStack)) {
            return Collections.emptyList();
        }
        List<IGhostIngredientHandler.Target<?>> targets = new ArrayList<>();
        for (Slot slot : this.inventorySlots.inventorySlots) {
            if (slot instanceof SlotFake) {
                ItemStack itemStack = (ItemStack) ingredient;
                IGhostIngredientHandler.Target<Object> target = new IGhostIngredientHandler.Target<Object>() {
                    @Override
                    public Rectangle getArea() {
                        return new Rectangle(getGuiLeft() + slot.xPos, getGuiTop() + slot.yPos, 16, 16);
                    }

                    @Override
                    public void accept(Object ingredient) {
                        final PacketInventoryAction p;
                        try {
                            p = new PacketInventoryAction(InventoryAction.PLACE_JEI_GHOST_ITEM, (SlotFake) slot, AEItemStack.fromItemStack(itemStack));
                            NetworkHandler.instance().sendToServer(p);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                targets.add(target);
                mapTargetSlot.putIfAbsent(target, slot);
            }
        }
        return targets;
    }

    @Override
    public Map<IGhostIngredientHandler.Target<?>, Object> getFakeSlotTargetMap() {
        return mapTargetSlot;
    }
}

