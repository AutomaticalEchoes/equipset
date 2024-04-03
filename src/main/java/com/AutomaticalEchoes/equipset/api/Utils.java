package com.AutomaticalEchoes.equipset.api;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Utils {
    public static final Component Total = Component.translatable("tip.equipset.total");
    public static final Component LackPart = Component.translatable("tip.equipset.lack_part").withStyle(ChatFormatting.RED);
    public static final Component CursePart = Component.translatable("tip.equipset.curse_part").withStyle(ChatFormatting.RED);
    public static final Component NoneSet = Component.translatable("tip.equipser.none_set").withStyle(ChatFormatting.RED);
    public static final Component LockSet = Component.translatable("tip.equipser.lock_set").withStyle(ChatFormatting.RED);

    public static boolean CheckItemSame(ItemStack A, ItemStack B){
        boolean equals = true;
        for (int i = 0; i < 2; i++) {
            equals = switch (i){
                case 0 -> A.getItem().equals(B.getItem());
                case 1 -> A.getDisplayName().getString().equals(B.getDisplayName().getString());
                case 2 -> A.getEnchantmentTags().equals(B.getEnchantmentTags());
//                case 3 -> A.areCapsCompatible(B);
                default -> true;
            };
            if(!equals) break;
        }
        return equals;
    }

    @OnlyIn(Dist.CLIENT)
    public static void Render4c(GuiGraphics graphics, ResourceLocation resourceLocation, int x, int y, int w, int h, int rw, int rh){
        graphics.blit(resourceLocation, x, y, 0, 0, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x + w/2, y, rw - w/2, 0, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x, y + h/2, 0, rh - h/2, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x + w/2, y + h/2, rw - w/2, rh - h/2, w/2, h/2, rw, rh);
    }
}
