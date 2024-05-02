package com.AutomaticalEchoes.equipset.client.keyMapping;

import com.AutomaticalEchoes.equipset.config.ConfigValue;
import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.HashMap;
import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class KeyMappings {
    public static final HashMap<KeyMapping,Runnable> KEY_MAPPING = new HashMap<>();
    public static final int[] CONSTANTS = new int[]{InputConstants.KEY_1, InputConstants.KEY_2, InputConstants.KEY_3, InputConstants.KEY_4, InputConstants.KEY_5, InputConstants.KEY_6, InputConstants.KEY_7, InputConstants.KEY_8, InputConstants.KEY_O, InputConstants.KEY_P};
    public static final KeyMapping CALL_SET_INVENTORY_KEY = RegisterKeyMapping(new KeyMapping("key.category.equipset.setinvetory",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.getKey(InputConstants.KEY_B,0),
            "key.equipset"), Actions::CallScreen);


    public static void Init(){
        KEY_MAPPING.clear();
        if(ConfigValue.KEYMAPPING_R){
            RegisterKeyMapping(new KeyMapping("key.category.equipset.setchange",
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    InputConstants.getKey(InputConstants.KEY_R,0 ),
                    "key.equipset"),() -> Actions.SendUsePreset(-1));
        }
        if(ConfigValue.KEYMAPPING_NUMS){
            for (int i = 0; i < ConfigValue.NUMS; i++) {
                int finalI = i;
                RegisterKeyMapping(new KeyMapping("key.category.equipset.setchange.select_%d".formatted(i),
                        KeyConflictContext.IN_GAME,
                        KeyModifier.CONTROL,
                        InputConstants.getKey(CONSTANTS[i],0 ),
                        "key.equipset"), () -> Actions.SendUsePreset(finalI));
            }
        }
    }

    public static KeyMapping RegisterKeyMapping(KeyMapping keyMapping, Runnable runnable){
        KEY_MAPPING.put(keyMapping,runnable);
        return keyMapping;
    }

    public static boolean OnClick(){
        for (Map.Entry<KeyMapping, Runnable> entry : KEY_MAPPING.entrySet()) {
            if(entry.getKey().isDown()){
                entry.getValue().run();
                return true;
            }
        }
        return false;
    }
}
