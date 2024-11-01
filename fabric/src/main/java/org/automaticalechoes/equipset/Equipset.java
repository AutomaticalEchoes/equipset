package org.automaticalechoes.equipset;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.automaticalechoes.equipset.Events.ClientEvents;
import org.automaticalechoes.equipset.NetWork.network.PacketHandler;
import org.automaticalechoes.equipset.common.command.UsePresetCommand;

public class Equipset implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        ClientEvents.init();
        EquipSet.init();
        PacketHandler.Init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> UsePresetCommand.register(dispatcher));

    }
}
