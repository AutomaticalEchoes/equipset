package automaticalechoes.equipset.equipset.forge.Config;

import automaticalechoes.equipset.equipset.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeEquipSetServerConfig extends Config.Server{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> CURSE_CHECK;
    public static final ForgeConfigSpec.ConfigValue<Integer> PRESET_NUM;
    static {
        BUILDER.push("equipset config");
        BUILDER.push("server");
        CURSE_CHECK = BUILDER.comment("cursed item can not change if true").define("enable_curse_check", false);
        PRESET_NUM = BUILDER.defineInRange("preset_nums", 4, 2, 10);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void OnLoad(){
        Server.NUMS.set(PRESET_NUM.get());
        Server.CURSE_CHECK.set(CURSE_CHECK.get());
    }

}
