package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record UpdatePreset(int targetNum, int cases) {
    // clear, save, lock, unLock, neo
    public static void encode(UpdatePreset msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeInt(msg.cases);
    }

    public static UpdatePreset decode(FriendlyByteBuf packetBuffer) {
        return new UpdatePreset(packetBuffer.readInt(), packetBuffer.readInt());
    }

    static void onMessage(UpdatePreset msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(UpdatePreset msg, ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.updateSet(msg.targetNum, msg.cases);
    }
}
