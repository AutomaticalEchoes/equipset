package com.AutomaticalEchoes.equipset.api;

import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresetManager extends LinkedHashMap<Integer, PresetEquipSet> {

    static final String ID = "id";
    private final Utils.ICode feedBack = new Utils.ICode();
    private PresetManager(){

    }
    public void setName(int num, String name){
        this.get(num).setName(name);
    }

    public int[] unLockedSets() {
        return entrySet().stream().filter(entry -> !entry.getValue().isLock()).mapToInt(Map.Entry::getKey).toArray();
    }

    public int UseSet(EntityPlayerMP serverPlayer, int old, int neo){
        prepareFeedBack(this.get(neo).getName());
        if(old == neo || !containsKey(old)){
            same(serverPlayer, neo);
        }else {
            diff(serverPlayer, old, neo);
        }
        return feedBack.getCode();
    }

    private void diff(EntityPlayerMP serverPlayer, int old, int neo){
        PresetEquipSet oldSet = this.get(old);
        PresetEquipSet neoSet = this.get(neo);
        for (String name : PresetEquipSet.DEFAULT_PARTS.keySet()) {
            PresetEquipPart oldPart = oldSet.get(name);
            PresetEquipPart neoPart = neoSet.get(name);
            if(!neoPart.isEnable() || neoPart.isWearing(serverPlayer) || neoPart.isCurse(serverPlayer, feedBack) || !neoPart.canFindPresetItem(serverPlayer, feedBack)) continue;
            if(oldPart.isWearing(serverPlayer)){
                if(oldPart.canHoldItem(serverPlayer)) oldPart.change(serverPlayer);
                else oldPart.getLocationRecord().update(neoPart.getLocationRecord());
            }
            if(!oldPart.getLocationRecord().equals(neoPart.getLocationRecord()))
                neoPart.change(serverPlayer);
        }
    }

    private void same(EntityPlayerMP serverPlayer, int neo){
        PresetEquipSet neoSet = this.get(neo);
        for (String name : PresetEquipSet.DEFAULT_PARTS.keySet()) {
            PresetEquipPart neoPart = neoSet.get(name);
            if(!neoPart.isEnable() || neoPart.isWearing(serverPlayer) || neoPart.isCurse(serverPlayer, feedBack) || !neoPart.canFindPresetItem(serverPlayer, feedBack)) continue;
            neoSet.get(name).change(serverPlayer);
        }
    }

    private void prepareFeedBack(String name){
        this.feedBack.setCode(1);
    }

    public void copyFrom(PresetManager presetManager){
        this.clear();
        this.putAll(presetManager);
    }

    public NBTTagCompound toTag() {
        NBTTagCompound compoundtag = new NBTTagCompound();
        int i = 0;
        for (Map.Entry<Integer, PresetEquipSet> entry : this.entrySet()) {
            NBTTagCompound set = entry.getValue().toTag();
            set.setInteger(ID, entry.getKey());
            compoundtag.setTag(String.valueOf(i),set);
            i++;
        }
        return compoundtag;
    }

    public static PresetManager FromTag(NBTTagCompound compoundTag) {
        PresetManager presetManager = new PresetManager();
        readTag(compoundTag, presetManager);
        return presetManager;
    }

    public void fromTag(NBTTagCompound compoundTag) {
        this.clear();
        readTag(compoundTag, this);
    }

    private static void readTag(NBTTagCompound compoundTag, PresetManager presetManager) {
        for(int i = 0; i < EquipSetConfig.PRESET_NUM; i++){
            String value = String.valueOf(i);
            if(compoundTag.hasKey(value)){
                NBTTagCompound compound = compoundTag.getCompoundTag(value);
                PresetEquipSet set = new PresetEquipSet(compound);
                Integer id = compound.getInteger(ID);
                presetManager.put(id, set);
            }else {
                presetManager.neoSet();
            }
        }
    }

    public void neoSet(){
        put(neoId(), new PresetEquipSet("Preset " + size()));
    }

    public Integer neoId(){
        int i = 0;
        while (containsKey(i)){
            i++;
        }
        return i;
    }

    public static PresetManager defaultManager(){
        PresetManager presetManager = new PresetManager();
        for (int i = 0; i < EquipSetConfig.PRESET_NUM; i++) {
            presetManager.neoSet();
        }
       return presetManager;
    }

}
