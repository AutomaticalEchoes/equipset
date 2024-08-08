package automaticalechoes.equipset.equipset.client.keyMapping;

import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class Actions {
    public static void CallScreen(){
        Minecraft.getInstance().getTutorial().onOpenInventory();
        Minecraft.getInstance().setScreen(new EquipmentSettingsScreen(Minecraft.getInstance().player));
    }

    public static void SendUsePreset(Integer nums) {
        Minecraft.getInstance().player.connection.sendCommand("eqs use_preset %d".formatted(nums));
    }
}
