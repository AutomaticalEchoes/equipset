package com.automaticalechoes.equipset.client.gui;


import com.automaticalechoes.equipset.api.IUtils;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import oshi.util.tuples.Pair;


public class IButton extends Button {
    private @Nullable Pair<Identifier, Integer> Common;
    private @Nullable Pair<Identifier, Integer> Hover;
    private static final WidgetSprites SPRITES = new WidgetSprites(Identifier.withDefaultNamespace("widget/button"), Identifier.withDefaultNamespace("widget/button_disabled"), Identifier.withDefaultNamespace("widget/button_highlighted"));
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

    public IButton BackGroundTexture(Identifier Identifier, int textureWidth, int textureHeight) {
        Common = new Pair<>(Identifier,textureWidth << 10 | textureHeight);
        return this;
    }

    public IButton BackGroundHoverTexture(Identifier Identifier, int textureWidth, int textureHeight) {
        Hover = new Pair<>(Identifier,textureWidth << 10 | textureHeight);
        return this;
    }



    public void renderBg(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        boolean hoveredOrFocused = this.isHoveredOrFocused();
        if(Common == null){
            guiGraphics.blitSprite(RenderPipelines.GUI,SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
//            Utils.blitWithBorder(guiGraphics, WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2);
        }else{
            Pair<Identifier, Integer> pair = Hover != null && hoveredOrFocused ? Hover : Common;
            IUtils.Render4c(guiGraphics, pair.getA(), getX(), getY(), width, height, pair.getB() >> 10, pair.getB() & 2047);
        }
    }

    public void drawString(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick, int color) {
        Minecraft mc = Minecraft.getInstance();
        if(omit){
            final FormattedText buttonText = mc.font.substrByWidth(this.getMessage(), this.width - 6); // Remove 6 pixels so that the text is always contained within the button's borders
            guiGraphics.centeredText(mc.font, Language.getInstance().getVisualOrder(buttonText), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color);
        }else {
            float scale = this.height <= 16 ? this.height <= 8 ? 0.25F: 0.5F : 1F;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(scale, scale,new Matrix3x2f());
            guiGraphics.centeredText(mc.font, getMessage(), (int) ((this.getX() + this.width / 2) /scale), (int) (((this.getY() + this.height / 2) / scale) - 4), color);
            guiGraphics.pose().popMatrix();
        }
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, float v) {
        renderBg(guiGraphicsExtractor, i, i1, v);
        if(showText) drawString(guiGraphicsExtractor, i, i1, v, textColor != textHoverColor && this.isHoveredOrFocused()?  textHoverColor : textColor);
    }
}
