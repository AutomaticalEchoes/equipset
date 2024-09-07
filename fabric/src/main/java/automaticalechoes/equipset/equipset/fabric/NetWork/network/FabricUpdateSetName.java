package automaticalechoes.equipset.equipset.fabric.NetWork.network;


import automaticalechoes.equipset.equipset.common.network.UpdateSetName;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public record FabricUpdateSetName(int suitNum, String suitName) implements UpdateSetName, FabricPacket {

    public static FabricUpdateSetName decode(FriendlyByteBuf packetBuffer) {
        return new FabricUpdateSetName(packetBuffer.readInt(),packetBuffer.readComponent().getString());
    }

    @Override
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(suitNum);
        packetBuffer.writeComponent(Component.translatable(suitName));
    }

    @Override
    public PacketType<?> getType() {
        return PacketHandler.FABRIC_UPDATE_SET_NAME;
    }
}
