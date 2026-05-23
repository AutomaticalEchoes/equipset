
package com.automaticalechoes.equipset.api

import com.automaticalechoes.equipset.impl.common.ContainerType
import com.automaticalechoes.equipset.impl.common.EqsSlotListener
import com.mojang.serialization.Codec
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

typealias BUILDER <T> = (CompoundTag) -> T

interface SlotListener{
    companion object{
        init {
            EqsSlotListener::class.java.getName()
        }

        const val CONTAINER_TYPE: String = "type"
        const val SLOT_NUM: String = "num"
        const val LISTENER_NAME: String = "listener_name"
        val SLOT_LISTENERS = mutableMapOf<String, Codec<out SlotListener>>()
        val INVENTORY_HEAD: EqsSlotListener = EqsSlotListener(ContainerType.TYPE_INVENTORY, 39)
        val INVENTORY_CHEST: EqsSlotListener = EqsSlotListener(ContainerType.TYPE_INVENTORY, 38)
        val INVENTORY_LEG: EqsSlotListener = EqsSlotListener(ContainerType.TYPE_INVENTORY, 37)
        val INVENTORY_FEET: EqsSlotListener = EqsSlotListener(ContainerType.TYPE_INVENTORY, 36)
        val INVENTORY_OFF_HAND: EqsSlotListener = EqsSlotListener(ContainerType.TYPE_INVENTORY, 40)
        val EQS_SLOT_LISTENER = Register(EqsSlotListener.NAME, EqsSlotListener.CODEC)

        fun  <T: SlotListener> Register(key: String, codec: Codec<T>){
            SLOT_LISTENERS[key] = codec
        }
//
//        inline fun <reified T: SlotListener> FromTag(tag: CompoundTag) : T {
//            val listenerName = tag.getString(LISTENER_NAME).get()
//            val builder = SLOT_LISTENERS[listenerName]  as? BUILDER<T>
//                ?: return EqsSlotListener.Default() as T  // 默认值必须强制转换为 T
//            return builder(tag)
//        }


    }

    var name: String
    var containerType: ContainerType
    var slotNum: Int

    fun getItem(serverPlayer: ServerPlayer): ItemStack {
        return containerType.getItem(serverPlayer, slotNum)
    }

    fun onChange(serverPlayer: ServerPlayer, itemStack: ItemStack) {
        containerType.setItem(serverPlayer, slotNum, itemStack)
    }

    fun toTag(): CompoundTag {
        val tag = CompoundTag()
        tag.putString(LISTENER_NAME, name )
        tag.putString(CONTAINER_TYPE, containerType.name)
        tag.putInt(SLOT_NUM, slotNum)
        return tag
    }

    fun clear() {
        this.slotNum = -1
        this.containerType = ContainerType.TYPE_INVENTORY
    }

    fun update(containerType: ContainerType, slotNum: Int) {
        this.containerType = containerType
        this.slotNum = slotNum
    }

    fun update(slotListener: SlotListener) {
        this.containerType = slotListener.containerType
        this.slotNum = slotListener.slotNum
    }

    fun isEmpty() : Boolean = this.containerType == ContainerType.TYPE_INVENTORY && this.slotNum == -1

//    override fun equals(other: Any?): Boolean {
//        return other is SlotListener && other.containerType == this.containerType && other.slotNum == this.slotNum
//    }
//
//    override fun hashCode(): Int {
//        var result = slotNum
//        result = 31 * result + containerType.hashCode()
//        return result
//    }
}