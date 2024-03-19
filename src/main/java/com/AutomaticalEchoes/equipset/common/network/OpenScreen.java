package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class OpenScreen implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return entityPlayer.inventoryContainer;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return new EquipmentSettingsScreen(entityPlayer);
    }
}
