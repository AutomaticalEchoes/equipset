package org.automaticalechoes.equipset;


import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.automaticalechoes.equipset.NetWork.network.NeoForgeNetworkImp;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.config.ModGameRule;

import java.util.Optional;

@Mod(Constants.MOD_ID)
public class Equipset {

    public Equipset(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        ModGameRule.EQUIP$NUMS = GameRules.register("equip_set_nums",
                GameRules.Category.PLAYER, GameRules.IntegerValue.create(4,
                        (server, integerValue) ->
                                server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                                    ((IPlayerInterface)serverPlayer).equipSet$resize(integerValue.get());
                                    Constants.NETWORK.ifPresent(network -> network.SendServerConfig(serverPlayer));
                                })
                )
        );
        Constants.NETWORK = Optional.of(new NeoForgeNetworkImp());
        Constants.init();
        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");

    }
}
