package org.automaticalechoes.equipset.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.IntegerValue.class)
public interface IntegerValue {
    @Invoker("create")
    static GameRules.Type<GameRules.IntegerValue> create(int pDefaultValue, int pMin, int pMax, BiConsumer<MinecraftServer, GameRules.IntegerValue> pChangeListener) {
        throw new AssertionError();
    }
}
