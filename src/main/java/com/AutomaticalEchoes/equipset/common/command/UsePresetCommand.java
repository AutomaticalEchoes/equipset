package com.AutomaticalEchoes.equipset.common.command;

import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class UsePresetCommand {
    public static final LiteralArgumentBuilder<CommandSourceStack> EQUIPMENT_PRESET = Commands.literal("eqs");
    public static final LiteralArgumentBuilder<CommandSourceStack> OPEN_SCREEN = Commands.literal("open_screen");
    public static final LiteralArgumentBuilder<CommandSourceStack> USE_PRESET = Commands.literal("use_preset");
    public static final RequiredArgumentBuilder<CommandSourceStack, Integer> ID = Commands.argument("id", IntegerArgumentType.integer(-1, 9));

    public static void registerServer(CommandDispatcher<CommandSourceStack> p_250343_) {
        p_250343_.register(EQUIPMENT_PRESET
                .then(USE_PRESET.executes(context -> UsePreset(context.getSource(), -1))
                                                           .then(ID.executes(context -> UsePreset(context.getSource(), IntegerArgumentType.getInteger(context, "id"))))));
    }
//    public static void registerClient(CommandDispatcher<CommandSourceStack> p_250343_){
//         p_250343_.register(EQUIPMENT_PRESET.then(OPEN_SCREEN).executes(context -> OpenScreen(context)));
//    }

    public static int UsePreset(CommandSourceStack sourceStack , int id){
        try {
            IPlayerInterface player = (IPlayerInterface) sourceStack.getPlayerOrException();
            if(id == -1){
                player.nextSet();
            }else {
                player.useSet(id, false);
            }

            return 1;
        } catch (CommandSyntaxException e) {
            return 0;
        }
    }

//    public static int OpenScreen(CommandContext<CommandSourceStack> context){
//        Minecraft.getInstance().getTutorial().onOpenInventory();
//        Minecraft.getInstance().setScreen(new EquipmentSettingsScreen(Minecraft.getInstance().player));
//    }
}
