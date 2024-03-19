package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.PresetEquipPart;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.UpdatePresetPartStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ItemButton extends GuiButton {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(EquipSet.MODID, "textures/gui/bundle.png");
    private final FontRenderer font = Minecraft.getMinecraft().fontRenderer;
    private final int num;
    private PresetEquipPart part;
    private final String PartName;
    private @Nullable
    String EMPTY;
    private final GuiScreen screen;
    protected ItemButton(int p_259075_, int p_259271_, int num, String partName) {
        super(0, p_259075_, p_259271_, 16, 16, "");
        this.num = num;
        this.PartName = partName;
        this.screen = Minecraft.getMinecraft().currentScreen;
    }

    public ItemButton emptyIcon(String resourceLocation){
        this.EMPTY = resourceLocation;
        return this;
    }

    @Override
    public void drawButton(Minecraft mc, int p_282682_, int p_281714_, float p_282542_) {
        if(part == null) return;
        boolean enable = part.isEnable();
        boolean isEmpty = part.getSettingNeed().isEmpty();
        this.hovered = this.enabled && p_282682_ >= this.x && p_281714_ >= this.y && p_282682_ < this.x + this.width && p_281714_ < this.y + this.height;
        mc.getTextureManager().bindTexture(TEXTURE_LOCATION);
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawModalRectWithCustomSizedTexture(x - 1, y - 1, 0, enable? 0 : 40,18, 20, 128, 128);

        if(enable && !isEmpty){
            Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(part.getSettingNeed(), x, y);
        }
        if((isEmpty || !enable) && EMPTY != null){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(EMPTY);
            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            drawTexturedModalRect( x, y, textureatlassprite, 16, 16);
        }
        if(isMouseOver()){
            drawGradientRect(x, y, x + 16, y + 16, 0, -2130706433);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        GlStateManager.popMatrix();
    }

    public boolean renderTooltip(Minecraft p_281670_, int p_282682_, int p_281714_, int scroll){
        if(!isMouseOver() || part.getSettingNeed().isEmpty()) return false;
        List<String> tooltipFromItem = screen.getItemToolTip(part.getSettingNeed());
        GlStateManager.pushMatrix();
        GlStateManager.translate(0,0, 400F);
        screen.drawHoveringText(tooltipFromItem, p_282682_, p_281714_ - scroll);
        GlStateManager.popMatrix();
        return true;
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean flag = super.mousePressed(mc, mouseX, mouseY);
        if(flag && part != null)
            CommonModEvents.NetWork.sendToServer(new UpdatePresetPartStatus(num, PartName, !part.isEnable()));
        return flag;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);

    }

    public void info(PresetEquipSet set){
        this.part = set.get(PartName);
    }

}
