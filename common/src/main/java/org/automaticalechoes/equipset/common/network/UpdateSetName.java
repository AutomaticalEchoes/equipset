package org.automaticalechoes.equipset.common.network;



import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.automaticalechoes.equipset.api.IPlayerInterface;

public record UpdateSetName(int suitNum, String suitName) implements CustomPacketPayload {
    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateSetName> CODEC = StreamCodec.composite(ByteBufCodecs.INT, UpdateSetName::suitNum, ByteBufCodecs.STRING_UTF8, UpdateSetName::suitName, UpdateSetName::new);
    public void handleMessage(ServerPlayer sender) {
        ((IPlayerInterface)sender).equipSet$updateSetName(suitNum(), suitName());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.UPDATE_PRESET_NAME_TYPE;
    }
}
