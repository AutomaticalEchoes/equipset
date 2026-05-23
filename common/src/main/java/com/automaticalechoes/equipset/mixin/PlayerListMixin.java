package com.automaticalechoes.equipset.mixin;


import com.automaticalechoes.equipset.Constants;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "placeNewPlayer", at = {@At("RETURN")})
    private void placeNewPlayer(Connection pConnection, ServerPlayer pPlayer, CommonListenerCookie pCookie, CallbackInfo ci) {
        Constants.NETWORK.ifPresent(network -> {network.SendServerConfig(pPlayer, server);});
    }
}
