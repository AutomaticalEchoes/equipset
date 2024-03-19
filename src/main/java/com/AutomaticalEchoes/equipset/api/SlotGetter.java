package com.AutomaticalEchoes.equipset.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class SlotGetter {
    static final String CONTAINER_TYPE = "type";
    static final String SLOT_NUM = "num";
    public static final SlotGetter INVENTORY_HEAD = new SlotGetter(ContainerType.TYPE_INVENTORY,39);
    public static final SlotGetter INVENTORY_CHEST = new SlotGetter(ContainerType.TYPE_INVENTORY,38);
    public static final SlotGetter INVENTORY_LEG = new SlotGetter(ContainerType.TYPE_INVENTORY,37);
    public static final SlotGetter INVENTORY_FEET = new SlotGetter(ContainerType.TYPE_INVENTORY,36);
    public static final SlotGetter INVENTORY_OFF_HAND =  new SlotGetter(ContainerType.TYPE_INVENTORY, 40);

    protected ContainerType containerType;
    protected int slotNum;

    public SlotGetter(@Nonnull ContainerType containerType , int slotNum){
        this.containerType = containerType;
        this.slotNum = slotNum;
    }

    public ItemStack getItem(ServerPlayer serverPlayer){
        return containerType.getContainer(serverPlayer).getItem(slotNum);
    }

    public void onChange(ServerPlayer serverPlayer, ItemStack itemStack){
        containerType.getContainer(serverPlayer).setItem(slotNum,itemStack);
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString(CONTAINER_TYPE, containerType.Name());
        tag.putInt(SLOT_NUM, slotNum);
        return tag;
    }

    public static SlotGetter fromTag(CompoundTag tag){
        ContainerType containerType = ContainerType.TYPES.getOrDefault(tag.getString(CONTAINER_TYPE), ContainerType.TYPE_INVENTORY);
        int slotNum = tag.getInt(SLOT_NUM);
        return new SlotGetter(containerType,slotNum);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SlotGetter slotGetter && slotGetter.containerType.equals(this.containerType) && slotGetter.slotNum == this.slotNum;
    }
}


