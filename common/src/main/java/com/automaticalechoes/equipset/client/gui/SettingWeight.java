package com.automaticalechoes.equipset.client.gui;



import com.automaticalechoes.equipset.Constants;
import com.automaticalechoes.equipset.api.IUtils;
import com.automaticalechoes.equipset.client.keyMapping.Actions;
import com.automaticalechoes.equipset.impl.common.EquipSet;
import com.automaticalechoes.equipset.common.IPlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;

import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;


import java.util.HashSet;
import java.util.Set;

public class SettingWeight extends AbstractWidget {
    protected static final Component COMPONENT_CLEAR = Component.translatable("button.equipset.clear");
    protected static final Component COMPONENT_SAVE = Component.translatable("button.equipset.save");
    protected static final Component COMPONENT_LOCK = Component.translatable("button.equipset.lock");
    protected static final Component COMPONENT_UNLOCK = Component.translatable("unlock");
    protected static final Component COMPONENT_DELETE = Component.translatable("button.equipset.delete");
    static final Identifier BLACK_GROUND = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"textures/gui/set.png");
    static final Identifier LOCK = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"textures/gui/lock.png");
    static final Identifier UNLOCK = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"textures/gui/unlock.png");
    public static final Identifier EMPTY_ARMOR_SLOT_HELMET = Identifier.withDefaultNamespace("container/slot/helmet");
    public static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE = Identifier.withDefaultNamespace("container/slot/chestplate");
    public static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS = Identifier.withDefaultNamespace("container/slot/leggings");
    public static final Identifier EMPTY_ARMOR_SLOT_BOOTS = Identifier.withDefaultNamespace("container/slot/boots");
    public static final Identifier EMPTY_ARMOR_SLOT_SHIELD = Identifier.withDefaultNamespace("container/slot/shield");
    static final Identifier[] TEXTURE_EMPTY_SLOTS = new Identifier[]{ EMPTY_ARMOR_SLOT_SHIELD, EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private final int id;
    private final Set<AbstractWidget> children = new HashSet<>();
    private final IButton UnLock ;
    private final IEditBox NameEdit;
    private final int lineY_1;
    private final int lineY_2;
    private final int lineX_1;
    private final int lineX_2;
    private EquipSet preSet;

    public SettingWeight(Font font, int x, int y, int w, int h, int id) {
        super(x, y, w, h, Component.empty());
        this.id = id;
        lineY_1 = y + 4;
        lineY_2 = y + height - 4;
        lineX_1 = x + 4;
        lineX_2 = x + width - 4;
        IButton lock = new IButton(lineX_2 - 20,  lineY_1 + 1, 20, 10, COMPONENT_LOCK, this::onPress);
        IButton save = new IButton(lineX_1,  lineY_2 - 18,  10,  18,COMPONENT_SAVE, this::onPress);
        IButton change = new IButton(lineX_1,  lineY_1 + 1, 10, 10, Component.literal(String.valueOf(id + 1)), p_93751_ -> Actions.SendUsePreset(id));
        NameEdit = new IEditBox(font, this.getX() + 17,  lineY_1 + 2, 64, 8, Component.empty(), name -> {
            Constants.NETWORK.ifPresent(net -> net.SendUpdateSetName(id, name));
        } );
        NameEdit.setValue(Set(id).getName());

        this.children.addAll(Set.of(change, NameEdit, lock, save));
        int i = 0;
        for (String partName : EquipSet.DEFAULT_PARTS.keySet()) {
            this.children.add(new ItemButton(lineX_1 + 12 + 18 * i, lineY_2 - 17, id, partName).emptyIcon(TEXTURE_EMPTY_SLOTS[4-i]));
            i++;
        }

        UnLock = new IButton(this.getX() + width / 2 - 10, this.getY() + height / 2 - 10, 16, COMPONENT_UNLOCK, this::onPress)
                .UnDrawText()
                .BackGroundTexture(LOCK, 16, 16)
                .BackGroundHoverTexture(UNLOCK, 16, 16);
    }



    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return !super.mouseClicked(event, doubleClick)? UnLock.mouseClicked(event, doubleClick) : children.stream().anyMatch(child -> child.mouseClicked(event, doubleClick));
    }

    public boolean renderTooltip(GuiGraphicsExtractor p_281670_, int p_282682_, int p_281714_, int scroll){
        return isHoveredOrFocused() && children.stream().anyMatch(widget -> widget instanceof ItemButton itemButton && itemButton.renderTooltip(p_281670_, p_282682_, p_281714_, scroll));
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, float v) {
        this.preSet = Set(id);
        IUtils.Render4c(guiGraphicsExtractor, BLACK_GROUND, getX(), getY(), width, height, 140, 40);
        if (!NameEdit.isFocused()) NameEdit.setValue(preSet.getName());
        active = !preSet.isLock();
        for (AbstractWidget child : children) {
            if (child instanceof ItemButton itemButton) itemButton.info(preSet);
            child.extractRenderState(guiGraphicsExtractor, i, i1, v);
            if (child != NameEdit)
                child.setFocused(active && child.isMouseOver(i, i1));
        }
        if(!active){
            guiGraphicsExtractor.fillGradient( getX(), getY(), this.width,  this.height, -1072689136, -804253680);
            guiGraphicsExtractor.pose().pushMatrix();
            guiGraphicsExtractor.pose().translate(0,0, new Matrix3x2f());//250
            UnLock.extractRenderState(guiGraphicsExtractor, i, i1, v);
            guiGraphicsExtractor.pose().popMatrix();
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
    }

    private void onPress(Button button){
        // clear, save, lock, unLock
        int cases;
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
        } else {
            cases = -1;
        }

        if(cases != -1)  Constants.NETWORK.ifPresent(net -> net.SendUpdatePreset(id, cases));
    }

    private static EquipSet Set(int id){
        return ((IPlayerInterface)Minecraft.getInstance().player).equipSet$getEquipmentSets().get(id);
    }
}
