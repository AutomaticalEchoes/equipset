package org.automaticalechoes.equipset.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.EquipSetNetWork;
import automaticalechoes.equipset.equipset.config.Config;
import automaticalechoes.equipset.equipset.config.ModGameRule;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkImp implements EquipSetNetWork {
//    @Override
//    public void AskConfig() {
//        CommonModEvents.NetWork.sendToServer(new ForgeAskConfig());
//    }


    @Override
    public void SendServerConfig(ServerPlayer serverPlayer) {
        int nums = serverPlayer.server.getWorldData().getGameRules().getRule(ModGameRule.EQUIP$NUMS).get();
        ServerPlayNetworking.send(serverPlayer, new FabricSendServerConfig(nums));
    }

    @Override
    public void SendFeedBack(ServerPlayer serverPlayer, Component component) {
        ServerPlayNetworking.send(serverPlayer, new FabricFeedBack(component));
    }

    @Override
    public void SendUpdatePreset(int targetNum, int cases) {
        ClientPlayNetworking.send(new FabricUpdatePreset(targetNum, cases));
    }

    @Override
    public void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
        ClientPlayNetworking.send(new FabricUpdatePresetPartStatus(targetNum, partName, enable));
    }

    @Override
    public void SendUpdateSetName(int suitNum, String suitName) {
        ClientPlayNetworking.send(new FabricUpdateSetName(suitNum, suitName));
    }
}
