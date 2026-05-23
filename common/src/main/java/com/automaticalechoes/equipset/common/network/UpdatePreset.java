package com.automaticalechoes.equipset.common.network;



import com.automaticalechoes.equipset.common.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record UpdatePreset(int targetNum, int cases) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, UpdatePreset> CODEC = StreamCodec.composite(ByteBufCodecs.INT,UpdatePreset::targetNum,ByteBufCodecs.INT,UpdatePreset::cases,UpdatePreset::new);
    // clear, save, lock, unLock, neo
    public void handleMessage(ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.equipSet$updateSet(targetNum(),cases());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.UPDATE_PRESET_TYPE;
    }
}
