package automaticalechoes.equipset.equipset.common.network;

import automaticalechoes.equipset.equipset.EquipSet;
import net.minecraft.server.level.ServerPlayer;

public interface AskConfig {

    void SendServerConfig(ServerPlayer serverPlayer);
    default void handleMessage(AskConfig msg, ServerPlayer sender) {;
        SendServerConfig(sender);
    }

}
