package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.api.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

@OnlyIn(Dist.CLIENT)
public class IButton extends Button {
    private @Nullable Pair<ResourceLocation, Integer> Common;
    private @Nullable Pair<ResourceLocation, Integer> Hover;
    private boolean omit = false;
    private int textColor = -1;
    private int textHoverColor = -1;
    private boolean showText = true;

    public IButton(int p_259075_, int p_259271_, OnPress p_260152_) {
        super(p_259075_, p_259271_, 16, 16, Component.empty(), p_260152_, DEFAULT_NARRATION);
    }

    public IButton(int p_259075_, int p_259271_, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, 16, 16, component, p_260152_, DEFAULT_NARRATION);
    }

    public IButton(int p_259075_, int p_259271_, int width, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, width, width, component, p_260152_, DEFAULT_NARRATION);
    }

    public IButton(int p_259075_, int p_259271_, int width, int height, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, width, height, component, p_260152_, DEFAULT_NARRATION);
    }

    public IButton TextHoverColor(int textHoverColor) {
        this.textHoverColor = textHoverColor;
        return this;
    }

    public IButton TextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public IButton UnDrawText() {
        this.showText = false;
        return this;
    }

    public IButton TextOmit() {
        omit = true;
        return this;
    }

    public IButton BackGroundTexture(ResourceLocation resourceLocation, int textureWidth, int textureHeight) {
        Common = new Pair<>(resourceLocation,textureWidth << 10 | textureHeight);
        return this;
    }

    public IButton BackGroundHoverTexture(ResourceLocation resourceLocation, int textureWidth, int textureHeight) {
        Hover = new Pair<>(resourceLocation,textureWidth << 10 | textureHeight);
        return this;
    }

    public void renderWidget(GuiGraphics p_282701_, int p_282638_, int p_283565_, float p_282549_) {
        renderBg(p_282701_, p_282638_, p_283565_, p_282549_);
        if(showText) drawString(p_282701_, p_282638_, p_283565_, p_282549_, textColor != textHoverColor && this.isHoveredOrFocused()?  textHoverColor : textColor);
    }


    public void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean hoveredOrFocused = this.isHoveredOrFocused();
        if(Common == null){
            int k = !this.active ? 0 : ( hoveredOrFocused ? 2 : 1);
            guiGraphics.blitWithBorder(WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2);
        }else{
            Pair<ResourceLocation, Integer> pair = Hover != null && hoveredOrFocused ? Hover : Common;
            Utils.Render4c(guiGraphics, pair.getA(), getX(), getY(), width, height, pair.getB() >> 10, pair.getB() & 2047);
        }
    }

    public void drawString(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, int color) {
        Minecraft mc = Minecraft.getInstance();
        if(omit){
            final FormattedText buttonText = mc.font.ellipsize(this.getMessage(), this.width - 6); // Remove 6 pixels so that the text is always contained within the button's borders
            guiGraphics.drawCenteredString(mc.font, Language.getInstance().getVisualOrder(buttonText), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color);
        }else {
            float scale = this.height <= 16 ? this.height <= 8 ? 0.25F: 0.5F : 1F;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale, scale,1.0F);
            guiGraphics.drawCenteredString(mc.font, getMessage(), (int) ((this.getX() + this.width / 2) /scale), (int) (((this.getY() + this.height / 2) / scale) - 4), color);
            guiGraphics.pose().popPose();
        }
    }

}
