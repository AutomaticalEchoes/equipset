package com.AutomaticalEchoes.equipset.api;

import net.minecraft.nbt.CompoundTag;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresetEquipSet extends LinkedHashMap<String, PresetEquipPart>{
    public static final LinkedHashMap<String, SlotGetter> DEFAULT_PARTS = new LinkedHashMap<>();
    private static final String PARTS = "parts";
    private static final String NAME = "name";
    private static final String LOCK = "lock";
    public static void init() {
        DEFAULT_PARTS.put("head", SlotGetter.INVENTORY_HEAD);
        DEFAULT_PARTS.put("chest", SlotGetter.INVENTORY_CHEST);
        DEFAULT_PARTS.put("leg", SlotGetter.INVENTORY_LEG);
        DEFAULT_PARTS.put("feet", SlotGetter.INVENTORY_FEET);
        DEFAULT_PARTS.put("offhand", SlotGetter.INVENTORY_OFF_HAND);
    }

    private String name;
    private boolean lock = false;

    public PresetEquipSet(String name) {
        this.name = name;
        initDefaultMap();
    }

    public PresetEquipSet(CompoundTag tag){
        this.name = tag.getString(NAME);
        this.lock = tag.getBoolean(LOCK);
        partsFromTag(tag.getCompound(PARTS));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setPartStatus(String part, boolean enable){
        if(containsKey(part) && get(part).isEnable() != enable){
            get(part).setEnable(enable);
            return true;
        }
        return false;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put(PARTS, partsToTag()); ;
        tag.putString(NAME, name);
        tag.putBoolean(LOCK, lock);
        return tag;
    }

    private CompoundTag partsToTag(){
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<String, PresetEquipPart> entry : entrySet()) {
            tag.put(entry.getKey(), entry.getValue().toTag());
        }
        return tag;
    }

    private void initDefaultMap(){
        DEFAULT_PARTS.forEach((s, slotGetter) -> put(s, PresetEquipPart.defaultSetting(slotGetter)));
    }

    private void partsFromTag(CompoundTag tag){
        for (Map.Entry<String, SlotGetter> entry : DEFAULT_PARTS.entrySet()) {
            String partName = entry.getKey();
            put(partName, tag.contains(partName) ? PresetEquipPart.fromTag (tag.getCompound(partName)): PresetEquipPart.defaultSetting(entry.getValue()));
        }
    }

    public void clearSetting(){
        values().forEach(PresetEquipPart::clear);
    }

    public boolean isLock(){
        return this.lock;
    }

    public void lock(){
        this.lock = true;
    }

    public void unLock(){
        this.lock = false;
    }
}
