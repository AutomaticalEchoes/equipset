package automaticalechoes.equipset.equipset.common.network;

import automaticalechoes.equipset.equipset.EquipSet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface EquipSetNetWork {
    void SendFeedBack(ServerPlayer serverPlayer, Component component);
    void SendUpdatePreset(int targetNum, int cases);
    void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable);
    void SendUpdateSetName(int suitNum, String suitName);

}
