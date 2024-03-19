package com.AutomaticalEchoes.equipset.client.keyMapping;

import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Actions {
    public static void CallScreen(){
        Minecraft.getMinecraft().getTutorial().openInventory();
        Minecraft.getMinecraft().displayGuiScreen(new EquipmentSettingsScreen(Minecraft.getMinecraft().player));
    }

    public static void SendUsePreset(Integer nums) {
        Minecraft.getMinecraft().player.sendChatMessage("/eqs use_preset %d".replace("%d",String.valueOf(nums)));
    }
}
