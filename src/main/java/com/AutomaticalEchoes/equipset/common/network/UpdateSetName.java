package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record UpdateSetName(int suitNum, String suitName) {
    public static void encode(UpdateSetName msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.suitNum);
        packetBuffer.writeComponent(Component.translatable(msg.suitName));
    }
    public static UpdateSetName decode(FriendlyByteBuf packetBuffer) {
        return new UpdateSetName(packetBuffer.readInt(),packetBuffer.readComponent().getString());
    }
    static void onMessage(UpdateSetName msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(UpdateSetName msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).updateSetName(msg.suitNum,msg.suitName);
    }
}
