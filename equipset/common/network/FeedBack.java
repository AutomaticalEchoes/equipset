package com.AutomaticalEchoes.equipset.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record FeedBack(Component component){
    public static void encode(FeedBack msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeComponent(msg.component);
    }

    public static FeedBack decode(FriendlyByteBuf packetBuffer) {
        return new FeedBack(packetBuffer.readComponent());
    }

    static void onMessage(FeedBack msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(FeedBack msg, ServerPlayer sender) {
        Minecraft.getInstance().gui.setOverlayMessage(msg.component,false);
    }
}
