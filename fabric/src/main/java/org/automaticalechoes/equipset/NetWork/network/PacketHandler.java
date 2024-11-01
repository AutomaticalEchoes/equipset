package org.automaticalechoes.equipset.NetWork.network;



import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.automaticalechoes.equipset.common.network.FeedBack;
import org.automaticalechoes.equipset.common.network.Packets;

import java.util.Optional;

public class PacketHandler {

    public static void Init(){
        PayloadTypeRegistry.playS2C().register(Packets.FEED_BACK_TYPE, new StreamCodec<RegistryFriendlyByteBuf, FeedBack>() {
            @Override
            public FeedBack decode(RegistryFriendlyByteBuf object) {
                return object.reCo;
            }

            @Override
            public void encode(RegistryFriendlyByteBuf object, FeedBack object2) {

            }
        })
        ClientPlayNetworking.registerGlobalReceiver(Packets.FEED_BACK_TYPE, new ClientPlayNetworking.PlayPayloadHandler<FeedBack>() {
            @Override
            public void receive(FeedBack payload, ClientPlayNetworking.Context context) {
                context.
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(FABRIC_SERVER_CONFIG, (packet, player, responseSender) -> packet.handleMessage());
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET, (packet, player, responseSender) -> packet.handleMessage(player));
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET_PART_STATUS, (packet, player, responseSender) -> packet.handleMessage(player));
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_SET_NAME, (packet, player, responseSender) -> packet.handleMessage(player));
        EquipSet.NETWORK = Optional.of(new FabricNetworkImp());
    }

}
