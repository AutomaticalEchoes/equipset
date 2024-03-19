package com.AutomaticalEchoes.equipset.config;

public class ConfigValue {
    public static boolean CURSE_CHECK = false;
    public static boolean KEYMAPPING_R = true;
    public static boolean KEYMAPPING_NUMS = true;
    public static int NUMS = 4;
    public static void reInit(){
        CURSE_CHECK = EquipSetConfig.CURSE_CHECK.get();
        KEYMAPPING_R = EquipSetConfig.KEYMAPPING_R.get();
        KEYMAPPING_NUMS = EquipSetConfig.KEYMAPPING_NUMS.get();
        NUMS = EquipSetConfig.PRESET_NUM.get();
    }
}
