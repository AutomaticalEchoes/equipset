package com.AutomaticalEchoes.equipset;

import com.AutomaticalEchoes.equipset.api.ContainerType;
import com.AutomaticalEchoes.equipset.api.PresetEquipSet;
import com.AutomaticalEchoes.equipset.client.keyMapping.KeyMappings;
import com.AutomaticalEchoes.equipset.common.Serializer.SetsSerializer;
import com.AutomaticalEchoes.equipset.config.EquipSetConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EquipSet.MODID)
public class EquipSet
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "equipset";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public EquipSet()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        ContainerType.init();
        PresetEquipSet.init();

        EntityDataSerializers.registerSerializer(SETS_SERIALIZER);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
    }

}
