package org.automaticalechoes.equipset.api;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.function.TriConsumer;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public record ContainerType(String Name, Function<ServerPlayer, Boolean> canUse, TriConsumer<ServerPlayer, Integer, ItemStack> slotSetter, BiFunction<ServerPlayer, Integer, ItemStack> slotGetter, Function<ServerPlayer, Integer> sizeGetter) {
    public static HashMap<String, ContainerType> TYPES = new HashMap<>();
    public static ContainerType TYPE_INVENTORY = new ContainerType("type_inventory",
            serverPlayer -> true,
            (serverPlayer, slotNum, itemStack) -> {serverPlayer.getInventory().setItem(slotNum,itemStack);},
            (serverPlayer, slotNum) -> serverPlayer.getInventory().getItem(slotNum),
            serverPlayer -> serverPlayer.getInventory().getContainerSize()) ;
    public static ContainerType TYPE_ENDER_CHEST = new ContainerType("type_ender_chest",
            serverPlayer -> true,
            (serverPlayer, slotNum, itemStack) -> {serverPlayer.getEnderChestInventory().setItem(slotNum,itemStack);},
            (serverPlayer, slotNum) -> serverPlayer.getEnderChestInventory().getItem(slotNum),
            serverPlayer -> serverPlayer.getEnderChestInventory().getContainerSize()) ;
    public static ContainerType TYPE_TRAVELERSBACKPACK;

    public static void init(){
        TYPES.clear();
        TYPES.put(TYPE_INVENTORY.Name(),TYPE_INVENTORY);
//        TYPES.put(TYPE_ENDER_CHEST.Name(), TYPE_ENDER_CHEST);
    }

    public boolean canUse(ServerPlayer player){
        return canUse().apply(player);
    }

    public int size(ServerPlayer player){
        return sizeGetter.apply(player);
    }

    public void setItem(ServerPlayer serverPlayer, int slotNum, ItemStack itemStack){
        slotSetter.accept(serverPlayer, slotNum,itemStack);
    }

    public ItemStack getItem(ServerPlayer serverPlayer, int slotNum){
        return slotGetter.apply(serverPlayer, slotNum);
    }
}
