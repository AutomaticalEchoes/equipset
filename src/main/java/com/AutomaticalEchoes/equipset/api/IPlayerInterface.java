package com.AutomaticalEchoes.equipset.api;

import com.AutomaticalEchoes.equipset.api.PresetManager;


public interface IPlayerInterface {
    PresetManager getEquipmentSets();
    void nextSet();
    void useSet(int num, boolean addWhileLock);
    void updateSet(int num, int cases);
    void updateSetName(int num, String s);
    void updatePartStatus(int num, String partName, boolean enable);
}
