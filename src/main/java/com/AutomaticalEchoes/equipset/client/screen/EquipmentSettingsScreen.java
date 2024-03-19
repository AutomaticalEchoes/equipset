package com.AutomaticalEchoes.equipset.client.screen;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.client.gui.ManagerWeight;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class EquipmentSettingsScreen extends InventoryEffectRenderer {
    protected ManagerWeight managerWeight;
    private float oldMouseX;
    private float oldMouseY;
    public EquipmentSettingsScreen(EntityPlayer p_98839_) {
        super(p_98839_.inventoryContainer);
        this.allowUserInput = true;
        this.mc = Minecraft.getMinecraft();
        this.itemRender = mc.getRenderItem();
        this.fontRenderer = mc.fontRenderer;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.managerWeight = new ManagerWeight(0,guiLeft - 123, guiTop - 20, 120, 193, "");
        this.buttonList.add(managerWeight);
        reInit();
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(I18n.format("container.crafting"), 97, 8, 4210752);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.managerWeight.renderToolTip(mc, mouseX, mouseY);
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.mc.player);
    }

    public void reInit(){
        IPlayerInterface player = (IPlayerInterface) Minecraft.getMinecraft().player;
        this.managerWeight.update(player.getEquipmentSets());
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int eventDWheel = Mouse.getEventDWheel();
        if(eventDWheel !=0) managerWeight.mouseScrolled(eventDWheel);
    }

    //    @Override
//    public void setFocused(@Nullable GuiEventListener p_94677_) {
//        if(p_94677_ instanceof ManagerWeight) return;
//        super.setFocused(p_94677_);
//    }


    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if(managerWeight.textboxKeyTyped(typedChar,keyCode)) return;
        if(KeyMappings.CALL_SET_INVENTORY_KEY.isKeyDown()){
            this.onGuiClosed();
        }
        super.keyTyped(typedChar, keyCode);
    }

}
