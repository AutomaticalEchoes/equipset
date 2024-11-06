package org.automaticalechoes.equipset.Events;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.automaticalechoes.equipset.common.command.UsePresetCommand;

@EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public static void commands(RegisterCommandsEvent event){
        UsePresetCommand.register(event.getDispatcher());
    }

}
