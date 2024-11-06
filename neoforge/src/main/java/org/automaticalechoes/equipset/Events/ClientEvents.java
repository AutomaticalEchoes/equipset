package org.automaticalechoes.equipset.Events;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents {
    public static int inputDelay = 0;


    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if(inputDelay <= 0 && ModKeyMappings.OnClick())
            inputDelay = 10;
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event){
        if (inputDelay > 0) inputDelay--;
    }

}
