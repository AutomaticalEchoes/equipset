package org.automaticalechoes.equipset.api;

import net.minecraft.server.level.ServerPlayer;


public interface IPlayerInterface {
    PresetManager equipSet$getEquipmentSets();
    void equipSet$nextSet();
    void equipSet$useSet(int num, boolean lockCheck);
    void equipSet$updateSet(int num, int cases);
    void equipSet$updateSetName(int num, String s);
    void equipSet$updatePartStatus(int num, String partName, boolean enable);
    void equipSet$restoreFrom(ServerPlayer serverPlayer);
    void equipSet$resize(int nums);
}
