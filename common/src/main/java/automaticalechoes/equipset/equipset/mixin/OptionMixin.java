package automaticalechoes.equipset.equipset.mixin;

import automaticalechoes.equipset.equipset.api.EquipSetOptions;
import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionMixin implements EquipSetOptions {
    @Unique
    private static boolean equipset$KeyMappingInit = false;
    @Mutable
    @Final
    @Shadow public KeyMapping[] keyMappings;

    @Unique
    public void equipset$loadKeyMappings() {
        KeyMapping[] modKeyMappings = ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]);
        keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, modKeyMappings);
        equipset$KeyMappingInit = true;
    }

    @Override
    public void equipset$removeKeyMappings() {
        KeyMapping[] modKeyMappings = ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]);
        if(equipset$KeyMappingInit) keyMappings = ArrayUtils.removeElements(keyMappings, modKeyMappings);
    }
}
