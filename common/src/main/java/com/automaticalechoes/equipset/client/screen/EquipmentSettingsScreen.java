package com.automaticalechoes.equipset.client.screen;


import com.automaticalechoes.equipset.client.gui.IEditBox;
import com.automaticalechoes.equipset.client.gui.ManagerWeight;
import com.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;
import com.automaticalechoes.equipset.common.IPlayerInterface;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.CraftingRecipeBookComponent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;

import org.jetbrains.annotations.Nullable;


public class EquipmentSettingsScreen extends AbstractRecipeBookScreen<InventoryMenu> {
    protected final IPlayerInterface iPlayerInterface;
    protected ManagerWeight managerWeight;
    private boolean buttonClicked;
    public EquipmentSettingsScreen(Player player) {
        super(player.inventoryMenu,new CraftingRecipeBookComponent(player.inventoryMenu), player.getInventory(), Component.translatable("container.crafting"));
        this.iPlayerInterface = (IPlayerInterface) player;
        this.titleLabelX = 97;
    }

    @Override
    protected void init() {
        super.init();
        this.managerWeight = new ManagerWeight(leftPos - 123, topPos - 20, 120, 193, Component.empty());
        this.addRenderableWidget(managerWeight);
        reInit();
    }

    @Override
    protected ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 104, this.height / 2 - 22);
    }

    public void reInit(){
        managerWeight.update(font, iPlayerInterface.equipSet$getEquipmentSets());
    }


    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }
//
//    @Override
//    public void render(GuiGraphicsExtractor pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
//        renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
//        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
//        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
//    }


    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = this.leftPos;
        int yo = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, INVENTORY_LOCATION, xo, yo, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, xo + 26, yo + 8, xo + 75, yo + 78, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        if(p_94677_ instanceof ManagerWeight) return;
        super.setFocused(p_94677_);
    }

    @Override
    public boolean keyPressed(final KeyEvent event) {
        InputConstants.Key mouseKey = InputConstants.getKey(event);
        if(this.getFocused() instanceof IEditBox iEditBox){
            return event.key() == 257?  iEditBox.lostFocus(this) : iEditBox.keyPressed(event);
        }

        if(super.keyPressed(event)) return true;

        if(ModKeyMappings.CALL_SET_INVENTORY_KEY.equals(mouseKey)){
            this.onClose();
            return true;
        }
        return false;
    }

    protected void onRecipeBookButtonClick() {
        this.buttonClicked = true;
    }

    public boolean mouseReleased(MouseButtonEvent event) {
        if (this.buttonClicked) {
            this.buttonClicked = false;
            return true;
        } else {
            return super.mouseReleased(event);
        }
    }

}
