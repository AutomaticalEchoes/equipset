package com.automaticalechoes.equipset.mixin;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.ToIntFunction;

@Mixin(GameRules.class)
public interface GameRulesMixin {
    @Invoker("registerBoolean")
    static GameRule<Boolean> registerBoolean(String id, GameRuleCategory category, boolean defaultValue) {
        return null;
    }

    @Invoker("registerInteger")
    static GameRule<Integer> registerInteger(String id, GameRuleCategory category, int defaultValue, int min, int max) {
        return null;
    }

    @Invoker("register")
    static <T> GameRule<T> register(String id, GameRuleCategory category, GameRuleType typeHint, ArgumentType<T> argumentType, Codec<T> codec, T defaultValue, FeatureFlagSet requiredFeatures, GameRules.VisitorCaller<T> visitorCaller, ToIntFunction<T> commandResultFunction) {
        return null;
    }


}
