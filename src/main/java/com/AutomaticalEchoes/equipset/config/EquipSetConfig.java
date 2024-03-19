package com.AutomaticalEchoes.equipset.config;

import com.AutomaticalEchoes.equipset.EquipSet;
import net.minecraftforge.common.config.Config;

@Config(modid = EquipSet.MODID,name = "equipset-config")
public class EquipSetConfig {

    @Config.Comment("cursed item can not change if true")
    public static boolean CURSE_CHECK = false;
    @Config.Comment("preset_nums")
    @Config.RangeInt(min = 2 , max = 10)
    public static int PRESET_NUM = 4;

    @Config.Comment("should_keymapping_r_register")
    public static boolean KEYMAPPING_R = true;
    @Config.Comment("should_keymappings_register")
    public static boolean KEYMAPPING_NUMS = true;
}
