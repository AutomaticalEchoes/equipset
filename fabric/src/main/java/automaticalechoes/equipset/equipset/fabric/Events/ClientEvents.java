package automaticalechoes.equipset.equipset.fabric.Events;

import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static int inputDelay = 0;
    static {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (inputDelay > 0) inputDelay--;
            else if(ModKeyMappings.OnClick())
                inputDelay = 10;
        });
    }

}
