package com.AutomaticalEchoes.equipset.mixin;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public class ServerPlayerMixin {
    @Inject(method = "copyFrom", at = { @At("RETURN")} )
    public void restoreFrom(EntityPlayerMP p_9016_, boolean p_9017_, CallbackInfo callbackInfo){
        ((IPlayerInterface) this).restoreFrom(p_9016_);
    }
}
