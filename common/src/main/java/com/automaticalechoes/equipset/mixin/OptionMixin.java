package com.automaticalechoes.equipset.mixin;


import com.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;
import com.automaticalechoes.equipset.common.EquipSetOptions;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.*;

import java.util.Arrays;

@Mixin(Options.class)
public abstract class OptionMixin implements EquipSetOptions {

    @Mutable
    @Final
    @Shadow public KeyMapping[] keyMappings;

    @Shadow public abstract void load();

    @Unique
    public void equipset$loadKeyMappingsDiff() {
        KeyMapping[] modKeyMappings = ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]);
        KeyMapping[] registedKeyMapping = Arrays.stream(keyMappings).filter(keyMapping -> keyMapping.getCategory().equals(ModKeyMappings.MOD_CATEGORY)).toArray(KeyMapping[]::new);
        KeyMapping[] removal = Arrays.stream(registedKeyMapping).filter(keyMapping -> Arrays.stream(modKeyMappings).noneMatch(keyMapping1 -> keyMapping == keyMapping1)).toArray(KeyMapping[]::new);
        KeyMapping[] addon = Arrays.stream(modKeyMappings).filter(keyMapping -> Arrays.stream(registedKeyMapping).noneMatch(keyMapping1 -> keyMapping == keyMapping1)).toArray(KeyMapping[]::new);
        keyMappings = ArrayUtils.removeElements(keyMappings, removal);
        keyMappings = ArrayUtils.addAll(keyMappings, addon);
        load();
    }

}
