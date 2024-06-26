package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.PresetManager;
import com.AutomaticalEchoes.equipset.api.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class ManagerWeight extends AbstractWidget {
    public static final ResourceLocation BLACK_GROUND = new ResourceLocation(EquipSet.MODID, "textures/gui/manager.png");
    public static final Component TITLE = Component.translatable("label.equipset.manager");
    private final List<SettingWeight> sets = new ArrayList<>();
    private final Font font;
    private int scroll = 0;
    private int maxScroll = 0;


    public ManagerWeight(int p_240025_, int p_240026_, int p_240027_, int p_240028_, Component p_240029_) {
        super(p_240025_, p_240026_, p_240027_, p_240028_, p_240029_);
        this.font = Minecraft.getInstance().font;
    }

    public void update(Font font, PresetManager presetManager){
        sets.clear();
        int i = 0;
        for (Map.Entry<Integer, PresetEquipSet> entry : presetManager.entrySet()) {
            SettingWeight settingWeight = new SettingWeight(font, getX() + 5, getY() + 24 + i * 41 , width -  10, 40, entry.getKey());
            sets.add(settingWeight);
            i++;
        }
        maxScroll = i * 41 > height - 30? i * 41 + 30 - height : 0;
    }

    @Override
    public void renderWidget(GuiGraphics p_282213_, int p_282468_, int p_282209_, float p_283300_) {
        p_282213_.drawString(this.font, TITLE, getX() + 6, getY() + 6, 4210752);
        p_282213_.enableScissor(getX() + 4, getY() + 25, getX() + width - 4, getY() + height - 5);
        p_282213_.pose().pushPose();
        p_282213_.pose().translate(0, - scroll, 0);
        for (SettingWeight set : sets) {
            set.render(p_282213_, p_282468_, p_282209_ + scroll, p_283300_);
        }
        p_282213_.pose().popPose();
        p_282213_.disableScissor();
        Utils.Render4c(p_282213_, BLACK_GROUND, this.getX(), this.getY(), width, height, 140, 200);
        sets.forEach(set -> set.renderTooltip(p_282213_, p_282468_, p_282209_ + scroll, scroll));
    }

    @Override
    public boolean mouseClicked(double p_240170_, double p_240171_, int p_240172_) {
        return this.clicked(p_240170_, p_240171_) && sets.stream().anyMatch(b -> b.mouseClicked(p_240170_, p_240171_  + scroll, p_240172_));
    }

    @Override
    public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_) {
        if(this.clicked(p_94734_,p_94735_)){
            scroll -= Math.signum(p_94736_) * 20;
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
