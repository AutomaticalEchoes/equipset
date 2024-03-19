package com.AutomaticalEchoes.equipset.api;

import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

    public void update(EntityPlayerMP serverPlayer){
        this.settingNeed = equipmentSlot.getItem(serverPlayer).copy();
        this.locationRecord.clear();
    }

    public void clear(){
        this.settingNeed = ItemStack.EMPTY;
        this.locationRecord.clear();
    }

    public boolean isWearing(EntityPlayerMP serverPlayer){
        return Utils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed);
    }

    public boolean isCurse(EntityPlayerMP serverPlayer){
        return EquipSetConfig.CURSE_CHECK && EnchantmentHelper.hasBindingCurse( equipmentSlot.getItem(serverPlayer));
    }

    public boolean isCurse(EntityPlayerMP serverPlayer, Utils.ICode component){
        boolean curse = this.isCurse(serverPlayer);
        if(curse) component.setCode(component.getCode() | 4);
        return curse;
    }

    public boolean canFindPresetItem(EntityPlayerMP serverPlayer){
        updateLocationIfNeed(serverPlayer, settingNeed, locationRecord);
        return !locationRecord.isEmpty();
    }

    public boolean canFindPresetItem(EntityPlayerMP serverPlayer, Utils.ICode component){
       boolean hasItem = this.canFindPresetItem(serverPlayer);
       if(!hasItem) component.setCode(component.getCode() | 2);
       return hasItem;
    }

    public boolean canHoldItem(EntityPlayerMP serverPlayer){
        if(!Utils.CheckItemSame(equipmentSlot.getItem(serverPlayer), settingNeed)) return false;
        updateLocationIfNeed(serverPlayer,ItemStack.EMPTY, locationRecord);
        return !locationRecord.isEmpty();
    }

    private void updateLocationIfNeed(EntityPlayerMP serverPlayer, ItemStack itemStack, LocationLog locationRecord){
        if(locationRecord.isEmpty() || !Utils.CheckItemSame(locationRecord.getItem(serverPlayer), itemStack)){
            Optional<Pair<ContainerType, Integer>> itemLocation = findItemLocation(serverPlayer, itemStack);
            itemLocation.ifPresent(pair -> locationRecord.update(pair.getKey(), pair.getValue()));
            if(!itemLocation.isPresent()) locationRecord.clear();
        }
    }

    private Optional<Pair<ContainerType,Integer>> findItemLocation(EntityPlayerMP serverPlayer , ItemStack settingNeed){
        for (ContainerType value : ContainerType.TYPES.values()) {
            IInventory container = value.getContainer(serverPlayer);
            for (int i = 0; i < container.getSizeInventory(); i++) {
                if (Utils.CheckItemSame(container.getStackInSlot(i), settingNeed))
                    return Optional.of(Pair.of(value, i));
            }
        }
        return Optional.empty();
    }

    public void change(EntityPlayerMP serverPlayer){
        change(serverPlayer, equipmentSlot, locationRecord);
    }

    private void change(EntityPlayerMP serverPlayer, SlotGetter left, SlotGetter right){
        ItemStack leftItem = left.getItem(serverPlayer);
        ItemStack rightItem = right.getItem(serverPlayer);
        left.onChange(serverPlayer, rightItem);
        right.onChange(serverPlayer, leftItem);
    }

    public static PresetEquipPart defaultSetting(SlotGetter equipSlot){
        return new PresetEquipPart(equipSlot, ItemStack.EMPTY, LocationLog.Default());
    }

    public static PresetEquipPart fromTag(NBTTagCompound tag){
        SlotGetter slotGetter = SlotGetter.fromTag(tag.getCompoundTag(EQUIP));
        ItemStack itemStack = new ItemStack(tag.getCompoundTag(ITEM));
        LocationLog locationLog = LocationLog.fromTag(tag.getCompoundTag(LOG));
        return new PresetEquipPart(slotGetter, itemStack, locationLog).setEnable(tag.getBoolean(ENABLE));
    }

    public NBTTagCompound toTag(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag(EQUIP, equipmentSlot.toTag());
        tag.setTag(ITEM, settingNeed.serializeNBT());
        tag.setTag(LOG, locationRecord.toTag());
        tag.setBoolean(ENABLE, enable);
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
