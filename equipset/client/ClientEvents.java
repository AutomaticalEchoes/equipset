package com.AutomaticalEchoes.equipset.client;

import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static int inputDelay = 0;

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event){

    }

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if(inputDelay <= 0 && KeyMappings.OnClick())
            inputDelay = 10;
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if (inputDelay > 0) inputDelay--;
    }

}
