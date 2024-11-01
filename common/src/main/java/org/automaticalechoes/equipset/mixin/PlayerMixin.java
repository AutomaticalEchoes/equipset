package org.automaticalechoes.equipset.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.automaticalechoes.equipset.Constants;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.api.PresetEquipSet;
import org.automaticalechoes.equipset.api.PresetManager;
import org.automaticalechoes.equipset.api.Utils;
import org.automaticalechoes.equipset.client.screen.EquipmentSettingsScreen;
import org.automaticalechoes.equipset.config.ModGameRule;
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
    private final PresetManager equipSet$equipmentSets = PresetManager.defaultManager((Player)(Object)this);
    @Unique
    private int equipSet$focus;
    @Unique
    private static final EntityDataAccessor<CompoundTag> equipSet$SETS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.COMPOUND_TAG );
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder, CallbackInfo ci) {
        pBuilder.define(equipSet$SETS, PresetManager.defaultManager((Player)(Object)this).toTag());
    }

    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("EquipmentSettings")){
            CompoundTag suitTag = compoundTag.getCompound("EquipmentSettings");
            equipSet$equipmentSets.fromTag(suitTag);
            if(getServer() != null) equipSet$equipmentSets.resize(ModGameRule.getNums((ServerPlayer)(Object)this));
        }
        equipSet$onSetUpdate();
        equipSet$focus = compoundTag.getInt("Focus");
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipmentSettings", equipSet$equipmentSets.toTag());
    }

    public PresetManager equipSet$getEquipmentSets() {
        return this.equipSet$equipmentSets;
    }

    public void equipSet$useSet(int num, boolean lockCheck) {
        if(level().isClientSide) return;
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        Component feedBack = Utils.NoneSet;
        try {
            if(equipSet$equipmentSets.isEmpty()) throw new IndexOutOfBoundsException("none sets loaded");
            int canUse = num;
            if(lockCheck  && equipSet$equipmentSets.get(num).isLock()){
                int[] ints = equipSet$equipmentSets.unLockedSets();
                if(ints.length == 0) throw new IndexOutOfBoundsException("none unlock set");
                OptionalInt first = Arrays.stream(ints).filter(foInt -> foInt > equipSet$focus).findFirst();
                canUse = first.isPresent()? first.getAsInt() : ints[0];
            }
            feedBack = equipSet$equipmentSets.UseSet(serverPlayer, this.equipSet$focus, canUse);
            this.equipSet$focus = canUse;
        }catch (Exception e){ }
        Component finalFeedBack = feedBack;
        Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, finalFeedBack));
    }

    public void equipSet$nextSet(){
        equipSet$useSet((equipSet$focus + 1) % equipSet$equipmentSets.size(), true);
    }

    @Override
    public void equipSet$resize(int nums) {
        equipSet$equipmentSets.resize(nums);
        equipSet$onSetUpdate();
    }

    @Override
    public void equipSet$updatePartStatus(int num, String partName, boolean enable) {
        if(level().isClientSide) return;
        try {
            if(this.equipSet$equipmentSets.get(num).setPartStatus(partName, enable)) equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, Utils.NoneSet));
        }
    }

    public void equipSet$updateSetName(int num, String s){
        if(level().isClientSide) return;
        try{
            this.equipSet$equipmentSets.setName(num,s);
            equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, Utils.NoneSet));
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
                    PresetEquipSet presetEquipSet = this.equipSet$equipmentSets.get(num);
                    if(presetEquipSet.isLock()) yield false;
                    presetEquipSet.lock();
                    yield true;
                }
                case 3 -> {
                    PresetEquipSet presetEquipSet = this.equipSet$equipmentSets.get(num);
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
            Constants.NETWORK.ifPresent(equipSetNetWork -> equipSetNetWork.SendFeedBack(serverPlayer, Utils.NoneSet));
        }


    }

    public void equipSet$restoreFrom(ServerPlayer serverPlayer){
        ((IPlayerInterface)this).equipSet$getEquipmentSets().copyFrom(((IPlayerInterface) serverPlayer).equipSet$getEquipmentSets());
        equipSet$onSetUpdate();
    }

    @Unique
    private void equipSet$onSetUpdate(){
        this.entityData.set(equipSet$SETS, equipSet$equipmentSets.toTag(), true);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
        super.onSyncedDataUpdated(p_21104_);
        if(!level().isClientSide ) return;
        if(p_21104_.equals(equipSet$SETS)) {
            this.equipSet$equipmentSets.fromTag(entityData.get(equipSet$SETS));
            if(Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen)screen.reInit();
        }

    }
}
