package org.automaticalechoes.equipset;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;
import org.automaticalechoes.equipset.Events.ClientEvents;
import org.automaticalechoes.equipset.NetWork.network.PacketHandler;
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
        ClientEvents.init();
        ModGameRule.EQUIP$NUMS = GameRuleRegistry.register("equip_set_nums",
                GameRules.Category.PLAYER, GameRuleFactory.createIntRule(4,
                        (server, integerValue) ->
                                server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                                    ((IPlayerInterface)serverPlayer).equipSet$resize(integerValue.get());
                                    Constants.NETWORK.ifPresent(network -> network.SendServerConfig(serverPlayer));
                                })
                )
        );
        PacketHandler.Init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> UsePresetCommand.register(dispatcher));
        Constants.init();
    }
}
