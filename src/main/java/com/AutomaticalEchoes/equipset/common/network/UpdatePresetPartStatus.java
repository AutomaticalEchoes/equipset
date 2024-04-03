package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record UpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
    public static void encode(UpdatePresetPartStatus msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeUtf(msg.partName);
        packetBuffer.writeBoolean(msg.enable);
    }

    public static UpdatePresetPartStatus decode(FriendlyByteBuf packetBuffer) {
        return new UpdatePresetPartStatus(packetBuffer.readInt(), packetBuffer.readUtf(), packetBuffer.readBoolean());
    }

    static void onMessage(UpdatePresetPartStatus msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(UpdatePresetPartStatus msg, ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.updatePartStatus(msg.targetNum, msg.partName, msg.enable);
    }
}
