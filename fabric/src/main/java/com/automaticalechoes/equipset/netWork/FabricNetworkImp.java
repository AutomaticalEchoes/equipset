package com.automaticalechoes.equipset.netWork;


import com.automaticalechoes.equipset.common.network.*;
import com.automaticalechoes.equipset.config.ModGameRules;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;


public class FabricNetworkImp implements EquipSetNetWork {
//    @Override
//    public void AskConfig() {
//        CommonModEvents.NetWork.sendToServer(new ForgeAskConfig());
//    }


    @Override
    public void SendServerConfig(ServerPlayer serverPlayer, MinecraftServer server) {
        int nums = server.getGameRules().get(ModGameRules.getEQUIP$SET_NUMS());
        ServerPlayNetworking.send(serverPlayer, new SendServerConfig(nums));
    }

    @Override
    public void SendFeedBack(ServerPlayer serverPlayer, Component component) {
        ServerPlayNetworking.send(serverPlayer, new FeedBack(component));
    }

    @Override
    public void SendUpdatePreset(int targetNum, int cases) {
        ClientPlayNetworking.send(new UpdatePreset(targetNum, cases));
    }

    @Override
    public void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
        ClientPlayNetworking.send(new UpdatePresetPartStatus(targetNum, partName, enable));
    }

    @Override
    public void SendUpdateSetName(int suitNum, String suitName) {
        ClientPlayNetworking.send(new UpdateSetName(suitNum, suitName));
    }
}
