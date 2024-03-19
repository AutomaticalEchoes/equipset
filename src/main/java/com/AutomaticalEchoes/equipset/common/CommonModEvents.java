package com.AutomaticalEchoes.equipset.common;

import com.AutomaticalEchoes.equipset.EquipSet;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod.EventBusSubscriber(modid = EquipSet.MODID)
public class CommonModEvents {

    public static SimpleNetworkWrapper NetWork;


//    @SubscribeEvent
//    public static void Config(ConfigChangedEvent event){
//        if((event instanceof ModConfigEvent.Loading || event instanceof ModConfigEvent.Reloading) && event.getConfig().getModId().equals(EquipSet.MODID)){
//            ConfigValue.reInit();
//        }
//    }

}
