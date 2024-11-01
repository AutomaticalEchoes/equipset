package org.automaticalechoes.equipset.Events;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;

@Environment(EnvType.CLIENT)
public class ClientEvents {
    public static int inputDelay = 0;
    public static void init() {
        KeyBindingRegistryImpl.addCategory(ModKeyMappings.MOD_CATEGORY);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (inputDelay > 0) inputDelay--;
            else if(ModKeyMappings.OnClick())
                inputDelay = 10;
        });
    }

}
