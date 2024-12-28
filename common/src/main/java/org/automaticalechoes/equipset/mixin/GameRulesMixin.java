package org.automaticalechoes.equipset.mixin;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.BiConsumer;

@Mixin(GameRules.class)
public interface GameRulesMixin {
    @Invoker("register")
    static <T extends GameRules.Value<T>> GameRules.Key<T> register(String pName, GameRules.Category pCategory, GameRules.Type<T> pType) {
        throw new AssertionError();
    }
}
