package automaticalechoes.equipset.equipset.client.keyMapping;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.api.EquipSetOptions;
import automaticalechoes.equipset.equipset.config.Config;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;
@Environment(EnvType.CLIENT)
public class ModKeyMappings {
    public static final String MOD_CATEGORY = "key.equipset";
    public static final HashMap<KeyMapping,Runnable> KEY_MAPPING = new HashMap<>();
    public static final int[] CONSTANTS = new int[]{InputConstants.KEY_1, InputConstants.KEY_2, InputConstants.KEY_3, InputConstants.KEY_4, InputConstants.KEY_5, InputConstants.KEY_6, InputConstants.KEY_7, InputConstants.KEY_8, InputConstants.KEY_O, InputConstants.KEY_P};
    public static KeyMapping CALL_SET_INVENTORY_KEY = RegisterKeyMapping(new KeyMapping("key.category.equipset.setinvetory",
            InputConstants.KEY_B,
            MOD_CATEGORY), Actions::CallScreen);


    public static void Init(){
        ((EquipSetOptions) Minecraft.getInstance().options).equipset$removeKeyMappings();
        KEY_MAPPING.clear();
        CALL_SET_INVENTORY_KEY = RegisterKeyMapping(new KeyMapping("key.category.equipset.setinvetory",
                InputConstants.KEY_B,
                MOD_CATEGORY), Actions::CallScreen);
        if(Config.KeymappingR()){
            RegisterKeyMapping(new KeyMapping("key.category.equipset.setchange",
                    InputConstants.KEY_R,
                    MOD_CATEGORY),() -> Actions.SendUsePreset(-1));
        }
        if(Config.KeymappingNums()){
            int nums = EquipSet.Client.SERVER_SET_NUMS;
                for (int i = 0; i < nums; i++) {
                    int finalI = i;
                    RegisterKeyMapping(new KeyMapping("key.category.equipset.setchange.select_%d".formatted(i),
                            CONSTANTS[i],
                            MOD_CATEGORY), () -> Actions.SendUsePreset(finalI));
                }
        }
        ((EquipSetOptions) Minecraft.getInstance().options).equipset$loadKeyMappings();
    }

    public static KeyMapping RegisterKeyMapping(KeyMapping keyMapping, Runnable runnable){
        KEY_MAPPING.put(keyMapping,runnable);
        return keyMapping;
    }

    public static boolean OnClick(){
        for (Map.Entry<KeyMapping, Runnable> entry : KEY_MAPPING.entrySet()) {
            if(entry.getKey().isDown()){
                entry.getValue().run();
//                EquipSet.LOGGER.info("onclick");
                return true;
            }
        }
        return false;
    }
}
