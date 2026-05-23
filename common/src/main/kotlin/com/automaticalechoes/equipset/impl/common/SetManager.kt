package com.automaticalechoes.equipset.impl.common

import com.automaticalechoes.equipset.api.IUtils
import com.mojang.serialization.Codec
import io.netty.buffer.ByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.server.level.ServerPlayer


class SetManager() : LinkedHashMap<Int, EquipSet>() {
    var focus: Int = 0;

    companion object{
        @JvmStatic
        fun defaultManager(): SetManager {
            val presetManager = SetManager()
            for (i in 0..3) {
                presetManager.neoSet()
            }
            return presetManager
        }

        @JvmStatic
        val CODEC : Codec<SetManager> by lazy {
            Codec.unboundedMap(Codec.INT, EquipSet.CODEC)
                .xmap({ SetManager(it) }, { it })
            // 确保类型为 LinkedHashMap
        }


        @JvmStatic
        val STREAM_CODEC : StreamCodec<ByteBuf, SetManager> by lazy {
            ByteBufCodecs.fromCodec(CODEC)
        }
    }


    constructor(m: MutableMap<Int, EquipSet>) : this (){
        this.clear()
        putAll(m)
    }



//    fun toTag(): CompoundTag {
//        val compoundtag = CompoundTag()
//        var i = 0
//        for (entry in this.entries) {
//            val set = entry.value.toTag()
//            set.putInt(ID, entry.key)
//            compoundtag.put(i.toString(), set)
//            i++
//        }
//        compoundtag.putInt(SIZE, size)
//        return compoundtag
//    }
//
//
//    //    public static PresetManager FromTag(ServerPlayer owner, CompoundTag compoundTag) {
//    //        PresetManager presetManager = new PresetManager(owner);
//    //        presetManager.fromTag(compoundTag);
//    //        return presetManager;
//    //    }
//    fun fromTag(compoundTag: CompoundTag) {
//        this.clear()
//        val nums: Int = compoundTag.getInt(SIZE)
//        readTag(compoundTag, this, nums)
//    }

//    private fun readTag(compoundTag: CompoundTag, presetManager: SetManager, nums: Int) {
//        for (i in 0..<nums) {
//            val value = i.toString()
//            if (compoundTag.contains(value)) {
//                val compound: CompoundTag = compoundTag.getCompound(value)
//                val set = PresetEquipSet(presetManager.owner, compound)
//                val id: Int = compound.getInt(ID)
//                presetManager.put(id, set)
//            } else {
//                presetManager.neoSet()
//            }
//        }
//    }

    fun setName(num: Int, name: String) {
        this[num]?.setName(name)
    }

    fun unLockedSets(): IntArray {
        return entries.stream()
            .filter { entry -> !entry.value.isLock() }
            .mapToInt { entry -> entry.key }.toArray()
    }

    fun resize(nums: Int) {
        if (size == nums) return
        while (size > nums) popSet()
        while (size < nums) neoSet()
    }


    fun UseSet(serverPlayer: ServerPlayer, old: Int, neo: Int): Component {
        val feedBack = IUtils.Total.copy()
        this[neo]?.let {
            feedBack.append(it.name)
            if (old == neo || !containsKey(old)) {
                same(serverPlayer, neo, feedBack)
            } else {
                diff(serverPlayer, old, neo, feedBack)
            }
         }

        return feedBack
    }




    private fun diff(serverPlayer: ServerPlayer, old: Int, neo: Int, feedBack: MutableComponent) {
        val oldSet: EquipSet = this[old]!!
        val neoSet: EquipSet = this[neo]!!
        for (name in EquipSet.DEFAULT_PARTS.keys) {
            val old: EquipPart = oldSet[name]!!
            val neo: EquipPart = neoSet[name]!!
            if (!neo.isEnable() || neo.isWearing(serverPlayer) || neo.isCurse(serverPlayer, feedBack) || !neo.canFindPresetItem(serverPlayer, feedBack))
                continue
            if (old.isWearing(serverPlayer)) {
                if (old.canHoldItem(serverPlayer)){
                    old.change(serverPlayer)
                } else{
                    old.getLogSlot().update(neo.getLogSlot())
                }
            }
            if (old.getLogSlot() != neo.getLogSlot()){
                neo.change(serverPlayer)
            }
        }
    }

    private fun same(serverPlayer: ServerPlayer, neo: Int, feedBack: MutableComponent) {
        val neoSet: EquipSet = this[neo]!!
        for (name in EquipSet.DEFAULT_PARTS.keys) {
            val neoPart: EquipPart = neoSet[name]!!
            if (!neoPart.isEnable() || neoPart.isWearing(serverPlayer) || neoPart.isCurse(serverPlayer, feedBack) || !neoPart.canFindPresetItem(serverPlayer, feedBack))
                continue
            neoSet[name]!!.change(serverPlayer)
        }
    }

    fun neoSet() {
        put(neoId(), EquipSet("Preset " + (size + 1)))
    }

    fun popSet() {
        remove(size - 1)
    }

    fun neoId(): Int {
        var i = 0
        while (containsKey(i)) {
            i++
        }
        return i
    }

    fun copyFrom(presetManager: SetManager) {
        this.clear()
        this.putAll(presetManager)
    }

}