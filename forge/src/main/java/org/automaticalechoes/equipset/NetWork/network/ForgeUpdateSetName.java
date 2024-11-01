package org.automaticalechoes.equipset.NetWork.network;


import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import automaticalechoes.equipset.equipset.common.network.UpdateSetName;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ForgeUpdateSetName(int suitNum, String suitName) implements UpdateSetName {
    public static void encode(ForgeUpdateSetName msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.suitNum);
        packetBuffer.writeComponent(Component.translatable(msg.suitName));
    }
    public static ForgeUpdateSetName decode(FriendlyByteBuf packetBuffer) {
        return new ForgeUpdateSetName(packetBuffer.readInt(),packetBuffer.readComponent().getString());
    }
    static void onMessage(ForgeUpdateSetName msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(context.getSender()));
        context.setPacketHandled(true);
    }


}
