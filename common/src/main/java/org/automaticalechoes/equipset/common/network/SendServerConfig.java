package org.automaticalechoes.equipset.common.network;


import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.automaticalechoes.equipset.EquipSet;

public record SendServerConfig(int setsNum) implements CustomPacketPayload {
    public void handleMessage() {
        EquipSet.Client.InitServerConfig(setsNum());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return Packets.SERVER_CONFIG_TYPE;
    }
}
