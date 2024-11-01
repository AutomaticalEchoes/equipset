package org.automaticalechoes.equipset.api;

import org.spongepowered.asm.mixin.Unique;

public interface EquipSetOptions {

    @Unique
    void equipset$loadKeyMappings();

    @Unique
    void equipset$removeKeyMappings();
}
