package automaticalechoes.equipset.equipset.api;

import net.minecraft.server.level.ServerPlayer;


public interface IPlayerInterface {
    PresetManager getEquipmentSets();
    void equipSet$nextSet();
    void useSet(int num, boolean lockCheck);
    void equipSet$updateSet(int num, int cases);
    void equipSet$updateSetName(int num, String s);
    void equipSet$updatePartStatus(int num, String partName, boolean enable);
    void equipSet$restoreFrom(ServerPlayer serverPlayer);
}
