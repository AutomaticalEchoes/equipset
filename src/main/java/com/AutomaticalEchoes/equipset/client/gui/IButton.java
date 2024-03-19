package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.api.OnPress;
import com.AutomaticalEchoes.equipset.api.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class IButton extends GuiButton {
    private @Nullable
    MutablePair<ResourceLocation, Integer> Common;
    private @Nullable
    MutablePair<ResourceLocation, Integer> Hover;
    private boolean showText = true;
    private final OnPress<IButton> onPress;

    public IButton(int id, int p_259075_, int p_259271_, OnPress<IButton> p_260152_) {
        this(id, p_259075_, p_259271_, 16, "", p_260152_);
    }

    public IButton(int id, int p_259075_, int p_259271_, String component, OnPress<IButton> p_260152_) {
        this(id, p_259075_, p_259271_, 16, 16, component, p_260152_ );

    }

    public IButton(int id, int p_259075_, int p_259271_, int width, String component, OnPress<IButton> p_260152_) {
        this(id, p_259075_, p_259271_, width, width, component, p_260152_);
    }

    public IButton(int id, int p_259075_, int p_259271_, int width, int height, String component, OnPress<IButton> p_260152_) {
        super(id, p_259075_, p_259271_, width, height, component);
        this.onPress = p_260152_;
    }

    public IButton UnDrawText() {
        this.showText = false;
        return this;
    }

    public IButton BackGroundTexture(ResourceLocation resourceLocation, int textureWidth, int textureHeight) {
        Common = new MutablePair<>(resourceLocation, textureWidth << 10 | textureHeight);
        return this;
    }

    public IButton BackGroundHoverTexture(ResourceLocation resourceLocation, int textureWidth, int textureHeight) {
        Hover = new MutablePair<>(resourceLocation,textureWidth << 10 | textureHeight);
        return this;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean flag = super.mousePressed(mc, mouseX, mouseY);
        if(flag) onPress.action(this);
        return flag;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        FontRenderer font = mc.fontRenderer;
        this.hovered = this.enabled && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        if(Common == null){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            Utils.Render4c(mc, BUTTON_TEXTURES,  x, y, width, height, 0, 46 + i * 20, 200, 66 + i * 20, 256, 256);
            GlStateManager.disableBlend();
        }else {
            Pair<ResourceLocation, Integer> pair = Hover != null && hovered ? Hover : Common;
            Utils.Render4c(mc,pair.getKey(), x, y, width, height, pair.getValue() >> 10, pair.getValue() & 2047);
        }

        int j = 14737632;

        if (packedFGColour != 0)
        {
            j = packedFGColour;
        }
        else
        if (!this.enabled)
        {
            j = 10526880;
        }
        else if (this.hovered)
        {
            j = 16777120;
        }

        if(showText){
            float scale = this.height <= 16 ? this.height <= 8 ? 0.25F: 0.5F : 1F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale, scale,1.0F);
            drawCenteredString(font, displayString, (int) (( x + this.width / 2) /scale), (int) (((y + this.height / 2) / scale) - 4), j);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }


}
