package org.automaticalechoes.equipset.common.network;



import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.automaticalechoes.equipset.api.IPlayerInterface;

public record UpdateSetName(int suitNum, String suitName) implements CustomPacketPayload {
    public void handleMessage(ServerPlayer sender) {
        ((IPlayerInterface)sender).equipSet$updateSetName(suitNum(), suitName());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.UPDATE_PRESET_NAME_TYPE;
    }
}
