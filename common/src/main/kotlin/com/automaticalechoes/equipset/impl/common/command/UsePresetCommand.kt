
package com.automaticalechoes.equipset.impl.common.command

import com.automaticalechoes.equipset.common.IPlayerInterface
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

class UsePresetCommand {
    companion object{
        val EQUIPMENT_PRESET: LiteralArgumentBuilder<CommandSourceStack> = Commands.literal("eqs")
        val USE_PRESET: LiteralArgumentBuilder<CommandSourceStack> = Commands.literal("use_preset")
        val ID: RequiredArgumentBuilder<CommandSourceStack, Int> =
            Commands.argument<Int>("id", IntegerArgumentType.integer(-1, 9))

        @JvmStatic
        fun register(dispatcher: CommandDispatcher<CommandSourceStack?>) {
            dispatcher.register(
                EQUIPMENT_PRESET.then(
                    USE_PRESET.executes { context: CommandContext<CommandSourceStack?>? ->
                        UsePreset(context!!.getSource()!!, -1)
                    }
                        .then(ID.executes { context: CommandContext<CommandSourceStack?>? ->
                            UsePreset(context!!.getSource()!!,
                                IntegerArgumentType.getInteger(context, "id"))
                        })
                )
            )
        }

        fun UsePreset(sourceStack: CommandSourceStack, id: Int): Int {
            val player: IPlayerInterface = sourceStack.getPlayer() as? IPlayerInterface ?: return 0
            if (id == -1) {
                player.`equipSet$nextSet`()
            } else {
                player.`equipSet$useSet`(id, false)
            }

            return 1
        }
    }

}