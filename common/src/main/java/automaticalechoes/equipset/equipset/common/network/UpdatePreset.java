package automaticalechoes.equipset.equipset.common.network;


import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import net.minecraft.server.level.ServerPlayer;

public interface UpdatePreset {
    // clear, save, lock, unLock, neo
    int targetNum();
    int cases();

    default void handleMessage(UpdatePreset msg,ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.equipSet$updateSet(targetNum(),cases());
    }
}
