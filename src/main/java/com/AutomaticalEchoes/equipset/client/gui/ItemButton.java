package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.api.PresetEquipPart;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.UpdatePresetPartStatus;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;
@OnlyIn(Dist.CLIENT)
public class ItemButton extends Button {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private final Font font = Minecraft.getInstance().font;
    private final int num;
    private PresetEquipPart part;
    private final String PartName;
    private @Nullable ResourceLocation EMPTY;
    protected ItemButton(int p_259075_, int p_259271_, int num, String partName, OnPress p_260152_) {
        super(p_259075_, p_259271_, 16, 16, Component.empty(), p_260152_, DEFAULT_NARRATION);
        this.num = num;
        this.PartName = partName;
    }

    public ItemButton emptyIcon(ResourceLocation resourceLocation){
        this.EMPTY = resourceLocation;
        return this;
    }

    @Override
    public void renderWidget(GuiGraphics p_281670_, int p_282682_, int p_281714_, float p_282542_) {
        if(part == null) return;
        boolean enable = part.isEnable();
        boolean isEmpty = part.getSettingNeed().isEmpty();
        p_281670_.blit(TEXTURE_LOCATION, getX() - 1, getY() - 1, 0, enable? 0 : 40,18, 20, 128, 128);


        if(enable && !isEmpty){
            p_281670_.renderItem(part.getSettingNeed(), getX(), getY());
        }
        if((isEmpty || !enable) && EMPTY != null){
            Pair<ResourceLocation, ResourceLocation> pair = getNoItemIcon();
            TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
            p_281670_.blit(getX(), getY(), 0, 16, 16, textureatlassprite);
        }
        if(isHoveredOrFocused()){
            AbstractContainerScreen.renderSlotHighlight(p_281670_, getX(), getY(), 0, -2130706433);
        }
    }

    public boolean renderTooltip(GuiGraphics p_281670_, int p_282682_, int p_281714_, int scroll){
        if(!isHoveredOrFocused() || part.getSettingNeed().isEmpty()) return false;
        List<Component> tooltipFromItem = Screen.getTooltipFromItem(Minecraft.getInstance(), part.getSettingNeed());
        p_281670_.renderTooltip(font, tooltipFromItem, part.getSettingNeed().getTooltipImage(), p_282682_, p_281714_ - scroll);
        return true;
    }

    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY);
    }

    @Override
    public void onPress() {
        if(part != null)
            CommonModEvents.NetWork.sendToServer(new UpdatePresetPartStatus(num, PartName, !part.isEnable()));
    }

    public void info(PresetEquipSet set){
        this.part = set.get(PartName);
    }

}
