package com.AutomaticalEchoes.equipset;

import com.AutomaticalEchoes.equipset.api.ContainerType;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import com.AutomaticalEchoes.equipset.common.Serializer.SetsSerializer;
import com.AutomaticalEchoes.equipset.common.command.UsePresetCommand;
import com.AutomaticalEchoes.equipset.common.network.OpenScreen;
import com.AutomaticalEchoes.equipset.common.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.commons.lang3.ArrayUtils;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(modid = EquipSet.MODID, name = EquipSet.NAME, version = EquipSet.VERSION)
public class EquipSet
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "equipset";
    // Directly reference a slf4j logger
    public static final String NAME = "EquipSet";
    public static final String VERSION = "1.0";
    public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
//    public EquipSet()
//    {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        // Register the commonSetup method for modloading
//        ContainerType.init();
//        PresetEquipSet.init();
//        KeyMappings.Init();
//        EntityDataSerializers.registerSerializer(SETS_SERIALIZER);
//        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipsuit-config.toml");
//    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        DataSerializers.registerSerializer(SETS_SERIALIZER);
        CommonModEvents.NetWork = PacketHandler.RegisterPacket();
        NetworkRegistry.INSTANCE.registerGuiHandler(MODID, new OpenScreen());
        ContainerType.init();
        PresetEquipSet.init();
        KeyMappings.Init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(event.getSide().isClient()){
            Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.addAll(Minecraft.getMinecraft().gameSettings.keyBindings, KeyMappings.KEY_MAPPING.keySet().toArray(new KeyBinding[0]));
        }
        // some example code
//        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
    @Mod.EventHandler
    public static void commands(FMLServerStartingEvent event) {
        event.registerServerCommand(new UsePresetCommand());
    }

}
