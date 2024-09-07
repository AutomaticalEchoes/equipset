package automaticalechoes.equipset.equipset.fabric;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.common.command.UsePresetCommand;
import automaticalechoes.equipset.equipset.fabric.NetWork.network.PacketHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class EquipSetFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EquipSet.init();
        PacketHandler.Init();
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> UsePresetCommand.register(dispatcher));
    }
}