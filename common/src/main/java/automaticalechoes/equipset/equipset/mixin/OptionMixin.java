package automaticalechoes.equipset.equipset.mixin;

import automaticalechoes.equipset.equipset.api.EquipSetOptions;
import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.*;

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
        if(equipset$KeyMappingInit) keyMappings = ArrayUtils.removeElements(keyMappings, modKeyMappings);
        keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, modKeyMappings);
        equipset$KeyMappingInit = true;
    }
}
