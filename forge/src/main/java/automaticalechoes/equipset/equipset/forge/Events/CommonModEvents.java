package automaticalechoes.equipset.equipset.forge.Events;

import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import automaticalechoes.equipset.equipset.forge.Config.ForgeEquipSetClientConfig;
import automaticalechoes.equipset.equipset.forge.Config.ForgeEquipSetServerConfig;
import automaticalechoes.equipset.equipset.forge.NetWork.network.ForgeNetworkImp;
import automaticalechoes.equipset.equipset.forge.NetWork.network.PacketHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.ArrayUtils;

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

        ModKeyMappings.Init();

    }

    @SubscribeEvent
    public static void Load(FMLLoadCompleteEvent event) {
        Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
    }

    @SubscribeEvent
    public static void onConfig(ModConfigEvent event){
        if(event.getConfig().getModId() == null) return;
        if(event.getConfig().getModId() != EquipSet.MODID) return;
        if(event.getConfig().getType() == ModConfig.Type.CLIENT){
            ForgeEquipSetClientConfig.OnLoad();
        }else if(event.getConfig().getType() == ModConfig.Type.SERVER){
            ForgeEquipSetServerConfig.OnLoad();
        }

    }

}
