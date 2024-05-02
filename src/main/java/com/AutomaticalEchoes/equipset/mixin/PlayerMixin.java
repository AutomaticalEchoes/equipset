package com.AutomaticalEchoes.equipset.mixin;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.PresetManager;
import com.AutomaticalEchoes.equipset.api.Utils;
import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.FeedBack;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.OptionalInt;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final PresetManager equipmentSets = PresetManager.defaultManager();
    private int focus = 0;
    private static final EntityDataAccessor<PresetManager> SETS = SynchedEntityData.defineId(Player.class , EquipSet.SETS_SERIALIZER);
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(SETS, PresetManager.defaultManager());
    }

    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("EquipmentSettings")){
            CompoundTag suitTag = compoundTag.getCompound("EquipmentSettings");
            equipmentSets.fromTag(suitTag);
        }
        onSetUpdate();
        focus = compoundTag.getInt("Focus");
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipmentSettings", equipmentSets.toTag());
    }

    public PresetManager getEquipmentSets() {
        return this.level.isClientSide() ? this.entityData.get(SETS) : this.equipmentSets;
    }

    public void useSet(int num, boolean lockCheck) {
        if(level.isClientSide) return;
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        Component feedBack = Utils.NoneSet;
        try {
            if(equipmentSets.size() == 0) throw new IndexOutOfBoundsException("none sets loaded");
            int canUse = num;
            if(lockCheck && equipmentSets.get(num).isLock()){
                int[] ints = equipmentSets.unLockedSets();
                if(ints.length == 0) throw new IndexOutOfBoundsException("none unlock set");
                OptionalInt first = Arrays.stream(ints).filter(foInt -> foInt > focus).findFirst();
                canUse = first.isPresent()? first.getAsInt() : ints[0];
            }
            feedBack = equipmentSets.UseSet(serverPlayer, this.focus, canUse);
            this.focus = canUse;
        }catch (Exception e){ }
        CommonModEvents.NetWork.sendTo(new FeedBack(feedBack), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public void nextSet(){
        useSet((focus + 1) % equipmentSets.size(), true);
    }

    @Override
    public void updatePartStatus(int num, String partName, boolean enable) {
        if(level.isClientSide) return;
        try {
            if(this.equipmentSets.get(num).setPartStatus(partName, enable)) onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }

    }

    public void updateSetName(int num, String s){
        if(level.isClientSide) return;
        try{
            this.equipmentSets.setName(num,s);
            onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }

    }

    public void updateSet(int num, int cases){
        // clear, save, lock, unLock, neo, delete
        try{
            boolean shouldUpdate = switch (cases){
                case 0 -> {
                    this.equipmentSets.get(num).clearSetting();
                    yield true;
                }
                case 1 -> {
                    this.equipmentSets.get(num).values().forEach(part -> part.update((ServerPlayer)(Object)this));
                    yield true;
                }
                case 2 -> {
                    PresetEquipSet presetEquipSet = this.equipmentSets.get(num);
                    if(presetEquipSet.isLock()) yield false;
                    presetEquipSet.lock();
                    yield true;
                }
                case 3 -> {
                    PresetEquipSet presetEquipSet = this.equipmentSets.get(num);
                    if(!presetEquipSet.isLock()) yield false;
                    presetEquipSet.unLock();
                    yield true;
                }
                case 4 -> {
                    this.equipmentSets.neoSet();
                    yield true;
                }
                case 5 -> {
                    this.equipmentSets.remove(num);
                    yield true;
                }
                default -> false;
            };
            if(shouldUpdate) onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    private void onSetUpdate(){
        this.entityData.set(SETS, equipmentSets);
    }

    public void restoreFrom(ServerPlayer serverPlayer){
        ((IPlayerInterface)this).getEquipmentSets().copyFrom(((IPlayerInterface) serverPlayer).getEquipmentSets());
        onSetUpdate();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
        super.onSyncedDataUpdated(p_21104_);
        if(level.isClientSide && Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen){
            screen.reInit();
        }
    }
}
