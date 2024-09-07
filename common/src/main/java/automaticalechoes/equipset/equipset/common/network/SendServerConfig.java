package automaticalechoes.equipset.equipset.common.network;

import automaticalechoes.equipset.equipset.EquipSet;
import net.minecraft.server.level.ServerPlayer;

public interface SendServerConfig {

    int setsNum();
    default void handleMessage() {
        EquipSet.Client.InitServerConfig(setsNum());
    }
}
