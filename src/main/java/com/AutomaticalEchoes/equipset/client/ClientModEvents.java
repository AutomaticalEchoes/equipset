package com.AutomaticalEchoes.equipset.client;


import com.AutomaticalEchoes.equipset.EquipSet;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSet.MODID,value = Side.CLIENT)
public  class ClientModEvents {


}
