package automaticalechoes.equipset.equipset.config;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.api.IPlayerInterface;
import net.minecraft.world.level.GameRules;

public class ModGameRule {
    public static final GameRules.Key<GameRules.BooleanValue> EQUIP$CURSE_CHECK = GameRules.register("equip_curse_check", GameRules.Category.PLAYER,  GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.IntegerValue> EQUIP$NUMS = GameRules.register( "equip_set_nums",
            GameRules.Category.PLAYER,  GameRules.IntegerValue.create(4,
                    (server, integerValue) ->
                            server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                                ((IPlayerInterface)serverPlayer).equipSet$resize(integerValue.get());
                                EquipSet.NETWORK.ifPresent(network -> network.SendServerConfig(serverPlayer));
                            })
            )
    );

    public static void init() {

    }
}
