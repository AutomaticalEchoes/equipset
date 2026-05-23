
package com.automaticalechoes.equipset.api

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.item.ItemStack

class IUtils {

    companion object{
        @JvmField
        val Total: Component = Component.translatable("tip.equipset.total")
        @JvmField
        val LackPart: Component = Component.translatable("tip.equipset.lack_part").withStyle(ChatFormatting.RED)
        @JvmField
        val CursePart: Component = Component.translatable("tip.equipset.curse_part").withStyle(ChatFormatting.RED)
        @JvmField
        val NoneSet: Component = Component.translatable("tip.equipser.none_set").withStyle(ChatFormatting.RED)
        @JvmField
        val LockSet: Component = Component.translatable("tip.equipser.lock_set").withStyle(ChatFormatting.RED)
        @JvmStatic
        fun CheckItemSame(A: ItemStack, B: ItemStack): Boolean {
            var equals = true
            for (i in 0..1) {
                equals = when (i) {
                    0 -> (A.getItem() == B.getItem())
                    1 -> (A.getDisplayName().getString() == B.getDisplayName().getString())
                    else -> true
                }
                if (!equals) break
            }
            return equals
        }
        @JvmStatic
        fun Render4c(graphics: GuiGraphicsExtractor, resourceLocation: Identifier, x: Int, y: Int, w: Int, h: Int, rw: Int, rh: Int) {
            graphics.blit(resourceLocation, x, y, 0, 0, (w / 2).toFloat(), (h / 2).toFloat(), rw.toFloat(), rh.toFloat())
            graphics.blit(resourceLocation, x + w / 2, y, rw - w / 2, 0, (w / 2).toFloat(), (h / 2).toFloat(), rw.toFloat(), rh.toFloat())
            graphics.blit(resourceLocation, x, y + h / 2, 0, rh - h / 2, (w / 2).toFloat(), (h / 2).toFloat(), rw.toFloat(), rh.toFloat())
            graphics.blit(resourceLocation, x + w / 2, y + h / 2, rw - w / 2, rh - h / 2, (w / 2).toFloat(), (h / 2).toFloat(), rw.toFloat(), rh.toFloat())
        }
    }

    //    public static void blitWithBorder(GuiGraphicsExtractor guiGraphics, Identifier texture, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder)
    //    {
    //        int fillerWidth = textureWidth - leftBorder - rightBorder;
    //        int fillerHeight = textureHeight - topBorder - bottomBorder;
    //        int canvasWidth = width - leftBorder - rightBorder;
    //        int canvasHeight = height - topBorder - bottomBorder;
    //        int xPasses = canvasWidth / fillerWidth;
    //        int remainderWidth = canvasWidth % fillerWidth;
    //        int yPasses = canvasHeight / fillerHeight;
    //        int remainderHeight = canvasHeight % fillerHeight;
    //
    //        // Draw Border
    //        // Top Left
    //        guiGraphics.blit(RenderPipelines.GUI, texture, x, y, u, v, leftBorder, topBorder);
    //        // Top Right
    //        guiGraphics.blit(texture, x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder);
    //        // Bottom Left
    //        guiGraphics.blit(texture, x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder);
    //        // Bottom Right
    //        guiGraphics.blit(texture, x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);
    //
    //        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++)
    //        {
    //            // Top Border
    //            guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder);
    //            // Bottom Border
    //            guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder);
    //
    //            // Throw in some filler for good measure
    //            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
    //                guiGraphics.blit(texture, x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight));
    //        }
    //
    //        // Side Borders
    //        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
    //        {
    //            // Left Border
    //            guiGraphics.blit(texture, x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight));
    //            // Right Border
    //            guiGraphics.blit(texture, x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight));
    //        }
    //    }



//    fun blitWithBorder(
//        guiGraphics: GuiGraphics,
//        texture: ResourceLocation?,
//        x: Int,
//        y: Int,
//        u: Int,
//        v: Int,
//        width: Int,
//        height: Int,
//        textureWidth: Int,
//        textureHeight: Int,
//        topBorder: Int,
//        bottomBorder: Int,
//        leftBorder: Int,
//        rightBorder: Int
//    ) {
//        val fillerWidth = textureWidth - leftBorder - rightBorder
//        val fillerHeight = textureHeight - topBorder - bottomBorder
//        val canvasWidth = width - leftBorder - rightBorder
//        val canvasHeight = height - topBorder - bottomBorder
//        val xPasses = canvasWidth / fillerWidth
//        val remainderWidth = canvasWidth % fillerWidth
//        val yPasses = canvasHeight / fillerHeight
//        val remainderHeight = canvasHeight % fillerHeight
//
//        // Draw Border
//        // Top Left
//        guiGraphics.blit(texture, x, y, u, v, leftBorder, topBorder)
//        // Top Right
//        guiGraphics.blit(
//            texture,
//            x + leftBorder + canvasWidth,
//            y,
//            u + leftBorder + fillerWidth,
//            v,
//            rightBorder,
//            topBorder
//        )
//        // Bottom Left
//        guiGraphics.blit(
//            texture,
//            x,
//            y + topBorder + canvasHeight,
//            u,
//            v + topBorder + fillerHeight,
//            leftBorder,
//            bottomBorder
//        )
//        // Bottom Right
//        guiGraphics.blit(
//            texture,
//            x + leftBorder + canvasWidth,
//            y + topBorder + canvasHeight,
//            u + leftBorder + fillerWidth,
//            v + topBorder + fillerHeight,
//            rightBorder,
//            bottomBorder
//        )
//
//        for (i in 0..<xPasses + (if (remainderWidth > 0) 1 else 0)) {
//            // Top Border
//            guiGraphics.blit(
//                texture,
//                x + leftBorder + (i * fillerWidth),
//                y,
//                u + leftBorder,
//                v,
//                (if (i == xPasses) remainderWidth else fillerWidth),
//                topBorder
//            )
//            // Bottom Border
//            guiGraphics.blit(
//                texture,
//                x + leftBorder + (i * fillerWidth),
//                y + topBorder + canvasHeight,
//                u + leftBorder,
//                v + topBorder + fillerHeight,
//                (if (i == xPasses) remainderWidth else fillerWidth),
//                bottomBorder
//            )
//
//            // Throw in some filler for good measure
//            for (j in 0..<yPasses + (if (remainderHeight > 0) 1 else 0)) guiGraphics.blit(
//                texture,
//                x + leftBorder + (i * fillerWidth),
//                y + topBorder + (j * fillerHeight),
//                u + leftBorder,
//                v + topBorder,
//                (if (i == xPasses) remainderWidth else fillerWidth),
//                (if (j == yPasses) remainderHeight else fillerHeight)
//            )
//        }
//
//        // Side Borders
//        for (j in 0..<yPasses + (if (remainderHeight > 0) 1 else 0)) {
//            // Left Border
//            guiGraphics.blit(
//                texture,
//                x,
//                y + topBorder + (j * fillerHeight),
//                u,
//                v + topBorder,
//                leftBorder,
//                (if (j == yPasses) remainderHeight else fillerHeight)
//            )
//            // Right Border
//            guiGraphics.blit(
//                texture,
//                x + leftBorder + canvasWidth,
//                y + topBorder + (j * fillerHeight),
//                u + leftBorder + fillerWidth,
//                v + topBorder,
//                rightBorder,
//                (if (j == yPasses) remainderHeight else fillerHeight)
//            )
//        }
//    }
//
//
//    fun Render4c(
//        graphics: GuiGraphics,
//        resourceLocation: ResourceLocation?,
//        x: Int,
//        y: Int,
//        w: Int,
//        h: Int,
//        rw: Int,
//        rh: Int
//    ) {
//        graphics.blit(resourceLocation, x, y, 0, 0, w / 2, h / 2, rw, rh)
//        graphics.blit(resourceLocation, x + w / 2, y, rw - w / 2, 0, w / 2, h / 2, rw, rh)
//        graphics.blit(resourceLocation, x, y + h / 2, 0, rh - h / 2, w / 2, h / 2, rw, rh)
//        graphics.blit(resourceLocation, x + w / 2, y + h / 2, rw - w / 2, rh - h / 2, w / 2, h / 2, rw, rh)
//    }
}