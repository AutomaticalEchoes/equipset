package com.AutomaticalEchoes.equipset.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.function.Function;

public record ContainerType(String Name, Function<ServerPlayer,Container> Getter) {
    public static HashMap<String, ContainerType> TYPES = new HashMap<>();
    public static ContainerType TYPE_INVENTORY = new ContainerType("type_inventory", Player::getInventory) ;
    public static ContainerType TYPE_ENDER_CHEST = new ContainerType("type_ender_chest",Player::getEnderChestInventory);

    public static void init(){
        TYPES.clear();
        TYPES.put(TYPE_INVENTORY.Name(),TYPE_INVENTORY);
//        TYPES.put(TYPE_ENDER_CHEST.Name(), TYPE_ENDER_CHEST);
    }

    public Container getContainer(ServerPlayer serverPlayer){
        return Getter.apply(serverPlayer);
    }
}
