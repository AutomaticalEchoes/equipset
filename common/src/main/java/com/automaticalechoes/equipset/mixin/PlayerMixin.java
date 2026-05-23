package com.automaticalechoes.equipset.mixin;


import com.automaticalechoes.equipset.Constants;
import com.automaticalechoes.equipset.api.IUtils;
import com.automaticalechoes.equipset.client.screen.EquipmentSettingsScreen;
import com.automaticalechoes.equipset.common.IPlayerInterface;
import com.automaticalechoes.equipset.impl.common.EquipSet;
import com.automaticalechoes.equipset.impl.common.SetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.OptionalInt;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    @Shadow public abstract void animateHurt(float p_265280_);
    @Unique
    private final SetManager equipSet$equipmentSets = SetManager.defaultManager();
    @Unique
    private static final EntityDataAccessor<SetManager> equipSet$SETS = SynchedEntityData.defineId(Player.class, EntityDataSerializer.forValueType(SetManager.getSTREAM_CODEC()));
    @Unique
    private static final EntityDataAccessor<Integer> equipSet$FOCUS  = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }
//
    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder, CallbackInfo ci) {
        pBuilder.define(equipSet$SETS, equipSet$equipmentSets);
        pBuilder.define(equipSet$FOCUS, equipSet$equipmentSets.getFocus());
    }

    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(ValueInput input, CallbackInfo callbackInfo){
        input.read("EquipmentSettings", SetManager.getCODEC()).ifPresent(equipSet$equipmentSets::copyFrom);
        input.getInt("Focus").ifPresent(equipSet$equipmentSets::setFocus);
//        if(compoundTag.contains("EquipmentSettings")){
//            CompoundTag suitTag = compoundTag.getCompound("EquipmentSettings");
//            equipSet$equipmentSets.fromTag(suitTag);
//            if(getServer() != null) equipSet$equipmentSets.resize(ModGameRule.getNums((ServerPlayer)(Object)this));
//        }
        equipSet$onSetUpdate();
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(ValueOutput output , CallbackInfo callbackInfo) {
//        p_36265_.put("EquipmentSettings", equipSet$equipmentSets.toTag());
        output.store("EquipmentSettings",SetManager.getCODEC(),equipSet$equipmentSets);
        output.putInt("Focus", equipSet$equipmentSets.getFocus());
    }


    public SetManager equipSet$getEquipmentSets() {
        return this.equipSet$equipmentSets;
    }

    public void equipSet$useSet(int num, boolean lockCheck) {
        if(level().isClientSide()) return;
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        Component feedBack = IUtils.NoneSet;
        try {
            if(equipSet$equipmentSets.isEmpty()) throw new IndexOutOfBoundsException("none sets loaded");
            int canUse = num;
            if(lockCheck  && equipSet$equipmentSets.get(num).isLock()){
                int[] ints = equipSet$equipmentSets.unLockedSets();
                if(ints.length == 0) throw new IndexOutOfBoundsException("none unlock set");
                OptionalInt first = Arrays.stream(ints).filter(foInt -> foInt > equipSet$equipmentSets.getFocus()).findFirst();
                canUse = first.isPresent()? first.getAsInt() : ints[0];
            }
            feedBack = equipSet$equipmentSets.UseSet(serverPlayer, equipSet$equipmentSets.getFocus(), canUse);
            this.equipSet$equipmentSets.setFocus(canUse);
        }catch (Exception e){ }
        Component finalFeedBack = feedBack;
        Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, finalFeedBack));
    }

    public void equipSet$nextSet(){
        equipSet$useSet((equipSet$equipmentSets.getFocus() + 1) % equipSet$equipmentSets.size(), true);
    }

    @Override
    public void equipSet$resize(int nums) {
        equipSet$equipmentSets.resize(nums);
        equipSet$onSetUpdate();
    }

    @Override
    public void equipSet$updatePartStatus(int num, String partName, boolean enable) {
        if(level().isClientSide()) return;
        try {
            if(this.equipSet$equipmentSets.get(num).setPartStatus(partName, enable)) equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, IUtils.NoneSet));
        }
    }

    public void equipSet$updateSetName(int num, String s){
        if(level().isClientSide()) return;
        try{
            this.equipSet$equipmentSets.setName(num,s);
            equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, IUtils.NoneSet));
        }
    }

    public void equipSet$updateSet(int num, int cases){
        // clear, save, lock, unLock, neo, delete
        try{
            boolean shouldUpdate = switch (cases){
                case 0 -> {
                    this.equipSet$equipmentSets.get(num).clearSetting();
                    yield true;
                }
                case 1 -> {
                    this.equipSet$equipmentSets.get(num).values().forEach(part -> part.update((ServerPlayer)(Object)this));
                    yield true;
                }
                case 2 -> {
                    EquipSet presetEquipSet = this.equipSet$equipmentSets.get(num);
                    if(presetEquipSet.isLock()) yield false;
                    presetEquipSet.lock();
                    yield true;
                }
                case 3 -> {
                    EquipSet presetEquipSet = this.equipSet$equipmentSets.get(num);
                    if(!presetEquipSet.isLock()) yield false;
                    presetEquipSet.unLock();
                    yield true;
                }
                case 4 -> {
                    this.equipSet$equipmentSets.neoSet();
                    yield true;
                }
                case 5 -> {
                    this.equipSet$equipmentSets.remove(num);
                    yield true;
                }
                default -> false;
            };
            if(shouldUpdate) equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, IUtils.NoneSet));
        }


    }

    public void equipSet$restoreFrom(ServerPlayer serverPlayer){
        ((IPlayerInterface)this).equipSet$getEquipmentSets().copyFrom(((IPlayerInterface) serverPlayer).equipSet$getEquipmentSets());
        equipSet$onSetUpdate();
    }

    @Unique
    private void equipSet$onSetUpdate(){
        this.entityData.set(equipSet$SETS, equipSet$equipmentSets, true);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if(!level().isClientSide()) return;
        if(accessor.equals(equipSet$SETS)) {
            this.equipSet$equipmentSets.copyFrom(entityData.get(equipSet$SETS));
            if(Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen)screen.reInit();
        }

    }
}
