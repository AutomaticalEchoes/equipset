package automaticalechoes.equipset.equipset.mixin;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.common.network.EquipSetNetWork;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = {@At("RETURN")})
    private void placeNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        EquipSet.NETWORK.ifPresent(network -> {network.SendServerConfig(serverPlayer);});
    }
}
