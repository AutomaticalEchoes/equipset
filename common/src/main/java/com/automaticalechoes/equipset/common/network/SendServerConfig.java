package com.automaticalechoes.equipset.common.network;


import com.automaticalechoes.equipset.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;


public record SendServerConfig(int setsNum) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, SendServerConfig> CODEC = StreamCodec.composite(ByteBufCodecs.INT, SendServerConfig::setsNum, SendServerConfig::new);
    public void handleMessage() {
        Constants.Client.InitServerConfig(setsNum());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.SERVER_CONFIG_TYPE;
    }
}
