package com.AutomaticalEchoes.equipset.client.screen;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import com.AutomaticalEchoes.equipset.client.gui.IEditBox;
import com.AutomaticalEchoes.equipset.client.gui.ManagerWeight;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class EquipmentSettingsScreen extends EffectRenderingInventoryScreen<InventoryMenu> {
    protected final IPlayerInterface iPlayerInterface;
    protected ManagerWeight managerWeight;
    public EquipmentSettingsScreen(Player p_98839_) {
        super(p_98839_.inventoryMenu, p_98839_.getInventory(), Component.translatable("container.crafting"));
        this.iPlayerInterface = (IPlayerInterface) p_98839_;
        this.titleLabelX = 97;
    }

    @Override
    protected void init() {
        super.init();
        this.managerWeight = new ManagerWeight(leftPos - 123, topPos - 20, 120, 193, Component.empty());
        this.addRenderableWidget(managerWeight);
        reInit();
    }

    public void reInit(){
        managerWeight.update(font, iPlayerInterface.getEquipmentSets());
    }

    @Override
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
        p_281635_.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    public void render(GuiGraphics p_283027_, int p_281444_, int p_282953_, float p_281666_) {
        renderBackground(p_283027_);
        super.render(p_283027_, p_281444_, p_282953_, p_281666_);
        renderTooltip(p_283027_,p_281444_,p_282953_);
    }

    @Override
    protected void renderBg(GuiGraphics p_281500_, float p_281299_, int p_283481_, int p_281831_) {
        int i = this.leftPos;
        int j = this.topPos;
        p_281500_.blit(INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventoryFollowsMouse(p_281500_, i + 51, j + 75, 30, (float)(i + 51) - p_283481_, (float)(j + 75 - 50) - p_281831_, this.minecraft.player);
    }

    @Override
    public void setFocused(@Nullable GuiEventListener p_94677_) {
        if(p_94677_ instanceof ManagerWeight) return;
        super.setFocused(p_94677_);
    }

    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
        InputConstants.Key mouseKey = InputConstants.getKey(p_97765_, p_97766_);
        if(this.getFocused() instanceof IEditBox iEditBox){
            return p_97765_ == 257?  iEditBox.lostFocus(this) : iEditBox.keyPressed(p_97765_, p_97766_, p_97767_);
        }

        if(super.keyPressed(p_97765_, p_97766_, p_97767_)) return true;

        if(KeyMappings.CALL_SET_INVENTORY_KEY.getKey().equals(mouseKey)){
            this.onClose();
            return true;
        }
        return false;
    }

}
