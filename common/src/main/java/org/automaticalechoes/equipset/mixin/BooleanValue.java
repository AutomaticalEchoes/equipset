package org.automaticalechoes.equipset.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRules.BooleanValue.class)
public interface BooleanValue {
    @Invoker("create")
    static GameRules.Type<GameRules.BooleanValue> create(boolean pDefaultValue) {
        throw new AssertionError();
    }
}
