package com.AutomaticalEchoes.equipset.api;


public interface IPlayerInterface {
    PresetManager getEquipmentSets();
    void nextSet();
    void useSet(int num, boolean addWhileLock);
    void updateSet(int num, int cases);
    void updateSetName(int num, String s);
    void updatePartStatus(int num, String partName, boolean enable);
}
