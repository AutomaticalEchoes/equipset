package org.automaticalechoes.equipset.NetWork.network;


import automaticalechoes.equipset.equipset.common.network.UpdatePresetPartStatus;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;

public record FabricUpdatePresetPartStatus(int targetNum, String partName, boolean enable) implements UpdatePresetPartStatus, FabricPacket {

    public static FabricUpdatePresetPartStatus decode(FriendlyByteBuf packetBuffer) {
        return new FabricUpdatePresetPartStatus(packetBuffer.readInt(), packetBuffer.readUtf(), packetBuffer.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(targetNum);
        packetBuffer.writeUtf(partName);
        packetBuffer.writeBoolean(enable);
    }

    @Override
    public PacketType<?> getType() {
        return PacketHandler.FABRIC_UPDATE_PRESET_PART_STATUS;
    }
}
