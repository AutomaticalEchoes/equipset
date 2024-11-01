package org.automaticalechoes.equipset.NetWork.network;



import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.automaticalechoes.equipset.Constants;
import org.automaticalechoes.equipset.common.network.*;

import java.util.Optional;

public class PacketHandler {

    public static void Init(){
        PayloadTypeRegistry.playS2C().register(Packets.FEED_BACK_TYPE, FeedBack.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(Packets.FEED_BACK_TYPE, (payload, context) -> payload.handleMessage());

        PayloadTypeRegistry.playC2S().register(Packets.UPDATE_PRESET_NAME_TYPE, UpdateSetName.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(Packets.UPDATE_PRESET_NAME_TYPE, (payload, context) -> payload.handleMessage(context.player()));

        PayloadTypeRegistry.playC2S().register(Packets.UPDATE_PRESET_TYPE, UpdatePreset.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(Packets.UPDATE_PRESET_TYPE, (payload, context) -> payload.handleMessage(context.player()));

        PayloadTypeRegistry.playC2S().register(Packets.UPDATE_PRESET_PART_STATUS_TYPE, UpdatePresetPartStatus.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(Packets.UPDATE_PRESET_PART_STATUS_TYPE, (payload, context) -> payload.handleMessage(context.player()));

        PayloadTypeRegistry.playS2C().register(Packets.SERVER_CONFIG_TYPE, SendServerConfig.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(Packets.SERVER_CONFIG_TYPE, (payload, context) -> payload.handleMessage());

        Constants.NETWORK = Optional.of(new FabricNetworkImp());
    }

}
