package automaticalechoes.equipset.equipset.forge.Events;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.forge.Config.ForgeEquipSetClientConfig;
import automaticalechoes.equipset.equipset.forge.NetWork.network.ForgeNetworkImp;
import automaticalechoes.equipset.equipset.forge.NetWork.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = EquipSet.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
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




    @SubscribeEvent
    public static void onConfig(ModConfigEvent event){
        if(event instanceof ModConfigEvent.Unloading) return;
        if(event.getConfig().getModId() == null) return;
        if(!event.getConfig().getModId().equals(EquipSet.MODID)) return;
        if(event.getConfig().getType() == ModConfig.Type.CLIENT){
            ForgeEquipSetClientConfig.OnLoad();
        }

    }

}
