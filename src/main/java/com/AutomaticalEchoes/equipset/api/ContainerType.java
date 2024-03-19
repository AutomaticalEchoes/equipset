package com.AutomaticalEchoes.equipset.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;


public final class ContainerType {
    private final String Name;
    private final Function<EntityPlayerMP, IInventory> Getter;
    public static HashMap<String, ContainerType> TYPES = new HashMap<>();
    public static ContainerType TYPE_INVENTORY = new ContainerType("type_inventory", entityPlayerMP -> entityPlayerMP.inventory) ;
    public static ContainerType TYPE_ENDER_CHEST = new ContainerType("type_ender_chest",EntityPlayer::getInventoryEnderChest);

    public static void init(){
        TYPES.clear();
        TYPES.put(TYPE_INVENTORY.Name(),TYPE_INVENTORY);
//        TYPES.put(TYPE_ENDER_CHEST.Name(), TYPE_ENDER_CHEST);
    }



    ContainerType(String Name, Function<EntityPlayerMP, IInventory> Getter) {
        this.Name = Name;
        this.Getter = Getter;
    }

    public IInventory getContainer(EntityPlayerMP serverPlayer){
        return Getter.apply(serverPlayer);
    }

    public String Name() {
        return Name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ContainerType that = (ContainerType) obj;
        return Objects.equals(this.Name, that.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }

    @Override
    public String toString() {
        return "ContainerType[" +
                "Name=" + Name + ']';
    }
}