package org.automaticalechoes.equipset.Events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public static void OnPayLoad()

}
