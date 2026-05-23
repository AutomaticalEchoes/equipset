package com.automaticalechoes.equipset.client.gui;



import com.automaticalechoes.equipset.Constants;
import com.automaticalechoes.equipset.api.IUtils;
import com.automaticalechoes.equipset.impl.common.EquipSet;
import com.automaticalechoes.equipset.impl.common.SetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ManagerWeight extends AbstractWidget {
    public static final Identifier BLACK_GROUND = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/manager.png");
    public static final Component TITLE = Component.translatable("label.equipset.manager");
    private final List<SettingWeight> sets = new ArrayList<>();
    private final Font font;
    private int scroll = 0;
    private int maxScroll = 0;

    public ManagerWeight(int p_240025_, int p_240026_, int p_240027_, int p_240028_, Component p_240029_) {
        super(p_240025_, p_240026_, p_240027_, p_240028_, p_240029_);
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, float v) {
        guiGraphicsExtractor.text(this.font, TITLE, getX() + 6, getY() + 6, 4210752);
        guiGraphicsExtractor.enableScissor(getX() + 4, getY() + 25, getX() + width - 4, getY() + height - 5);
        guiGraphicsExtractor.pose().pushMatrix();
//        guiGraphicsExtractor.pose().translate(0, - scroll, new Matrix3x2f());//0
        for (SettingWeight set : sets) {
            set.extractWidgetRenderState(guiGraphicsExtractor, i, i1 + scroll, v);
        }
        guiGraphicsExtractor.pose().popMatrix();
        guiGraphicsExtractor.disableScissor();
        IUtils.Render4c(guiGraphicsExtractor, BLACK_GROUND, this.getX(), this.getY(), width, height, 140, 200);
        sets.forEach(set -> set.renderTooltip(guiGraphicsExtractor, i, i1 + scroll, scroll));
    }

    public void update(Font font, SetManager presetManager){
        sets.clear();
        int i = 0;
        for (Map.Entry<Integer, EquipSet> entry : presetManager.entrySet()) {
            SettingWeight settingWeight = new SettingWeight(font, getX() + 5, getY() + 24 + i * 41 , width -  10, 40, entry.getKey());
            sets.add(settingWeight);
            i++;
        }
        maxScroll = i * 41 > height - 30? i * 41 + 30 - height : 0;
    }




    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return this.mouseClicked(event, doubleClick) && sets.stream().anyMatch(b -> b.mouseClicked(event, doubleClick));
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pScrollX, double pScrollY) {
        if(this.isHoveredOrFocused()){
            scroll -= (int) (Math.signum(pScrollY) * 20);
            scroll = Math.max(scroll, 0);
            scroll = Math.min(maxScroll , scroll);
            return true;
        }
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }
}
