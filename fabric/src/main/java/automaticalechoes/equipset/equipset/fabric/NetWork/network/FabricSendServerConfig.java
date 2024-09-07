package automaticalechoes.equipset.equipset.fabric.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.SendServerConfig;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

public record FabricSendServerConfig(int setsNum) implements SendServerConfig, FabricPacket {

    public static FabricSendServerConfig decode(FriendlyByteBuf packetBuffer) {
        return new FabricSendServerConfig(packetBuffer.readInt());
    }

    @Override
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(this.setsNum());
    }

    @Override
    public PacketType<?> getType() {
        return PacketHandler.FABRIC_SERVER_CONFIG;
    }
}
