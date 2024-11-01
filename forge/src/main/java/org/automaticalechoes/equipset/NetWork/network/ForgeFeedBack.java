package org.automaticalechoes.equipset.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.FeedBack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ForgeFeedBack(Component component) implements FeedBack {
    public static void encode(ForgeFeedBack msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeComponent(msg.component);
    }

    public static ForgeFeedBack decode(FriendlyByteBuf packetBuffer) {
        return new ForgeFeedBack(packetBuffer.readComponent());
    }

    static void onMessage(ForgeFeedBack msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(msg::handleMessage);
        context.setPacketHandled(true);
    }
}
