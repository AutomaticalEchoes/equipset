package com.AutomaticalEchoes.equipset.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Predicate;

public class EquipSetConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> CURSE_CHECK;
    public static final ForgeConfigSpec.ConfigValue<Integer> PRESET_NUM;


    public static final ForgeConfigSpec.ConfigValue<Boolean> KEYMAPPING_R;
    public static final ForgeConfigSpec.ConfigValue<Boolean> KEYMAPPING_NUMS;
    static {
        BUILDER.push("equipset config");
        BUILDER.push("server");
        CURSE_CHECK = BUILDER.comment("cursed item can not change if true").define("enable_curse_check", false);
        PRESET_NUM = BUILDER.defineInRange("preset_nums", 4, 2, 10);
        BUILDER.pop();
        BUILDER.push("client");
        KEYMAPPING_R = BUILDER.define("should_keymapping_r_register", true);
        KEYMAPPING_NUMS = BUILDER.define("should_keymappings_register", true);
        BUILDER.pop();
        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
