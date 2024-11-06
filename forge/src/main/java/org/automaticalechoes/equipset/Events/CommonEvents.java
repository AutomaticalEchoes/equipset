package org.automaticalechoes.equipset.Events;


import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.automaticalechoes.equipset.common.command.UsePresetCommand;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void commands(RegisterCommandsEvent event){
        UsePresetCommand.register(event.getDispatcher());
    }



}
