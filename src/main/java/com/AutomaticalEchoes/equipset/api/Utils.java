package com.AutomaticalEchoes.equipset.api;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Utils {
    public static final MutableComponent Total = Component.translatable("tip.equipset.total");
    public static final MutableComponent LackPart = Component.translatable("tip.equipset.lack_part").withStyle(ChatFormatting.RED);
    public static final MutableComponent CursePart = Component.translatable("tip.equipset.curse_part").withStyle(ChatFormatting.RED);
    public static final MutableComponent NoneSet = Component.translatable("tip.equipser.none_set").withStyle(ChatFormatting.RED);
    public static final MutableComponent LockSet = Component.translatable("tip.equipser.lock_set").withStyle(ChatFormatting.RED);

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
    public static void Render4c(PoseStack graphics, ResourceLocation resourceLocation, int x, int y, int w, int h, int rw, int rh){
        RenderSystem.setShaderTexture(0, resourceLocation);
        Gui.blit(graphics, x, y, 0, 0, w/2, h/2, rw, rh);
        Gui.blit(graphics, x + w/2, y, rw - w/2, 0, w/2, h/2, rw, rh);
        Gui.blit(graphics,  x, y + h/2, 0, rh - h/2, w/2, h/2, rw, rh);
        Gui.blit(graphics, x + w/2, y + h/2, rw - w/2, rh - h/2, w/2, h/2, rw, rh);
    }

    @OnlyIn(Dist.CLIENT)
    public static void Render4c(PoseStack graphics, ResourceLocation resourceLocation, int x, int y, int w, int h, int sx, int sy, int ex, int ey, int rw, int rh){
        RenderSystem.setShaderTexture(0, resourceLocation);
        Gui.blit(graphics, x, y, sx, sy, w/2, h/2, rw, rh);
        Gui.blit(graphics, x + w/2, y, ex - w/2, sy, w/2, h/2, rw, rh);
        Gui.blit(graphics,  x, y + h/2, sx, ey - h/2, w/2, h/2, rw, rh);
        Gui.blit(graphics, x + w/2, y + h/2, ex - w/2, ey - h/2, w/2, h/2, rw, rh);
    }
}
