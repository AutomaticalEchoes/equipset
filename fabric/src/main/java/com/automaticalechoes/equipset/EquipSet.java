package com.automaticalechoes.equipset;

import com.automaticalechoes.equipset.impl.common.command.UsePresetCommand;
import com.automaticalechoes.equipset.netWork.PacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class EquipSet implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
        ExampleModKT.exampleKotlin();

        PacketHandler.Init();
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) -> UsePresetCommand.register(dispatcher));
        Constants.init();
    }
}
