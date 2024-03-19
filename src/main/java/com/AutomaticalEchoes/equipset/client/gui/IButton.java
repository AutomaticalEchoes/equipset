package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.api.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

@OnlyIn(Dist.CLIENT)
public class  IButton extends Button {
    private @Nullable Pair<ResourceLocation, Integer> Common;
    private @Nullable Pair<ResourceLocation, Integer> Hover;
    private boolean showText = true;
    public IButton(int p_259075_, int p_259271_, OnPress p_260152_) {
        super(p_259075_, p_259271_, 16, 16, Component.empty(), p_260152_ );
    }

    public IButton(int p_259075_, int p_259271_, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, 16, 16, component, p_260152_ );
    }

    public IButton(int p_259075_, int p_259271_, int width, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, width, width, component, p_260152_ );
    }

    public IButton(int p_259075_, int p_259271_, int width, int height, Component component, OnPress p_260152_) {
        super(p_259075_, p_259271_, width, height, component, p_260152_ );
    }

    public IButton UnDrawText() {
        this.showText = false;
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

    @Override
    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_){
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        boolean hoveredOrFocused = this.isHoveredOrFocused();
        if(Common == null){
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
            int i = this.getYImage(hoveredOrFocused);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            Utils.Render4c(p_93676_, WIDGETS_LOCATION,  x, y, width, height, 0, 46 + i * 20, 200, 66 + i * 20, 256, 256);
        }else {
            Pair<ResourceLocation, Integer> pair = Hover != null && hoveredOrFocused ? Hover : Common;
            Utils.Render4c(p_93676_, pair.getA(), x, y, width, height, pair.getB() >> 10, pair.getB() & 2047);
        }

        int j = getFGColor();

        if(showText){
            float scale = this.height <= 16 ? this.height <= 8 ? 0.25F: 0.5F : 1F;
            p_93676_.pushPose();
            p_93676_.scale(scale, scale,1.0F);
            drawCenteredString(p_93676_ , font, getMessage(), (int) (( x + this.width / 2) /scale), (int) (((y + this.height / 2) / scale) - 4), j | Mth.ceil(this.alpha * 255.0F) << 24);
            p_93676_.popPose();
        }
    }


}
