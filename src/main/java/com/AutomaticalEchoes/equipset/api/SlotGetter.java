package com.AutomaticalEchoes.equipset.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

    public ItemStack getItem(EntityPlayerMP serverPlayer){
        return containerType.getContainer(serverPlayer).getStackInSlot(slotNum);
    }

    public void onChange(EntityPlayerMP serverPlayer, ItemStack itemStack){
        IInventory container = containerType.getContainer(serverPlayer);
        container.setInventorySlotContents(slotNum,itemStack);
        container.markDirty();
        serverPlayer.inventoryContainer.detectAndSendChanges();
    }

    public NBTTagCompound toTag() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(CONTAINER_TYPE, containerType.Name());
        tag.setInteger(SLOT_NUM, slotNum);
        return tag;
    }

    public static SlotGetter fromTag(NBTTagCompound tag){
        ContainerType containerType = ContainerType.TYPES.getOrDefault(tag.getString(CONTAINER_TYPE), ContainerType.TYPE_INVENTORY);
        int slotNum = tag.getInteger(SLOT_NUM);
        return new SlotGetter(containerType,slotNum);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SlotGetter && ((SlotGetter)obj).containerType.equals(this.containerType) &&  ((SlotGetter)obj).slotNum == this.slotNum;
    }
}


