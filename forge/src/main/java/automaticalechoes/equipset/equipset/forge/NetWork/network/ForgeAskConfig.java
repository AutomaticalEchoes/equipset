package automaticalechoes.equipset.equipset.forge.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.AskConfig;
import automaticalechoes.equipset.equipset.config.Config;
import automaticalechoes.equipset.equipset.forge.Events.CommonModEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ForgeAskConfig implements AskConfig {

    public static void encode(ForgeAskConfig msg, FriendlyByteBuf packetBuffer) {
    }

    public static ForgeAskConfig decode(FriendlyByteBuf packetBuffer) {
        return new ForgeAskConfig();
    }

    static void onMessage(ForgeAskConfig msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg, context.getSender()));
        context.setPacketHandled(true);
    }

    @Override
    public void SendServerConfig(ServerPlayer serverPlayer) {
        CommonModEvents.NetWork.sendTo(new ForgeSendServerConfig(Config.Server.NUMS()), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
