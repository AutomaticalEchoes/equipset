package org.automaticalechoes.equipset.common.network;


import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface EquipSetNetWork {
    void SendServerConfig(ServerPlayer serverPlayer);
    void SendFeedBack(ServerPlayer serverPlayer, Component component);
    void SendUpdatePreset(int targetNum, int cases);
    void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable);
    void SendUpdateSetName(int suitNum, String suitName);

}
