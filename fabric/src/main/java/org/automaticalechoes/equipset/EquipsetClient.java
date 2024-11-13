package org.automaticalechoes.equipset;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;

@Environment(EnvType.CLIENT)
public class EquipsetClient implements ClientModInitializer {
    public static int inputDelay = 0;

    @Override
    public void onInitializeClient() {
        KeyBindingRegistryImpl.addCategory(ModKeyMappings.MOD_CATEGORY);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (inputDelay > 0) inputDelay--;
            else if(ModKeyMappings.OnClick())
                inputDelay = 10;
        });
    }
}
