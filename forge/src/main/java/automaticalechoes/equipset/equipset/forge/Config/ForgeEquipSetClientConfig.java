package automaticalechoes.equipset.equipset.forge.Config;

import automaticalechoes.equipset.equipset.config.Config;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgeEquipSetClientConfig extends Config.Client {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Boolean> KEYMAPPING_R;
    public static final ForgeConfigSpec.ConfigValue<Boolean> KEYMAPPING_NUMS;
    static {
        BUILDER.push("equipset config");
        BUILDER.push("client");
        KEYMAPPING_R = BUILDER.define("should_keymapping_r_register", true);
        KEYMAPPING_NUMS = BUILDER.define("should_keymappings_register", true);
        BUILDER.pop();
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static void OnLoad(){
        Client.KEYMAPPING_R.set(KEYMAPPING_R.get());
        Client.KEYMAPPING_NUMS.set(KEYMAPPING_NUMS.get());
    }
}
