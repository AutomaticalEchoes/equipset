package org.automaticalechoes.equipset.client.keyMapping;



import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.automaticalechoes.equipset.client.screen.EquipmentSettingsScreen;

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
