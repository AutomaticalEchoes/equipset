package org.automaticalechoes.equipset.NetWork.network;



import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import org.automaticalechoes.equipset.common.network.*;
import org.automaticalechoes.equipset.config.ModGameRule;

public class NeoForgeNetworkImp implements EquipSetNetWork {
//    @Override
//    public void AskConfig() {
//        CommonModEvents.NetWork.sendToServer(new ForgeAskConfig());
//    }


    @Override
    public void SendServerConfig(ServerPlayer serverPlayer) {
        int nums = serverPlayer.server.getWorldData().getGameRules().getRule(ModGameRule.EQUIP$NUMS).get();
        PacketDistributor.sendToPlayer(serverPlayer, new SendServerConfig(nums));
    }

    @Override
    public void SendFeedBack(ServerPlayer serverPlayer, Component component) {
        PacketDistributor.sendToPlayer(serverPlayer, new FeedBack(component));
    }

    @Override
    public void SendUpdatePreset(int targetNum, int cases) {
        PacketDistributor.sendToServer(new UpdatePreset(targetNum, cases));
    }

    @Override
    public void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
        PacketDistributor.sendToServer(new UpdatePresetPartStatus(targetNum, partName, enable));
    }

    @Override
    public void SendUpdateSetName(int suitNum, String suitName) {
        PacketDistributor.sendToServer(new UpdateSetName(suitNum, suitName));
    }
}
