package automaticalechoes.equipset.equipset.fabric.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.FeedBack;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public record FabricFeedBack(Component component) implements FeedBack , FabricPacket {

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
