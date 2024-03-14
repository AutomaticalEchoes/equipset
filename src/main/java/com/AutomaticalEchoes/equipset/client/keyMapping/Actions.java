package com.AutomaticalEchoes.equipset.client.keyMapping;

import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Actions {
    public static void CallScreen(){
        Minecraft.getInstance().getTutorial().onOpenInventory();
        Minecraft.getInstance().setScreen(new EquipmentSettingsScreen(Minecraft.getInstance().player));
    }

    public static void SendUsePreset(Integer nums) {
        Minecraft.getInstance().player.connection.sendCommand("eqs use_preset %d".formatted(nums));
    }
}
