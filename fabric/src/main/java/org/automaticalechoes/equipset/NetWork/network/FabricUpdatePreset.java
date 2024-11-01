package org.automaticalechoes.equipset.NetWork.network;


import automaticalechoes.equipset.equipset.common.network.UpdatePreset;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public record FabricUpdatePreset(int targetNum, int cases)implements UpdatePreset , FabricPacket {
    // clear, save, lock, unLock, neo
    public static FabricUpdatePreset decode(FriendlyByteBuf packetBuffer) {
        return new FabricUpdatePreset(packetBuffer.readInt(), packetBuffer.readInt());
    }

    @Override
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(this.targetNum);
        packetBuffer.writeInt(this.cases);
    }

    @Override
    public PacketType<?> getType() {
        return PacketHandler.FABRIC_UPDATE_PRESET;
    }
}
