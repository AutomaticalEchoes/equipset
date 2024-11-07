package org.automaticalechoes.equipset.api;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


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
                default -> true;
            };
            if(!equals) break;
        }
        return equals;
    }

    public static void blitWithBorder(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder)
    {
        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        // Draw Border
        // Top Left
        guiGraphics.blit(texture, x, y, u, v, leftBorder, topBorder);
        // Top Right
        guiGraphics.blit(texture, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder);
        // Bottom Left
        guiGraphics.blit(texture, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder);
        // Bottom Right
        guiGraphics.blit(texture, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++)
        {
            // Top Border
            guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder);
            // Bottom Border
            guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder);

            // Throw in some filler for good measure
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
                guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight));
        }

        // Side Borders
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
        {
            // Left Border
            guiGraphics.blit(texture, x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight));
            // Right Border
            guiGraphics.blit(texture, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight));
        }
    }


    public static void Render4c(GuiGraphics graphics, ResourceLocation resourceLocation, int x, int y, int w, int h, int rw, int rh){
        graphics.blit(resourceLocation, x, y, 0, 0, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x + w/2, y, rw - w/2, 0, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x, y + h/2, 0, rh - h/2, w/2, h/2, rw, rh);
        graphics.blit(resourceLocation, x + w/2, y + h/2, rw - w/2, rh - h/2, w/2, h/2, rw, rh);
    }
}
