package com.automaticalechoes.equipset.common.network;



import com.automaticalechoes.equipset.common.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record UpdatePresetPartStatus(int targetNum, String partName, boolean enable)  implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, UpdatePresetPartStatus> CODEC =
            StreamCodec.composite(ByteBufCodecs.INT,UpdatePresetPartStatus::targetNum,
                    ByteBufCodecs.STRING_UTF8,UpdatePresetPartStatus::partName,
                    ByteBufCodecs.BOOL,UpdatePresetPartStatus::enable,
                    UpdatePresetPartStatus::new);
    public void handleMessage(ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.equipSet$updatePartStatus(targetNum(), partName(), enable());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.UPDATE_PRESET_PART_STATUS_TYPE;
    }
}
