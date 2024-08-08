package automaticalechoes.equipset.equipset.mixin;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import automaticalechoes.equipset.equipset.api.PresetEquipSet;
import automaticalechoes.equipset.equipset.api.PresetManager;
import automaticalechoes.equipset.equipset.api.Utils;
import automaticalechoes.equipset.equipset.client.screen.EquipmentSettingsScreen;
import automaticalechoes.equipset.equipset.common.network.FeedBack;
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
    private final PresetManager equipSet$equipmentSets = PresetManager.defaultManager();
    @Unique
    private int equipSet$focus;
    @Unique
    private static final EntityDataAccessor<PresetManager> equipSet$SETS = SynchedEntityData.defineId(Player.class , EquipSet.SETS_SERIALIZER);
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(equipSet$SETS, PresetManager.defaultManager());
    }

    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("EquipmentSettings")){
            CompoundTag suitTag = compoundTag.getCompound("EquipmentSettings");
            equipSet$equipmentSets.fromTag(suitTag);
        }
        equipSet$onSetUpdate();
        equipSet$focus = compoundTag.getInt("Focus");
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipmentSettings", equipSet$equipmentSets.toTag());
    }

    public PresetManager getEquipmentSets() {
        return this.level().isClientSide() ? this.entityData.get(equipSet$SETS) : this.equipSet$equipmentSets;
    }

    public void useSet(int num, boolean lockCheck) {
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
        CommonModEvents.NetWork.sendTo(new FeedBack(feedBack), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public void equipSet$nextSet(){
        useSet((equipSet$focus + 1) % equipSet$equipmentSets.size(), true);
    }

    @Override
    public void equipSet$updatePartStatus(int num, String partName, boolean enable) {
        if(level().isClientSide) return;
        try {
            if(this.equipSet$equipmentSets.get(num).setPartStatus(partName, enable)) equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            serverPlayer.connection.
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }

    }

    public void equipSet$updateSetName(int num, String s){
        if(level().isClientSide) return;
        try{
            this.equipSet$equipmentSets.setName(num,s);
            equipSet$onSetUpdate();
        }catch (NullPointerException e){
            ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
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
            CommonModEvents.NetWork.sendTo(new FeedBack(Utils.NoneSet), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }


    }

    public void equipSet$restoreFrom(ServerPlayer serverPlayer){
        ((IPlayerInterface)this).getEquipmentSets().copyFrom(((IPlayerInterface) serverPlayer).getEquipmentSets());
        equipSet$onSetUpdate();
    }

    @Unique
    private void equipSet$onSetUpdate(){
        this.entityData.set(equipSet$SETS, equipSet$equipmentSets, true);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
        super.onSyncedDataUpdated(p_21104_);
        if(level().isClientSide && Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen){
            screen.reInit();
        }
    }
}
