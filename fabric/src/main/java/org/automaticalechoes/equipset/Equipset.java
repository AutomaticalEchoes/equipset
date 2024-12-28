package org.automaticalechoes.equipset;

import com.tiviacz.travelersbackpack.component.ComponentUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.GameRules;
import org.automaticalechoes.equipset.NetWork.network.PacketHandler;
import org.automaticalechoes.equipset.api.ContainerType;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.common.command.UsePresetCommand;
import org.automaticalechoes.equipset.config.ModGameRule;

public class Equipset implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        // Use Fabric to bootstrap the Common mod.

        if(FabricLoader.getInstance().isModLoaded("travelersbackpack")){
            ContainerType.TYPE_TRAVELERSBACKPACK = new ContainerType("travelersbackpack",
                    serverPlayer -> ComponentUtils.getBackpackInv(serverPlayer) != null,
                    (serverPlayer, slotNum, itemStack) -> ComponentUtils.getBackpackInv(serverPlayer).getInventory().setItem(slotNum, itemStack),
                    (serverPlayer, integer) -> ComponentUtils.getBackpackInv(serverPlayer).getInventory().getItem(integer),
                    serverPlayer -> ComponentUtils.getBackpackInv(serverPlayer).getInventory().getContainerSize());
            ContainerType.TYPES.put(ContainerType.TYPE_TRAVELERSBACKPACK.Name(), ContainerType.TYPE_TRAVELERSBACKPACK);
        }
        PacketHandler.Init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> UsePresetCommand.register(dispatcher));
        Constants.init();
    }
}
