package org.automaticalechoes.equipset.NetWork.network;


import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import automaticalechoes.equipset.equipset.common.network.UpdatePreset;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ForgeUpdatePreset(int targetNum, int cases)implements UpdatePreset {
    // clear, save, lock, unLock, neo
    public static void encode(ForgeUpdatePreset msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeInt(msg.cases);
    }

    public static ForgeUpdatePreset decode(FriendlyByteBuf packetBuffer) {
        return new ForgeUpdatePreset(packetBuffer.readInt(), packetBuffer.readInt());
    }

    static void onMessage(ForgeUpdatePreset msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(context.getSender()));
        context.setPacketHandled(true);
    }

}
