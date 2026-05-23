package com.automaticalechoes.equipset.impl.common

import com.automaticalechoes.equipset.api.SlotListener
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import java.util.function.Consumer
import kotlin.String

class EquipSet(var name : String, var lock : Boolean): LinkedHashMap<String, EquipPart>() {

    companion object{
        @JvmField
        val DEFAULT_PARTS = LinkedHashMap<String, SlotListener>()
        const val PARTS: String = "parts"
        const val NAME: String = "name"
        const val LOCK: String = "lock"
        var Default_Parts_Setting : Consumer<LinkedHashMap<String, SlotListener>> = { parts : LinkedHashMap<String, SlotListener> ->
            parts.clear()
            parts["head"] = SlotListener.INVENTORY_HEAD
            parts["chest"] = SlotListener.INVENTORY_CHEST
            parts["leg"] = SlotListener.INVENTORY_LEG
            parts["feet"] = SlotListener.INVENTORY_FEET
            parts["offhand"] = SlotListener.INVENTORY_OFF_HAND
        }
        var Default_Setting : Consumer<EquipSet> = { set: EquipSet->
            set.clear()
            for (entry in DEFAULT_PARTS.entries) {
                set[entry.key]= EquipPart.defaultSetting(entry.value)
            }
        }

        val PARTS_CODEC : Codec<EquipSet> by lazy {
            Codec.unboundedMap(Codec.STRING, EquipPart.CODEC)
                .xmap({ EquipSet(it) }, { it })  // 确保类型为 LinkedHashMap
        }
//
//        val PARTS_CODEC : Codec<LinkedHashMap<String, EquipPart>> by lazy {
//            Codec.unboundedMap(Codec.STRING, EquipPart.CODEC)
//                .xmap({ LinkedHashMap(it) }, { it })  // 确保类型为 LinkedHashMap
//        }

        val Builder : (String, Boolean, EquipSet) -> EquipSet= { name , lock , set  ->
            if(lock) set.lock().setName(name)
            else set.unLock().setName(name)
        }



        val CODEC : Codec<EquipSet> by lazy {
            RecordCodecBuilder.create { instance ->
                instance.group(
                    Codec.STRING.fieldOf(NAME).forGetter { it.name },
                    Codec.BOOL.fieldOf(LOCK).forGetter { it.lock },
                    PARTS_CODEC.fieldOf(PARTS).forGetter { it }
                ).apply(instance,Builder)
            }
        }

    }


    constructor(m: MutableMap<String, EquipPart>) : this ("empty", false){
        this.clear()
        putAll(m)
    }

    constructor(name: String) : this(name, false) {
        Default()
    }

//    constructor(name: String, lock: Boolean) : this(name,lock){
//
//    }

    fun Default() : EquipSet {
        Default_Parts_Setting.accept(DEFAULT_PARTS)
        Default_Setting.accept(this)
        return this
    }


    fun setName(name: String) : EquipSet{
        this.name = name
        return this
    }

    fun setPartStatus(part: String, enable: Boolean): Boolean {
        get(part)?.let { if(it.isEnable() != enable) {
            it.setEnable(enable)
            return true
        }
        }
        return false
    }


    fun clearSetting() {
        values.forEach(action = {
            it.clear()
        })
    }

    fun isLock(): Boolean {
        return this.lock
    }

    fun lock() : EquipSet{
        this.lock = true
        return this
    }

    fun unLock() : EquipSet{
        this.lock = false
        return this
    }
}