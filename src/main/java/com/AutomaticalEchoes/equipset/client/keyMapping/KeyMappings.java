package com.AutomaticalEchoes.equipset.client.keyMapping;

import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class KeyMappings {
    public static final HashMap<KeyBinding,Runnable> KEY_MAPPING = new HashMap<>();
    public static final int[] CONSTANTS = new int[]{Keyboard.KEY_1, Keyboard.KEY_2, Keyboard.KEY_3, Keyboard.KEY_4, Keyboard.KEY_5, Keyboard.KEY_6, Keyboard.KEY_7, Keyboard.KEY_8, Keyboard.KEY_O, Keyboard.KEY_P};
    public static final KeyBinding CALL_SET_INVENTORY_KEY = RegisterKeyBinding(new KeyBinding("key.category.equipset.setinvetory",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            Keyboard.KEY_B,
            "key.equipset"), Actions::CallScreen);


    public static void Init(){
        if(EquipSetConfig.KEYMAPPING_R){
            RegisterKeyBinding(new KeyBinding("key.category.equipset.setchange",
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    Keyboard.KEY_R,
                    "key.equipset"),() -> Actions.SendUsePreset(null));
        }

        if(EquipSetConfig.KEYMAPPING_NUMS){
            for (int i = 0; i < EquipSetConfig.PRESET_NUM; i++) {
                int finalI = i;
                RegisterKeyBinding(new KeyBinding("key.category.equipset.setchange.select_%d".replace("%d",String.valueOf(i)),
                        KeyConflictContext.IN_GAME,
                        KeyModifier.CONTROL,
                        CONSTANTS[i],
                        "key.equipset"), () -> Actions.SendUsePreset(finalI));
            }
        }
    }

    public static KeyBinding RegisterKeyBinding(KeyBinding keyBinding, Runnable runnable){
        KEY_MAPPING.put(keyBinding,runnable);
        return keyBinding;
    }

    public static boolean OnClick(){
        for (Map.Entry<KeyBinding, Runnable> entry : KEY_MAPPING.entrySet()) {
            if(entry.getKey().isKeyDown()){
                entry.getValue().run();
                return true;
            }
        }
        return false;
    }
}
