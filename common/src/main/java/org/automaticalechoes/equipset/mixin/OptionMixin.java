package org.automaticalechoes.equipset.mixin;


import com.google.common.collect.Maps;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.automaticalechoes.equipset.api.EquipSetOptions;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;
import org.spongepowered.asm.mixin.*;

import java.util.Arrays;
import java.util.function.Predicate;

@Mixin(Options.class)
public abstract class OptionMixin implements EquipSetOptions {
    @Unique
    private static boolean equipset$KeyMappingInit = false;
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
        equipset$KeyMappingInit = true;
        load();
    }

}
