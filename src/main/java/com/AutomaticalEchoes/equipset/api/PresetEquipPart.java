package com.AutomaticalEchoes.equipset.api;

import com.AutomaticalEchoes.equipset.config.ConfigValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public class PresetEquipPart {
    static final String EQUIP = "equip";
    static final String ITEM = "item";
    static final String LOG = "log";
    static final String ENABLE = "enable";


    private final SlotGetter equipmentSlot;
    private ItemStack settingNeed;
    private final LocationLog locationRecord;
    private boolean enable = true;

    public PresetEquipPart(SlotGetter equipmentSlot, ItemStack settingNeed, LocationLog locationRecord){
        this.equipmentSlot = equipmentSlot;
        this.settingNeed = settingNeed;
        this.locationRecord = locationRecord;
    }

    public void update(ServerPlayer serverPlayer){
        this.settingNeed = equipmentSlot.getItem(serverPlayer).copy();
        this.locationRecord.clear();
    }

    public void clear(){
        this.settingNeed = ItemStack.EMPTY;
        this.locationRecord.clear();
    }

    public boolean isWearing(ServerPlayer serverPlayer){
        return Utils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed);
    }

    public boolean isCurse(ServerPlayer serverPlayer){
        return ConfigValue.CURSE_CHECK && EnchantmentHelper.hasBindingCurse( equipmentSlot.getItem(serverPlayer));
    }

    public boolean isCurse(ServerPlayer serverPlayer, MutableComponent component){
        boolean curse = this.isCurse(serverPlayer);
        if(curse && !component.getSiblings().contains(Utils.CursePart)) component.append(Utils.CursePart);
        return curse;
    }

    public boolean canFindPresetItem(ServerPlayer serverPlayer){
        updateLocationIfNeed(serverPlayer, settingNeed, locationRecord);
        return !locationRecord.isEmpty();
    }

    public boolean canFindPresetItem(ServerPlayer serverPlayer, MutableComponent component){
       boolean hasItem = this.canFindPresetItem(serverPlayer);
       if(!hasItem && !component.getSiblings().contains(Utils.LackPart)) component.append(Utils.LackPart);
       return hasItem;
    }

    public boolean canHoldItem(ServerPlayer serverPlayer){
        if(!Utils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed)) return false;
        updateLocationIfNeed(serverPlayer,ItemStack.EMPTY, locationRecord);
        return !locationRecord.isEmpty();
    }

    private void updateLocationIfNeed(ServerPlayer serverPlayer, ItemStack itemStack, LocationLog locationRecord){
        if(locationRecord.isEmpty() || !Utils.CheckItemSame(locationRecord.getItem(serverPlayer), itemStack)){
            findItemLocation(serverPlayer, itemStack)
                    .ifPresentOrElse(pair -> locationRecord.update(pair.getKey(), pair.getValue()), locationRecord::clear);
        }
    }

    private Optional<Pair<ContainerType,Integer>> findItemLocation(ServerPlayer serverPlayer , ItemStack settingNeed){
        for (ContainerType value : ContainerType.TYPES.values()) {
            Container container = value.getContainer(serverPlayer);
            for (int i = 0; i < container.getContainerSize(); i++) {
                if (Utils.CheckItemSame(container.getItem(i), settingNeed))
                    return Optional.of(Pair.of(value, i));
            }
        }
        return Optional.empty();
    }

    public void change(ServerPlayer serverPlayer){
        change(serverPlayer, equipmentSlot, locationRecord);
    }

    private void change(ServerPlayer serverPlayer, SlotGetter left, SlotGetter right){
        ItemStack leftItem = left.getItem(serverPlayer);
        ItemStack rightItem = right.getItem(serverPlayer);
        left.onChange(serverPlayer, rightItem);
        right.onChange(serverPlayer, leftItem);
    }

    public static PresetEquipPart defaultSetting(SlotGetter equipSlot){
        return new PresetEquipPart(equipSlot, ItemStack.EMPTY, LocationLog.Default());
    }

    public static PresetEquipPart fromTag(CompoundTag tag){
        SlotGetter slotGetter = SlotGetter.fromTag(tag.getCompound(EQUIP));
        ItemStack itemStack = ItemStack.of(tag.getCompound(ITEM));
        LocationLog locationLog = LocationLog.fromTag(tag.getCompound(LOG));
        return new PresetEquipPart(slotGetter, itemStack, locationLog).setEnable(tag.getBoolean(ENABLE));
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.put(EQUIP, equipmentSlot.toTag());
        tag.put(ITEM, settingNeed.save(new CompoundTag()));
        tag.put(LOG, locationRecord.toTag());
        tag.putBoolean(ENABLE, enable);
        return tag;
    }

    public PresetEquipPart setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public ItemStack getSettingNeed() {
        return settingNeed;
    }

    public SlotGetter getEquipSlot() {
        return equipmentSlot;
    }

    public LocationLog getLocationRecord() {
        return locationRecord;
    }
}
