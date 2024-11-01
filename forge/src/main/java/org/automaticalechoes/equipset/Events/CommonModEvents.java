package org.automaticalechoes.equipset.Events;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.forge.NetWork.network.ForgeNetworkImp;
import automaticalechoes.equipset.equipset.forge.NetWork.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.automaticalechoes.equipset.Constants;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    public static SimpleChannel NetWork;
    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(()->{
            NetWork = PacketHandler.RegisterPacket();
            ForgeNetworkImp forgeNetworkImp = new ForgeNetworkImp();
            EquipSet.NETWORK = Optional.of(forgeNetworkImp);
        });

    }

}
