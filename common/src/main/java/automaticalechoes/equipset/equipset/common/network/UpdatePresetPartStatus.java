package automaticalechoes.equipset.equipset.common.network;


import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import net.minecraft.server.level.ServerPlayer;

public interface UpdatePresetPartStatus{
    int targetNum();
    String partName();
    boolean enable();

    default void handleMessage(UpdatePresetPartStatus msg, ServerPlayer sender) {
        IPlayerInterface player = (IPlayerInterface) sender;
        player.equipSet$updatePartStatus(msg.targetNum(), msg.partName(), msg.enable());
    }
}
