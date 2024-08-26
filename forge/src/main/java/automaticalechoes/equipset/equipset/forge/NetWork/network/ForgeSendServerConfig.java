package automaticalechoes.equipset.equipset.forge.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.SendServerConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ForgeSendServerConfig(int setsNum) implements SendServerConfig {

    public static void encode(ForgeSendServerConfig msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.setsNum());
    }
    public static ForgeSendServerConfig decode(FriendlyByteBuf packetBuffer) {
        return new ForgeSendServerConfig(packetBuffer.readInt());
    }

    public static void onMessage(ForgeSendServerConfig msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }

}
