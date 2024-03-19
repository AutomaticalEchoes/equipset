package com.AutomaticalEchoes.equipset.mixin;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.api.PresetManager;
import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.network.FeedBack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.OptionalInt;


@Mixin(EntityPlayer.class)
public abstract class PlayerMixin extends EntityLivingBase implements IPlayerInterface {
    private final PresetManager equipmentSets = PresetManager.defaultManager();
    private int focus = 0;
    private static final DataParameter<PresetManager> SETS = EntityDataManager.createKey(EntityPlayer.class , EquipSet.SETS_SERIALIZER);

    public PlayerMixin(World worldIn) {
        super(worldIn);
    }


    @Inject(method = {"entityInit"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.dataManager.register(SETS, PresetManager.defaultManager());
    }

    @Inject(method = {"readEntityFromNBT"},at = {@At("RETURN")})
    public void readAdditionalSaveData(NBTTagCompound compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.hasKey("EquipmentSettings")){
            NBTTagCompound suitTag = compoundTag.getCompoundTag("EquipmentSettings");
            equipmentSets.fromTag(suitTag);
        }
        onSetUpdate();
    }

    @Inject(method = {"writeEntityToNBT"},at = {@At("RETURN")})
    public void addAdditionalSaveData(NBTTagCompound p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.setTag("EquipmentSettings", equipmentSets.toTag());
    }

    public PresetManager getEquipmentSets() {
        return this.world.isRemote ? this.dataManager.get(SETS) : this.equipmentSets;
    }

    public void useSet(int num, boolean addWhileLock) {
        if(this.world.isRemote) return;
        EntityPlayerMP serverPlayer = (EntityPlayerMP) (Object) this;
        int feedBack = 4;
        try {
            if(equipmentSets.size() == 0) throw new IndexOutOfBoundsException("none sets loaded");
            int canUse = num;
            if(equipmentSets.get(num).isLock()){
                if(addWhileLock){
                    int[] ints = equipmentSets.unLockedSets();
                    if(ints.length == 0) throw new IndexOutOfBoundsException("none unlock set");
                    OptionalInt first = Arrays.stream(ints).filter(foInt -> foInt > focus).findFirst();
                    canUse = first.isPresent()? first.getAsInt() : ints[0];
                }else{
                    feedBack = 2;
                    throw new IndexOutOfBoundsException("set locked");
                }
            }
            feedBack = equipmentSets.UseSet(serverPlayer, this.focus, canUse);
            this.focus = canUse;
        }catch (Exception e){ }
        CommonModEvents.NetWork.sendTo(new FeedBack(feedBack,this.focus), serverPlayer);
    }

    public void nextSet(){
        useSet((focus + 1) % equipmentSets.size(), true);
    }

    @Override
    public void updatePartStatus(int num, String partName, boolean enable) {
        if(this.world.isRemote) return;
        try {
            if(this.equipmentSets.get(num).setPartStatus(partName, enable)) onSetUpdate();
        }catch (NullPointerException e){
            EntityPlayerMP serverPlayer = (EntityPlayerMP) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(4), serverPlayer);
        }

    }

    public void updateSetName(int num, String s){
        if(this.world.isRemote) return;
        try{
            this.equipmentSets.setName(num,s);
            onSetUpdate();
        }catch (NullPointerException e){
            EntityPlayerMP serverPlayer = (EntityPlayerMP) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(4), serverPlayer);
        }

    }

    public void updateSet(int num, int cases){
        // clear, save, lock, unLock, neo, delete
        try{
            boolean shouldUpdate = true;
            switch (cases){
                case 0 :
                    this.equipmentSets.get(num).clearSetting();
                    shouldUpdate = true;
                    break;
                case 1 :
                    this.equipmentSets.get(num).values().forEach(part -> part.update((EntityPlayerMP)(Object)this));
                    shouldUpdate = true;
                    break;
                case 2 :
                    PresetEquipSet presetEquipSet = this.equipmentSets.get(num);
                    if(presetEquipSet.isLock()) {
                        shouldUpdate = false;
                        break;
                    }
                    presetEquipSet.lock();
                    shouldUpdate = true;
                    break;
                case 3 :
                    PresetEquipSet presetEquipSet1 = this.equipmentSets.get(num);
                    if(!presetEquipSet1.isLock()) {
                        shouldUpdate = false;
                        break;
                    }
                    presetEquipSet1.unLock();
                    shouldUpdate = true;
                    break;
                case 4 :
                    this.equipmentSets.neoSet();
                    shouldUpdate = true;
                    break;
                case 5 :
                    this.equipmentSets.remove(num);
                    shouldUpdate = true;
                    break;
                default:
                    break;

            };
            if(shouldUpdate) onSetUpdate();
        }catch (NullPointerException e){
            EntityPlayerMP serverPlayer = (EntityPlayerMP) (Object) this;
            CommonModEvents.NetWork.sendTo(new FeedBack(4), serverPlayer);
        }
    }

    private void onSetUpdate(){
        this.dataManager.set(SETS, equipmentSets);
    }

    public void restoreFrom(EntityPlayerMP serverPlayer){
        ((IPlayerInterface)this).getEquipmentSets().copyFrom(((IPlayerInterface) serverPlayer).getEquipmentSets());
        onSetUpdate();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if(world.isRemote && Minecraft.getMinecraft().currentScreen instanceof EquipmentSettingsScreen){
            ((EquipmentSettingsScreen)Minecraft.getMinecraft().currentScreen).reInit();
        }
    }
}
