package automaticalechoes.equipset.equipset.api;

import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface EquipSetOptions {

    @Unique
    void equipset$loadKeyMappings();

    @Unique
    void equipset$removeKeyMappings();
}
