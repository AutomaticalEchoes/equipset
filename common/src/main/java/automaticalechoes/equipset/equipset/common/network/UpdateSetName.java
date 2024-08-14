package automaticalechoes.equipset.equipset.common.network;


import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import net.minecraft.server.level.ServerPlayer;

public interface UpdateSetName {
    int suitNum();
    String suitName();

    default void handleMessage(UpdateSetName msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).equipSet$updateSetName(msg.suitNum(),msg.suitName());
    }
}
