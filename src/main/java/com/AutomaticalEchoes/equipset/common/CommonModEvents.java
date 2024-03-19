package com.AutomaticalEchoes.equipset.common;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import com.AutomaticalEchoes.equipset.common.network.PacketHandler;
import com.AutomaticalEchoes.equipset.config.ConfigValue;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.ArrayUtils;

@Mod.EventBusSubscriber(modid = EquipSet.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    public static SimpleChannel NetWork;
    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(()->{
            NetWork = PacketHandler.RegisterPacket();
        });

    }

    @SubscribeEvent
    public static void Load(FMLLoadCompleteEvent event) {
        Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, KeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
    }

    @SubscribeEvent
    public static void Config(ModConfigEvent event){
        if((event instanceof ModConfigEvent.Loading || event instanceof ModConfigEvent.Reloading) && event.getConfig().getModId().equals(EquipSet.MODID)){
            ConfigValue.reInit();
        }
    }

}
