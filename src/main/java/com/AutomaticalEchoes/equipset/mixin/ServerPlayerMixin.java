package com.AutomaticalEchoes.equipset.mixin;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "restoreFrom", at = {@At("RETURN")})
    public void restore(ServerPlayer p_9016_, boolean p_9017_, CallbackInfo ci){
        ((IPlayerInterface)this).restoreFrom(p_9016_);
    }
}
