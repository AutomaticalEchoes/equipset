package org.automaticalechoes.equipset.common.network;



import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.automaticalechoes.equipset.api.IPlayerInterface;

public record UpdatePresetPartStatus(int targetNum, String partName, boolean enable)  implements CustomPacketPayload {
    public void handleMessage(ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.equipSet$updatePartStatus(targetNum(), partName(), enable());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.UPDATE_PRESET_PART_STATUS_TYPE;
    }
}
