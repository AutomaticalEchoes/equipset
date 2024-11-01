package org.automaticalechoes.equipset.common.network;



import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.automaticalechoes.equipset.api.IPlayerInterface;

public record UpdatePreset(int targetNum, int cases) implements CustomPacketPayload {
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
