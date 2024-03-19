package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.Utils;
import com.AutomaticalEchoes.equipset.client.keyMapping.Actions;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.UpdatePreset;
import com.AutomaticalEchoes.equipset.common.network.UpdateSetName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class SettingWeight extends GuiButton {
    protected static final String COMPONENT_CLEAR = "button.equipset.clear";
    protected static final String COMPONENT_SAVE = I18n.format("button.equipset.save");
    protected static final String COMPONENT_LOCK = I18n.format("button.equipset.lock");
    protected static final String COMPONENT_UNLOCK = I18n.format("button.equipset.unlock");
    protected static final String COMPONENT_DELETE = "button.equipset.delete";
    static final ResourceLocation BLACK_GROUND = new ResourceLocation(EquipSet.MODID,"textures/gui/set.png");
    static final ResourceLocation LOCK = new ResourceLocation(EquipSet.MODID,"textures/gui/lock.png");
    static final ResourceLocation UNLOCK = new ResourceLocation(EquipSet.MODID,"textures/gui/unlock.png");
//    static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
//    static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
//    static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
//    static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
//    static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
//    static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{ EMPTY_ARMOR_SLOT_SHIELD, EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    public static final String[]  TEXTURE_EMPTY_SLOTS = new String[] { "minecraft:items/empty_armor_slot_shield", "minecraft:items/empty_armor_slot_boots", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_helmet"};
    private final Set<GuiButton> children = new HashSet<>();
    private final GuiButton UnLock ;
    private final IEditBox NameEdit;
    private final int lineY_1;
    private final int lineY_2;
    private final int lineX_1;
    private final int lineX_2;
    private PresetEquipSet preSet;

    public SettingWeight(FontRenderer font, int x, int y, int w, int h, int id) {
        super(id,x, y, w, h, "");
        lineY_1 = y + 4;
        lineY_2 = y + height - 4;
        lineX_1 = x + 4;
        lineX_2 = x + width - 4;
        IButton lock = new IButton(0, lineX_2 - 20,  lineY_1 + 1, 20, 10, COMPONENT_LOCK, this::onPress);
        IButton save = new IButton(0, lineX_1,  lineY_2 - 18,  10,  18,COMPONENT_SAVE, this::onPress);
        IButton change = new IButton(0, lineX_1,  lineY_1 + 1, 10, 10, String.valueOf(id), p_93751_ -> Actions.SendUsePreset(id));
        NameEdit = new IEditBox(5, font, this.x + 17,  lineY_1 + 2, 64, 8, (name) -> {
            CommonModEvents.NetWork.sendToServer(new UpdateSetName(id,name));
        } );
        NameEdit.setText(Set(id).getName());

        this.children.add(change);
        this.children.add(lock);
        this.children.add(save);
        int i = 0;
        for (String partName : PresetEquipSet.DEFAULT_PARTS.keySet()) {
            this.children.add(new ItemButton(lineX_1 + 12 + 18 * i, lineY_2 - 17, id, partName).emptyIcon(TEXTURE_EMPTY_SLOTS[4-i]));
            i++;
        }

        UnLock = new IButton(0, this.x + width / 2 - 10, this.y + height / 2 - 10, 16, COMPONENT_UNLOCK, this::onPress)
                .UnDrawText()
                .BackGroundTexture(LOCK, 16, 16)
                .BackGroundHoverTexture(UNLOCK, 16, 16);
    }

    @Override
    public void drawButton(Minecraft p_282421_, int p_93658_, int p_93659_, float p_93660_) {
        if(!this.visible) return;
        this.hovered = p_93658_ >= this.x && p_93659_ >= this.y && p_93658_ < this.x + this.width && p_93659_ < this.y + this.height;
        try {
            this.preSet = Set(id);
            Utils.Render4c(p_282421_, BLACK_GROUND, x, y, width, height, 140, 40);
            if (!NameEdit.isFocused()) NameEdit.setText(preSet.getName());
            renderWidget(p_282421_, p_93658_, p_93659_, p_93660_);
            if(!enabled){
                drawGradientRect(x, y, x + this.width, y + this.height, -1072689136, -804253680);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0,0,250);
                UnLock.drawButton(p_282421_, p_93658_, p_93659_, p_93660_);
                GlStateManager.popMatrix();
            }
        }catch (Exception e){ }
    }
    
    public void renderWidget(Minecraft p_282421_, int p_268034_, int p_268009_, float p_268085_) {
        enabled = !preSet.isLock();
        NameEdit.drawTextBox();
        children.forEach(child -> {
            if(child instanceof ItemButton) ((ItemButton)child).info(preSet);
            child.drawButton(p_282421_, p_268034_, p_268009_, p_268085_);
            child.enabled = this.enabled;
        });
    }


    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return !super.mousePressed(mc,mouseX,mouseY)? UnLock.mousePressed(mc, mouseX, mouseY) : NameEdit.mouseClicked(mouseX,mouseY,1) || children.stream().anyMatch(child -> child.mousePressed(mc, mouseX, mouseY));
    }
    
    public boolean renderTooltip(Minecraft p_281670_, int p_282682_, int p_281714_, int scroll){
        return this.isMouseOver() && children.stream().anyMatch(widget -> widget instanceof ItemButton && ((ItemButton) widget).renderTooltip(p_281670_, p_282682_, p_281714_, scroll));
    }

    private void onPress(GuiButton button){
        // clear, save, lock, unLock
        int cases = -1;
        if(button.displayString.equals(COMPONENT_CLEAR)){
            cases = 0;
        }else if(button.displayString.equals(COMPONENT_SAVE)){
            cases = 1;
        }else if(button.displayString.equals(COMPONENT_LOCK)){
            cases = 2;
        }else if(button.displayString.equals(COMPONENT_UNLOCK)){
            cases = 3;
        }else if(button.displayString.equals(COMPONENT_DELETE)){
            cases = 5;
        }

        if(cases != -1) CommonModEvents.NetWork.sendToServer(new UpdatePreset(id, cases));
    }

    private static PresetEquipSet Set(int id){
        return ((IPlayerInterface)Minecraft.getMinecraft().player).getEquipmentSets().get(id);
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode){
        return this.NameEdit.textboxKeyTyped(typedChar,keyCode);
    }
}
