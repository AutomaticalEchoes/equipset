package automaticalechoes.equipset.equipset.forge.NetWork.network;


import automaticalechoes.equipset.equipset.common.network.UpdatePresetPartStatus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ForgeUpdatePresetPartStatus(int targetNum, String partName, boolean enable) implements UpdatePresetPartStatus {
    public static void encode(ForgeUpdatePresetPartStatus msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeUtf(msg.partName);
        packetBuffer.writeBoolean(msg.enable);
    }

    public static ForgeUpdatePresetPartStatus decode(FriendlyByteBuf packetBuffer) {
        return new ForgeUpdatePresetPartStatus(packetBuffer.readInt(), packetBuffer.readUtf(), packetBuffer.readBoolean());
    }

    static void onMessage(UpdatePresetPartStatus msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }


}
