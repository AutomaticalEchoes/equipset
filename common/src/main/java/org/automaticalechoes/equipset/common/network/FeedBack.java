package org.automaticalechoes.equipset.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record FeedBack(Component component) implements CustomPacketPayload  {
    public static final StreamCodec<RegistryFriendlyByteBuf, FeedBack> CODEC = StreamCodec.composite(Component.C)
    public void handleMessage() {
        Minecraft.getInstance().gui.setOverlayMessage(this.component(),false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.FEED_BACK_TYPE;
    }

}
