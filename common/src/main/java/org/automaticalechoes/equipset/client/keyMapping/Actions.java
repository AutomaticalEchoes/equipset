package org.automaticalechoes.equipset.client.keyMapping;



import net.minecraft.client.Minecraft;
import org.automaticalechoes.equipset.client.screen.EquipmentSettingsScreen;


public class Actions {
    public static void CallScreen(){
        Minecraft.getInstance().getTutorial().onOpenInventory();
        Minecraft.getInstance().setScreen(new EquipmentSettingsScreen(Minecraft.getInstance().player));
    }

    public static void SendUsePreset(Integer nums) {
        Minecraft.getInstance().player.connection.sendCommand("eqs use_preset %d".formatted(nums));
    }
}
