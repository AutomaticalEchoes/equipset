package org.automaticalechoes.equipset.common.network;


import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.automaticalechoes.equipset.Constants;

public record SendServerConfig(int setsNum) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, SendServerConfig> CODEC = StreamCodec.composite(ByteBufCodecs.INT, SendServerConfig::setsNum, SendServerConfig::new);
    public void handleMessage() {
        Constants.Client.InitServerConfig(setsNum());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.SERVER_CONFIG_TYPE;
    }
}
