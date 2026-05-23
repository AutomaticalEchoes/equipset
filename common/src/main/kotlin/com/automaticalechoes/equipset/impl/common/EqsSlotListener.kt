package com.automaticalechoes.equipset.impl.common

import com.automaticalechoes.equipset.api.SlotListener
import com.automaticalechoes.equipset.api.SlotListener.Companion.CONTAINER_TYPE
import com.automaticalechoes.equipset.api.SlotListener.Companion.SLOT_NUM
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder


class EqsSlotListener(override var containerType: ContainerType,
                      override var slotNum: Int
) : SlotListener {
    override var name = NAME

    companion object {
        const val NAME = "eqs_slot_listener"

        val CODEC: Codec<EqsSlotListener> by lazy {
            RecordCodecBuilder.create { instance ->
                instance.group(
                    ContainerType.CODEC.fieldOf(CONTAINER_TYPE).forGetter { it?.containerType },
                    Codec.INT.fieldOf(SLOT_NUM).forGetter { it?.slotNum }
                ).apply(instance, ::EqsSlotListener)
            }
        }

//        @JvmStatic
//        fun FromTag(tag: TagValueInput): EqsSlotListener {
//            val containerType =
//                ContainerType.TYPES.getOrDefault(tag.getString(CONTAINER_TYPE).get(), ContainerType.Companion.TYPE_INVENTORY)
//            val slotNum = tag.getInt(SLOT_NUM).get()
//            return EqsSlotListener(containerType, slotNum,)
//        }

        @JvmStatic
        fun DefaultLog(): EqsSlotListener {
            return EqsSlotListener(ContainerType.TYPE_INVENTORY, -1,)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is SlotListener && other.containerType == this.containerType && other.slotNum == this.slotNum
    }

    override fun hashCode(): Int {
        var result = slotNum
        result = 31 * result + containerType.hashCode()
        return result
    }

}