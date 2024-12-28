package org.automaticalechoes.equipset.config;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.automaticalechoes.equipset.Constants;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.mixin.GameRulesMixin;
import org.automaticalechoes.equipset.mixin.BooleanValue;
import org.automaticalechoes.equipset.mixin.IntegerValue;

public class ModGameRule {
    public static GameRules.Key<GameRules.BooleanValue> EQUIP$CURSE_CHECK ;
    public static GameRules.Key<GameRules.IntegerValue> EQUIP$NUMS ;
    public static GameRules.Key<GameRules.BooleanValue> EQUIP$ENABLE_ENDER_CHEST_CHECK ;

    public static void init() {
        EQUIP$CURSE_CHECK = GameRulesMixin.register("equipset_curse_check",
                GameRules.Category.PLAYER, BooleanValue.create(false));
        EQUIP$ENABLE_ENDER_CHEST_CHECK = GameRulesMixin.register("equipset_ender_chest_check",
                GameRules.Category.PLAYER, BooleanValue.create(false));
        EQUIP$NUMS = GameRulesMixin.register("equipset_set_nums",
                GameRules.Category.PLAYER, IntegerValue.create(4, 2, 16,
                        (server, integerValue) ->
                                server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                                    ((IPlayerInterface)serverPlayer).equipSet$resize(integerValue.get());
                                    Constants.NETWORK.ifPresent(network -> network.SendServerConfig(serverPlayer));
                                })
                )
        );
    }

    public static boolean shouldCurseCheck(ServerPlayer player){
        return EQUIP$CURSE_CHECK != null && player.server.getWorldData().getGameRules().getRule(EQUIP$CURSE_CHECK).get();
    }

    public static boolean shouldRegisterEnderChest(ServerPlayer player){
        return EQUIP$ENABLE_ENDER_CHEST_CHECK !=null && player.server.getWorldData().getGameRules().getRule(EQUIP$ENABLE_ENDER_CHEST_CHECK).get();
    }

    public static int getNums(ServerPlayer player){
        return EQUIP$NUMS != null ? player.server.getWorldData().getGameRules().getRule(EQUIP$NUMS).get() : 4 ;
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
