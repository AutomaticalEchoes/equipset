package org.automaticalechoes.equipset;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.automaticalechoes.equipset.NetWork.network.ForgeNetworkImp;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.config.ModGameRule;

import java.util.Optional;

@Mod(Constants.MOD_ID)
public class Equipset {

    public Equipset() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
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
        Constants.NETWORK = Optional.of(new ForgeNetworkImp());
        Constants.init();
    }
}
