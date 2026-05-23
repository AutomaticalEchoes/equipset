package com.automaticalechoes.equipset.client.gui;



import com.automaticalechoes.equipset.Constants;
import com.automaticalechoes.equipset.impl.common.EquipPart;
import com.automaticalechoes.equipset.impl.common.EquipSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemButton extends Button {
    public static final Identifier SLOT = Identifier.withDefaultNamespace("textures/gui/sprites/container/bundle/slot.png");
    public static final Identifier BLOCKED_SLOT = Identifier.withDefaultNamespace("textures/gui/sprites/container/bundle/blocked_slot.png");
    private final Font font = Minecraft.getInstance().font;
    private final int num;
    private EquipPart part;
    private final String PartName;
    private @Nullable Identifier EMPTY;
    protected ItemButton(int p_259075_, int p_259271_, int num, String partName) {
        super(p_259075_, p_259271_, 16, 16, Component.empty(), _ -> {}, DEFAULT_NARRATION);
        this.num = num;
        this.PartName = partName;
    }

    public ItemButton emptyIcon(Identifier Identifier){
        this.EMPTY = Identifier;
        return this;
    }

//    private void extractSelectedItemTooltip(Font font, GuiGraphicsExtractor graphics, int x, int y, int w) {
//        ItemStackTemplate selectedItem = this.contents.getSelectedItem();
//        if (selectedItem != null) {
//            ItemStack itemStack = selectedItem.create();
//            Component selectedItemName = itemStack.getStyledHoverName();
//            int textWidth = font.width(selectedItemName.getVisualOrderText());
//            int centerTooltip = x + w / 2 - 12;
//            ClientTooltipComponent selectedItemNameTooltip = ClientTooltipComponent.create(selectedItemName.getVisualOrderText());
//            graphics.tooltip(
//                    font,
//                    List.of(selectedItemNameTooltip),
//                    centerTooltip - textWidth / 2,
//                    y - 15,
//                    DefaultTooltipPositioner.INSTANCE,
//                    itemStack.get(DataComponents.TOOLTIP_STYLE)
//            );
//        }
//    }

    public boolean renderTooltip(GuiGraphicsExtractor guiGraphicsExtractor, int x, int y, int scroll){
        if(!isHoveredOrFocused() || part.getSettingNeed().isEmpty()) return false;
        ItemStack settingNeed = part.getSettingNeed();
        Component selectedItemName = settingNeed.getStyledHoverName();
        ClientTooltipComponent selectedItemNameTooltip = ClientTooltipComponent.create(selectedItemName.getVisualOrderText());
        guiGraphicsExtractor.tooltip(
                font,
                List.of(selectedItemNameTooltip),
                x,
                y - scroll,
                DefaultTooltipPositioner.INSTANCE,
                settingNeed.get(DataComponents.TOOLTIP_STYLE)
        );
//        List<Component> tooltipFromItem = Screen.getTooltipFromItem(Minecraft.getInstance(), part.getSettingNeed());
//        guiGraphicsExtractor.tooltip(font, tooltipFromItem, x, y - scroll);
        return true;
    }

//    public Pair<Identifier, Identifier> getNoItemIcon() {
//        return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY);
//    }

    @Override
    public void onPress(InputWithModifiers input) {
        if(part != null)
            Constants.NETWORK.ifPresent(net -> net.SendUpdatePresetPartStatus(num, PartName, !part.isEnable()));
    }

    public void info(EquipSet set){
        this.part = set.get(PartName);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, float v) {
        if(part == null) return;
        boolean enable = part.isEnable();
        boolean isEmpty = part.getSettingNeed().isEmpty();
        Identifier TEXTURE_LOCATION = enable? SLOT : BLOCKED_SLOT;
        guiGraphicsExtractor.blit(TEXTURE_LOCATION, getX() - 1, getY() - 1, 0,0,18, 20, 18, 20);
        if(enable && !isEmpty){
            guiGraphicsExtractor.item(part.getSettingNeed(), getX(), getY());
        }
//        if((isEmpty || !enable) && EMPTY != null){
//            Pair<Identifier, Identifier> pair = getNoItemIcon();
//            TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
//            guiGraphicsExtractor.blit(getX(), getY(), 0, 16, 16, textureatlassprite);
//        }
//        if(isHoveredOrFocused()){
//            (guiGraphicsExtractor, getX(), getY(), 0);
//        }
    }

//    private void extractSlotHighlightBack(GuiGraphicsExtractor graphics) {
//        if (this.hoveredSlot != null && this.hoveredSlot.isHighlightable()) {
//            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_BACK_SPRITE, this.hoveredSlot.x - 4, this.hoveredSlot.y - 4, 24, 24);
//        }
//
//    }
//
//    private void extractSlotHighlightFront(GuiGraphicsExtractor graphics) {
//        if (this.hoveredSlot != null && this.hoveredSlot.isHighlightable()) {
//            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, this.hoveredSlot.x - 4, this.hoveredSlot.y - 4, 24, 24);
//        }
//
//    }
}
