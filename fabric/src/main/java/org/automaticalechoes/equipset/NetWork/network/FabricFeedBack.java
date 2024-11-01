package org.automaticalechoes.equipset.NetWork.network;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import org.automaticalechoes.equipset.common.network.FeedBack;

public record FabricFeedBack(Component component) implements FeedBack, FabricPacket {

    public static FabricFeedBack decode(FriendlyByteBuf packetBuffer) {
        return new FabricFeedBack(packetBuffer.readComponent());
    }

    @Override
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeComponent(component);
    }

    @Override
    public PacketType<?> getType() {
        return PacketHandler.FABRIC_FEED_BACK;
    }
}
