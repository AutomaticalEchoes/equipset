package com.automaticalechoes.equipset.common;

import org.spongepowered.asm.mixin.Unique;

public interface EquipSetOptions {

    @Unique
    void equipset$loadKeyMappingsDiff();

}
