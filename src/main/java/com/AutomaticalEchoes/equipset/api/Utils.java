package com.AutomaticalEchoes.equipset.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Utils {
    public static final Style STYLE_RED = new Style().setColor(TextFormatting.RED);
    public static final TextComponentTranslation Total = new TextComponentTranslation("tip.equipset.total");
    public static final TextComponentTranslation LackPart = (TextComponentTranslation) new TextComponentTranslation("tip.equipset.lack_part").setStyle(STYLE_RED);
    public static final TextComponentTranslation CursePart = (TextComponentTranslation) new TextComponentTranslation("tip.equipset.curse_part").setStyle(STYLE_RED);
    public static final TextComponentTranslation NoneSet = (TextComponentTranslation) new TextComponentTranslation("tip.equipset.none_set").setStyle(STYLE_RED);
    public static final TextComponentTranslation LockSet = (TextComponentTranslation) new TextComponentTranslation("tip.equipser.lock_set").setStyle(STYLE_RED);

    public static boolean CheckItemSame(ItemStack A, ItemStack B){
        boolean equals = true;
        for (int i = 0; i < 2; i++) {
            switch (i){
                case 0 :
                    equals = A.getItem().equals(B.getItem());
                    break;
                case 1 :
                    equals = A.getDisplayName().equals(B.getDisplayName());
                    break;
                case 2 :
                    equals = A.getEnchantmentTagList().equals(B.getEnchantmentTagList());
                    break;
                default:
                    break;
            };
            if(!equals) break;
        }
        return equals;
    }

    @SideOnly(Side.CLIENT)
    public static ITextComponent BuildComponent(int code, int num){
        if((code & 1) ==1){
            IPlayerInterface player = (IPlayerInterface) Minecraft.getMinecraft().player;
            TextComponentTranslation copy = Total.createCopy();
            copy.appendText(player.getEquipmentSets().get(num).getName());
            if((code >> 1 & 1) ==1) copy.appendSibling(LackPart);
            if((code >> 2 & 1) ==1) copy.appendSibling(CursePart);
            return copy;
        }else if((code >> 1 & 1) ==1){
            return LockSet;
        }
        return NoneSet;
    }

    public static class ICode{
        private int code = 0;

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void Render4c(Minecraft mc, ResourceLocation resourceLocation, int x, int y, int w, int h, int rw, int rh){
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture(x + w/2, y, rw - w/2, 0, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture( x, y + h/2, 0, rh - h/2, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture(x + w/2, y + h/2, rw - w/2, rh - h/2, w/2, h/2, rw, rh);
        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    public static void Render4c(Minecraft mc, ResourceLocation resourceLocation, int x, int y, int w, int h, int sx, int sy, int ex, int ey, int rw, int rh){
        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, sx, sy, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture(x + w/2, y, ex - w/2, sy, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture( x, y + h/2, sx, ey - h/2, w/2, h/2, rw, rh);
        Gui.drawModalRectWithCustomSizedTexture(x + w/2, y + h/2, ex - w/2, ey - h/2, w/2, h/2, rw, rh);
        GlStateManager.popMatrix();
    }
}
