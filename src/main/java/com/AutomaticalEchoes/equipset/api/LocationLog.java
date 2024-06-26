package com.AutomaticalEchoes.equipset.api;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;


public class LocationLog extends SlotGetter{
    protected LocationLog(@NotNull ContainerType containerType, int slotNum) {
        super(containerType, slotNum);
    }

    public void clear(){
        this.slotNum = -1;
        this.containerType = ContainerType.TYPE_INVENTORY;
    }

    public void update(ContainerType containerType, int slotNum){
        this.containerType = containerType;
        this.slotNum = slotNum;
    }

    public void update(SlotGetter slotGetter){
        this.containerType = slotGetter.containerType;
        this.slotNum = slotGetter.slotNum;
    }

    public static LocationLog fromTag(CompoundTag tag){
        ContainerType containerType = ContainerType.TYPES.getOrDefault(tag.getString(CONTAINER_TYPE), ContainerType.TYPE_INVENTORY);
        int slotNum = tag.getInt(SLOT_NUM);
        return new LocationLog(containerType, slotNum);
    }

    public boolean isEmpty(){
        return this.containerType == ContainerType.TYPE_INVENTORY && this.slotNum == -1;
    }

    public static LocationLog Default(){
        return new LocationLog(ContainerType.TYPE_INVENTORY, -1);
    }
}
