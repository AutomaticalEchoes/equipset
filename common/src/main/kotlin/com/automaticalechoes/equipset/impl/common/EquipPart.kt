package com.automaticalechoes.equipset.impl.common

import com.automaticalechoes.equipset.api.SlotListener
import com.automaticalechoes.equipset.api.IUtils
import com.automaticalechoes.equipset.config.ModGameRules
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents
import net.minecraft.world.item.enchantment.EnchantmentHelper
import org.apache.commons.lang3.tuple.Pair
import java.util.*
import java.util.function.Consumer

class EquipPart (private val equipmentSlot: SlotListener,
                 private var settingNeed: ItemStack,
                 private val logSlot: EqsSlotListener,
                 private var enable: Boolean) {
    companion object{
        const val EQUIP: String = "equip"
        const val ITEM: String = "item"
        const val LOG: String = "log"
        const val ENABLE: String = "enable"


        fun defaultSetting(equipSlot: SlotListener): EquipPart {
            return EquipPart(equipSlot, ItemStack.EMPTY, EqsSlotListener.Companion.DefaultLog(), true)
        }

        val CODEC : Codec<EquipPart> by lazy {
            RecordCodecBuilder.create { instance ->
                instance.group(
                    EqsSlotListener.CODEC.fieldOf(EQUIP).forGetter { it?.equipmentSlot as EqsSlotListener? },
                    ItemStack.CODEC.fieldOf(ITEM).forGetter { it?.settingNeed },
                    EqsSlotListener.CODEC.fieldOf(LOG).forGetter { it?.logSlot },
                    Codec.BOOL.fieldOf(ENABLE).forGetter { it?.enable}
                ).apply(instance, ::EquipPart)
            }
        }
    }

    fun update(serverPlayer: ServerPlayer) {
        this.settingNeed = equipmentSlot.getItem(serverPlayer).copy()
        this.logSlot.clear()
    }

    fun clear() {
        this.settingNeed = ItemStack.EMPTY
        this.logSlot.clear()
    }

    fun isWearing(serverPlayer: ServerPlayer): Boolean {
        return IUtils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed)
    }

    fun isCurse(serverPlayer: ServerPlayer): Boolean {
        return ModGameRules.shouldCurseCheck(serverPlayer) && EnchantmentHelper.has(
            equipmentSlot.getItem(serverPlayer),
            EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP
        )
    }

    fun isCurse(serverPlayer: ServerPlayer, component: MutableComponent): Boolean {
        val curse = this.isCurse(serverPlayer)
        if (curse && !component.contains(IUtils.CursePart)) component.append(IUtils.CursePart)
        return curse
    }

    fun canFindPresetItem(serverPlayer: ServerPlayer): Boolean {
        updateLocationIfNeed(serverPlayer, settingNeed, logSlot)
        return !logSlot.isEmpty() && logSlot.containerType.canUse(serverPlayer)
    }

    fun canFindPresetItem(serverPlayer: ServerPlayer, component: MutableComponent): Boolean {
        val hasItem = this.canFindPresetItem(serverPlayer)
        if (!hasItem && !component.contains(IUtils.LackPart)) component.append(IUtils.LackPart)
        return hasItem
    }

    fun canHoldItem(serverPlayer: ServerPlayer): Boolean {
        if (!IUtils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed)) return false
        updateLocationIfNeed(serverPlayer, ItemStack.EMPTY, logSlot)
        return !logSlot.isEmpty()
    }

    fun updateLocationIfNeed(
        serverPlayer: ServerPlayer,
        itemStack: ItemStack,
        logSlot: EqsSlotListener
    ) {
        if (logSlot.isEmpty() || !logSlot.containerType.canUse(serverPlayer) || !IUtils.CheckItemSame(
                logSlot.getItem(serverPlayer),
                itemStack
            )
        ) {
            findItemLocation(serverPlayer, itemStack)
                .ifPresentOrElse(Consumer { pair: Pair<ContainerType, Int> ->
                    logSlot.update(
                        pair.key,
                        pair.value
                    )
                }, Runnable { logSlot.clear() })
        }
    }

    fun findItemLocation(
        serverPlayer: ServerPlayer,
        settingNeed: ItemStack
    ): Optional<Pair<ContainerType, Int>> {
        for (value in ContainerType.TYPES.values) {
            if (!value.canUse(serverPlayer)) continue
            for (i in 0..<value.size(serverPlayer)) {
                if (IUtils.CheckItemSame(
                        value.getItem(serverPlayer, i),
                        settingNeed
                    )
                ) return Optional.of<Pair<ContainerType, Int>>(Pair.of<ContainerType, Int>(value, i))
            }
        }
        return Optional.empty<Pair<ContainerType, Int>>()
    }

    fun change(serverPlayer: ServerPlayer) {
        change(serverPlayer, equipmentSlot, logSlot)
    }

    fun change(serverPlayer: ServerPlayer, left: SlotListener, right: SlotListener) {
        val leftItem = left.getItem(serverPlayer)
        val rightItem = right.getItem(serverPlayer)
        left.onChange(serverPlayer, rightItem)
        right.onChange(serverPlayer, leftItem)
    }


//    fun toTag(player: Player): CompoundTag {
//        val tag = CompoundTag()
//        tag.put(EQUIP, equipmentSlot!!.toTag())
//        if (!settingNeed.isEmpty()) tag.put(ITEM, settingNeed.save(player.registryAccess()))
//        tag.put(LOG, logSlot!!.toTag())
//        tag.putBoolean(ENABLE, enable)
//        return tag
//    }

    fun setEnable(enable: Boolean): EquipPart {
        this.enable = enable
        return this
    }

    fun getSettingNeed(): ItemStack {
        return settingNeed
    }

    fun isEnable(): Boolean {
        return enable
    }

    fun getLogSlot(): EqsSlotListener {
        return logSlot
    }
}