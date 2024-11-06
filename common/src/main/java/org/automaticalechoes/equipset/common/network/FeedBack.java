package org.automaticalechoes.equipset.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record FeedBack(Component component) implements CustomPacketPayload  {
    public static final StreamCodec<FriendlyByteBuf, FeedBack> CODEC = StreamCodec.composite(ComponentSerialization.TRUSTED_CONTEXT_FREE_STREAM_CODEC, FeedBack::component, FeedBack::new);
    public void handleMessage() {
        Minecraft.getInstance().gui.setOverlayMessage(this.component(),false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.FEED_BACK_TYPE;
    }

}
