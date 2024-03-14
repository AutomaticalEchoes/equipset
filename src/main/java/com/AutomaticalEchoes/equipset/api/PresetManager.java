package com.AutomaticalEchoes.equipset.api;

import com.AutomaticalEchoes.equipset.config.ConfigValue;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresetManager extends LinkedHashMap<Integer, PresetEquipSet> {

    static final String ID = "id";
    private MutableComponent feedBack;
    private PresetManager(){

    }
    public void setName(int num, String name){
        this.get(num).setName(name);
    }

    public int[] unLockedSets() {
        return entrySet().stream().filter(entry -> !entry.getValue().isLock()).mapToInt(Map.Entry::getKey).toArray();
    }

    public Component UseSet(ServerPlayer serverPlayer, int old, int neo){
        prepareFeedBack(this.get(neo).getName());
        if(old == neo || !containsKey(old)){
            same(serverPlayer, neo);
        }else {
            diff(serverPlayer, old, neo);
        }
        return feedBack;
    }

    private void diff(ServerPlayer serverPlayer, int old, int neo){
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

    private void same(ServerPlayer serverPlayer, int neo){
        PresetEquipSet neoSet = this.get(neo);
        for (String name : PresetEquipSet.DEFAULT_PARTS.keySet()) {
            PresetEquipPart neoPart = neoSet.get(name);
            if(!neoPart.isEnable() || neoPart.isWearing(serverPlayer) || neoPart.isCurse(serverPlayer, feedBack) || !neoPart.canFindPresetItem(serverPlayer, feedBack)) continue;
            neoSet.get(name).change(serverPlayer);
        }
    }

    private void prepareFeedBack(String name){
        this.feedBack = Utils.Total.plainCopy();
        this.feedBack.append(Component.literal(name).withStyle(ChatFormatting.ITALIC));
    }

    public CompoundTag toTag() {
        CompoundTag compoundtag = new CompoundTag();
        int i = 0;
        for (Map.Entry<Integer, PresetEquipSet> entry : this.entrySet()) {
            CompoundTag set = entry.getValue().toTag();
            set.putInt(ID, entry.getKey());
            compoundtag.put(String.valueOf(i),set);
            i++;
        }
        return compoundtag;
    }

    public static PresetManager FromTag(CompoundTag compoundTag) {
        PresetManager presetManager = new PresetManager();
        readTag(compoundTag, presetManager);
        return presetManager;
    }

    public void fromTag(CompoundTag compoundTag) {
        this.clear();
        readTag(compoundTag, this);
    }

    private static void readTag(CompoundTag compoundTag, PresetManager presetManager) {
        for(int i = 0; i < ConfigValue.NUMS; i++){
            String value = String.valueOf(i);
            if(compoundTag.contains(value)){
                CompoundTag compound = compoundTag.getCompound(value);
                PresetEquipSet set = new PresetEquipSet(compound);
                Integer id = compound.getInt(ID);
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
        for (int i = 0; i < ConfigValue.NUMS; i++) {
            presetManager.neoSet();
        }
       return presetManager;
    }

}
