
package com.automaticalechoes.equipset.config

import com.automaticalechoes.equipset.mixin.GameRulesMixin
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.gamerules.GameRule
import net.minecraft.world.level.gamerules.GameRuleCategory


class ModGameRules {
    companion object{
        @JvmStatic
        val `EQUIP$CURSE_CHECK`: GameRule<Boolean> by lazy {
            registerBoolean(
                "equipset_curse_check",
                GameRuleCategory.PLAYER, false) }
        @JvmStatic
        val `EQUIP$SET_NUMS`: GameRule<Int> by lazy {
            registerInt(
                "equipset_set_nums",
                GameRuleCategory.PLAYER, 4, 2, 16)}
        @JvmStatic
        val `EQUIP$ENABLE_ENDER_CHEST_CHECK`: GameRule<Boolean> by lazy {
            registerBoolean(
                "equipset_ender_chest_check",
                GameRuleCategory.PLAYER, false)}

        fun registerBoolean(id: String, category: GameRuleCategory ,defaultsValue: Boolean) : GameRule<Boolean>{
            return GameRulesMixin.registerBoolean(id,category,defaultsValue)
        }

        fun registerInt(id: String, category: GameRuleCategory ,defaultsValue: Int, min: Int, max: Int) : GameRule<Int>{
            return GameRulesMixin.registerInteger(id,category,defaultsValue,min,max)
        }

        fun shouldCurseCheck(player: ServerPlayer): Boolean =
            `EQUIP$CURSE_CHECK`.let{ rule ->
                player.level().server.gameRules.get(rule);
            };


        fun shouldRegisterEnderChest(player: ServerPlayer): Boolean =
            `EQUIP$ENABLE_ENDER_CHEST_CHECK`.let{ rule ->
                player.level().server.gameRules.get(rule);
            };


        fun getSetNums(player: ServerPlayer): Int =
            `EQUIP$SET_NUMS`.let{ rule ->
                player.level().gameRules.get(rule);
            };
    }


//    fun init() {
//        `EQUIP$CURSE_CHECK` =
//        `EQUIP$ENABLE_ENDER_CHEST_CHECK` = GameRulesMixin.register(
//            "equipset_ender_chest_check",
//            GameRuleCategory.PLAYER, false
//        );
//        `EQUIP$SET_NUMS` = GameRulesMixin.register(
//            "equipset_set_nums",
//            GameRuleCategory.PLAYER, IntegerValue.create(
//                4, 2, 16,
//                { server, integerValue ->
//                    server.getPlayerList().getPlayers().forEach({ serverPlayer ->
//                        (serverPlayer as IPlayerInterface).`equipSet$resize`(integerValue.get())
//                        Constants.NETWORK.ifPresent({ network -> network.SendServerConfig(serverPlayer) })
//                    })
//                }
//            )
//        )
//    }



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