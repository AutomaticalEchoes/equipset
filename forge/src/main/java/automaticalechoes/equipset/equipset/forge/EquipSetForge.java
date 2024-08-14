package automaticalechoes.equipset.equipset.forge;

import automaticalechoes.equipset.equipset.forge.Config.EquipSetConfig;
import dev.architectury.platform.forge.EventBuses;
import automaticalechoes.equipset.equipset.EquipSet;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EquipSet.MODID)
public class EquipSetForge {
    public EquipSetForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(EquipSet.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        EquipSet.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
    }
}