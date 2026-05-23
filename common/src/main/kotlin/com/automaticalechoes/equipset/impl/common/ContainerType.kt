package com.automaticalechoes.equipset.impl.common

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack


// 类型别名，让函数签名更清晰
typealias PlayerPredicate = (ServerPlayer) -> Boolean
typealias SlotGetterFun = (ServerPlayer, Int) -> ItemStack
typealias SlotSetterFun = (ServerPlayer, Int, ItemStack) -> Unit
typealias SizeGetterFun = (ServerPlayer) -> Int
@JvmRecord
data class ContainerType(
    val name: String,
    val useCheck: PlayerPredicate,
    val slotSetter: SlotSetterFun,
    val slotGetter: SlotGetterFun,
    val sizeGetter: SizeGetterFun
) {
    companion object {
        val CODEC: Codec<ContainerType> by lazy {
            Codec.STRING.comapFlatMap(
            { str -> DataResult.success(TYPES[str]) },
            { it.name })
        }


        // 预定义的容器类型，使用 lazy 延迟初始化以避免循环引用
        val TYPE_INVENTORY: ContainerType by lazy {
            ContainerType(
                name = "type_inventory",
                useCheck = { true },
                slotSetter = { player, slot, stack -> player.inventory.setItem(slot, stack) },
                slotGetter = { player, slot -> player.inventory.getItem(slot) },
                sizeGetter = { player -> player.inventory.containerSize }
            )
        }

        val TYPE_ENDER_CHEST: ContainerType by lazy {
            ContainerType(
                name = "type_ender_chest",
                useCheck = { true },
                slotSetter = { player, slot, stack -> player.enderChestInventory.setItem(slot, stack) },
                slotGetter = { player, slot -> player.enderChestInventory.getItem(slot) },
                sizeGetter = { player -> player.enderChestInventory.containerSize }
            )
        }

        var TYPE_TRAVELERS_BACKPACK: ContainerType? = null

        // 使用不可变 Map 替代可变 HashMap，保证线程安全
        val TYPES: Map<String, ContainerType> by lazy {
            buildMap {
                put(TYPE_INVENTORY.name, TYPE_INVENTORY)
                put(TYPE_ENDER_CHEST.name, TYPE_ENDER_CHEST)
                TYPE_TRAVELERS_BACKPACK?.let { put(it.name, it) }
            }
        }

//        // 初始化方法（如果还需要手动触发）
//        fun init() {
//            // 访问 TYPES 会触发 lazy 初始化
//            TYPES.forEach { (name, type) ->
//                // 可在此处进行额外初始化操作
//            }
//        }
    }

    // 这些函数现在可以安全调用，因为属性都是非空的
    fun canUse(player: ServerPlayer): Boolean = useCheck(player)
    fun size(player: ServerPlayer): Int = sizeGetter(player)
    fun setItem(player: ServerPlayer, slot: Int, stack: ItemStack) = slotSetter(player, slot, stack)
    fun getItem(player: ServerPlayer, slot: Int): ItemStack = slotGetter(player, slot)
}