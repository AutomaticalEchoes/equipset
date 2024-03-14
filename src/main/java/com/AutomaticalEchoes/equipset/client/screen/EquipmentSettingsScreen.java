package com.AutomaticalEchoes.equipset.client.screen;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.client.gui.IEditBox;
import com.AutomaticalEchoes.equipset.client.gui.ManagerWeight;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
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

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {
        this.font.draw(p_97808_, this.title, this.titleLabelX, this.titleLabelY, 4210752);
    }

    @Override
    public void render(PoseStack p_98705_, int p_98706_, int p_98707_, float p_98708_) {
        this.renderBackground(p_98705_);
        super.render(p_98705_, p_98706_, p_98707_, p_98708_);
        this.renderTooltip(p_98705_,p_98706_,p_98707_);
    }

    @Override
    protected void renderBg(PoseStack p_98870_, float p_98871_, int p_98872_, int p_98873_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_98870_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        InventoryScreen.renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - p_98872_, (float)(j + 75 - 50) - p_98873_, this.minecraft.player);
    }

    public void reInit(){
        managerWeight.update(font, iPlayerInterface.getEquipmentSets());
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
