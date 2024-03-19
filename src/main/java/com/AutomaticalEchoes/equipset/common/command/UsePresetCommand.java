package com.AutomaticalEchoes.equipset.common.command;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.api.IPlayerInterface;
import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class UsePresetCommand extends CommandBase {

    @Override
    public String getName() {
        return "eqs";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "eqs.use_preset";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
            if(!(sender.getCommandSenderEntity() instanceof EntityPlayer)) return;
            IPlayerInterface player = (IPlayerInterface) sender.getCommandSenderEntity();
            if(args.length == 0) return;
            if(args[0].equals("use_preset")) {
                if(args.length == 2) {
                    Integer integer = Integer.valueOf(args[1]);
                    if(integer >=0 && integer <= EquipSetConfig.PRESET_NUM)
                        player.useSet(integer,false) ;
                }else
                player.nextSet();
            }

    }
}
