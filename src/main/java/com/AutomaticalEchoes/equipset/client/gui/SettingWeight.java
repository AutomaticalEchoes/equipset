package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.Utils;
import com.AutomaticalEchoes.equipset.client.keyMapping.Actions;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.UpdatePreset;
import com.AutomaticalEchoes.equipset.common.network.UpdateSetName;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class SettingWeight extends AbstractWidget {
    protected static final Component COMPONENT_CLEAR = Component.translatable("button.equipset.clear");
    protected static final Component COMPONENT_SAVE = Component.translatable("button.equipset.save");
    protected static final Component COMPONENT_LOCK = Component.translatable("button.equipset.lock");
    protected static final Component COMPONENT_UNLOCK = Component.translatable("unlock");
    protected static final Component COMPONENT_DELETE = Component.translatable("button.equipset.delete");
    static final ResourceLocation BLACK_GROUND = new ResourceLocation(EquipSet.MODID,"textures/gui/set.png");
    static final ResourceLocation LOCK = new ResourceLocation(EquipSet.MODID,"textures/gui/lock.png");
    static final ResourceLocation UNLOCK = new ResourceLocation(EquipSet.MODID,"textures/gui/unlock.png");
    static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{ EMPTY_ARMOR_SLOT_SHIELD, EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private final int id;
    private final Set<AbstractWidget> children = new HashSet<>();
    private final Button UnLock ;
    private final IEditBox NameEdit;
    private final int lineY_1;
    private final int lineY_2;
    private final int lineX_1;
    private final int lineX_2;
    private PresetEquipSet preSet;

    public SettingWeight(Font font, int x, int y, int w, int h, int id) {
        super(x, y, w, h, Component.empty());
        this.id = id;
        lineY_1 = y + 4;
        lineY_2 = y + height - 4;
        lineX_1 = x + 4;
        lineX_2 = x + width - 4;
        IButton lock = new IButton(lineX_2 - 20,  lineY_1 + 1, 20, 10, COMPONENT_LOCK, this::onPress);
        IButton save = new IButton(lineX_1,  lineY_2 - 18,  10,  18,COMPONENT_SAVE, this::onPress);
        IButton change = new IButton(lineX_1,  lineY_1 + 1, 10, 10, Component.literal(String.valueOf(id)), p_93751_ -> Actions.SendUsePreset(id));
        NameEdit = new IEditBox(font, this.x + 17,  lineY_1 + 2, 64, 8, Component.empty(), name -> {
            CommonModEvents.NetWork.sendToServer(new UpdateSetName(id, name));
        } );
        NameEdit.setValue(Set(id).getName());

        this.children.addAll(Set.of(change, NameEdit, lock, save));
        int i = 0;
        for (String partName : PresetEquipSet.DEFAULT_PARTS.keySet()) {
            this.children.add(new ItemButton(lineX_1 + 12 + 18 * i, lineY_2 - 17, id, partName, Button::onPress).emptyIcon(TEXTURE_EMPTY_SLOTS[4-i]));
            i++;
        }

        UnLock = new IButton(this.x + width / 2 - 10, this.y + height / 2 - 10, 16, COMPONENT_UNLOCK, this::onPress)
                .UnDrawText()
                .BackGroundTexture(LOCK, 16, 16)
                .BackGroundHoverTexture(UNLOCK, 16, 16);
    }

    @Override
    public void render(PoseStack p_282421_, int p_93658_, int p_93659_, float p_93660_) {
        if(!this.visible) return;
        try {
            this.preSet = Set(id);
            Utils.Render4c(p_282421_, BLACK_GROUND, x, y, width, height, 140, 40);
            if (!NameEdit.isFocused()) NameEdit.setValue(preSet.getName());
            renderWidget(p_282421_, p_93658_, p_93659_, p_93660_);
            if(!active){
                fillGradient(p_282421_, x, y, x + this.width, y + this.height, -1072689136, -804253680,0);
                p_282421_.pushPose();
                p_282421_.translate(0,0, 250);
                UnLock.render(p_282421_, p_93658_, p_93659_, p_93660_);
                p_282421_.popPose();
            }
        }catch (Exception e){ }
    }
    
    public void renderWidget(PoseStack p_282421_, int p_268034_, int p_268009_, float p_268085_) {
        active = !preSet.isLock();
        children.forEach(child -> {
            if(child instanceof ItemButton itemButton) itemButton.info(preSet);
            child.renderButton(p_282421_, p_268034_, p_268009_, p_268085_);
            if(child != NameEdit)
                ((IWeight)child).setIFocus(active && child.isMouseOver(p_268034_, p_268009_));
        });
    }

    @Override
    public boolean mouseClicked(double p_93641_, double p_93642_, int p_93643_) {
        return !clicked(p_93641_, p_93642_) ? UnLock.mouseClicked(p_93641_, p_93642_, p_93643_) : children.stream().anyMatch(child -> child.mouseClicked(p_93641_, p_93642_, p_93643_));
    }

    public boolean renderTooltip(PoseStack p_281670_, int p_282682_, int p_281714_, int scroll){
        return this.clicked(p_282682_, p_281714_) && children.stream().anyMatch(widget -> widget instanceof ItemButton itemButton && itemButton.renderTooltip(p_281670_, p_282682_, p_281714_, scroll));
    }

    @Override
    public void updateNarration(NarrationElementOutput p_259858_) {
    }

    private void onPress(Button button){
        // clear, save, lock, unLock
        int cases = -1;
        if(button.getMessage().equals(COMPONENT_CLEAR)){
            cases = 0;
        }else if(button.getMessage().equals(COMPONENT_SAVE)){
            cases = 1;
        }else if(button.getMessage().equals(COMPONENT_LOCK)){
            cases = 2;
        }else if(button.getMessage().equals(COMPONENT_UNLOCK)){
            cases = 3;
        }else if(button.getMessage().equals(COMPONENT_DELETE)){
            cases = 5;
        }

        if(cases != -1) CommonModEvents.NetWork.sendToServer(new UpdatePreset(id, cases));
    }

    private static PresetEquipSet Set(int id){
        return ((IPlayerInterface)Minecraft.getInstance().player).getEquipmentSets().get(id);
    }
}
