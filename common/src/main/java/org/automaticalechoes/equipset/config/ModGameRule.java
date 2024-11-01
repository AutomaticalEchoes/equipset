package org.automaticalechoes.equipset.config;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

public class ModGameRule {
    public static GameRules.Key<GameRules.BooleanValue> EQUIP$CURSE_CHECK ;
    public static GameRules.Key<GameRules.IntegerValue> EQUIP$NUMS ;

    public static boolean shouldCurseCheck(ServerPlayer player){
        return EQUIP$CURSE_CHECK != null && player.server.getWorldData().getGameRules().getRule(ModGameRule.EQUIP$CURSE_CHECK).get();
    }

    public static int getNums(ServerPlayer player){
        return EQUIP$NUMS != null ? player.server.getWorldData().getGameRules().getRule(ModGameRule.EQUIP$NUMS).get() : 4 ;
    }

//    public final GameRules.Key<GameRules.BooleanValue> EQUIP$CURSE_CHECK = GameRules.register("equip_curse_check", GameRules.Category.PLAYER,  GameRules.BooleanValue.create(true));
//    public final GameRules.Key<GameRules.IntegerValue> EQUIP$NUMS = GameRules.register( "equip_set_nums",
//            GameRules.Category.PLAYER,  GameRules.IntegerValue.create(4,
//                    (server, integerValue) ->
//                            server.getPlayerList().getPlayers().forEach(serverPlayer -> {
//                                ((IPlayerInterface)serverPlayer).equipSet$resize(integerValue.get());
//                                EquipSet.NETWORK.ifPresent(network -> network.SendServerConfig(serverPlayer));
//                            })
//            )
//    );
}
