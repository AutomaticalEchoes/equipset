package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.PresetManager;
import com.AutomaticalEchoes.equipset.api.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ManagerWeight extends GuiButton {
    public static final ResourceLocation BLACK_GROUND = new ResourceLocation(EquipSet.MODID, "textures/gui/manager.png");
    public static final String TITLE = I18n.format("label.equipset.manager");
    private final List<SettingWeight> sets = new ArrayList<>();
    private final FontRenderer font;
    private int scroll = 0;
    private int maxScroll = 0;


    public ManagerWeight(int id, int p_240025_, int p_240026_, int p_240027_, int p_240028_, String p_240029_) {
        super(id, p_240025_, p_240026_, p_240027_, p_240028_, p_240029_);
        this.font = Minecraft.getMinecraft().fontRenderer;
    }

    public void update(PresetManager presetManager){
        sets.clear();
        int i = 0;
        for (Map.Entry<Integer, PresetEquipSet> entry : presetManager.entrySet()) {
            SettingWeight settingWeight = new SettingWeight(font, x + 5, y + 24 + i * 41 , width -  10, 40, entry.getKey());
            sets.add(settingWeight);
            i++;
        }
        maxScroll = i * 41 > height - 30? i * 41 + 30 - height : 0;
    }

    @Override
    public void drawButton(Minecraft p_282213_, int p_282468_, int p_282209_, float p_283300_) {
//        p_282213_.getre
//        int i = window.getHeight();
//        double d0 = window.getGuiScale();
        ScaledResolution res = new ScaledResolution(p_282213_);
        double scaleW = p_282213_.displayWidth / res.getScaledWidth_double();
        double scaleH = p_282213_.displayHeight / res.getScaledHeight_double();
        this.hovered = p_282468_ >= this.x && p_282209_ >= this.y && p_282468_ < this.x + this.width && p_282209_ < this.y + this.height;
//        double d1 = (double)(x + 4) * d0;
//        double d2 = (double)i - (double)(y + height - 5) * d0;
//        double d3 = (double)(width - 8) * d0;
//        double d4 = (double)(height - 30) * d0;
        double d1 = (double)(x + 4) * scaleW;
        double d2 = (double)p_282213_.displayHeight - (double)(y + height - 5) * scaleH;
        double d3 = (double)(width - 8) * scaleW;
        double d4 = (double)(height - 30) * scaleH;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
//        GlStateManager.d.enableScissor((int)d1, (int)d2, Math.max(0, (int)d3), Math.max(0, (int)d4));
//        GlStateManager.cullFace(GlStateManager.CullFace.FRONT);

        GlStateManager.pushMatrix();
//        GlStateManager.viewport(x + 4, y + height - 5, width -8, height -30);
        GlStateManager.translate(0, - scroll, 0);
        for (SettingWeight set : sets) {
            set.drawButton(p_282213_, p_282468_, p_282209_ + scroll, p_283300_);
        }
        GlStateManager.popMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
//        GlStateManager.disableCull();
//        RenderSystem.disableScissor();
        Utils.Render4c(p_282213_, BLACK_GROUND, this.x, this.y, width, height, 140, 200);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0,0, 250);
        this.font.drawString(TITLE, x + 6, y + 6, 4210752);
        GlStateManager.popMatrix();
    }

    public void renderToolTip(Minecraft p_282213_, int p_282468_, int p_282209_){
        sets.forEach(set -> set.renderTooltip(p_282213_, p_282468_, p_282209_ + scroll, scroll));
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return super.mousePressed(mc, mouseX, mouseY) && sets.stream().anyMatch(b -> b.mousePressed(mc, mouseX, mouseY + scroll));
    }

    public void mouseScrolled(int sc) {
        if(this.isMouseOver()){
            scroll -= Math.signum(sc) * 20;
            scroll = MathHelper.clamp(scroll, 0, maxScroll);
        }
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode){
        return sets.stream().anyMatch(set -> set.textboxKeyTyped(typedChar,keyCode));
    }

}
