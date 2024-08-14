package automaticalechoes.equipset.equipset.forge.Events;

import automaticalechoes.equipset.equipset.common.command.UsePresetCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void commands(RegisterCommandsEvent event){
        UsePresetCommand.register(event.getDispatcher());
    }


}
