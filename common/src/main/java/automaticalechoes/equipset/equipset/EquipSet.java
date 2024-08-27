package automaticalechoes.equipset.equipset;

import automaticalechoes.equipset.equipset.api.ContainerType;
import automaticalechoes.equipset.equipset.api.PresetEquipSet;
import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import automaticalechoes.equipset.equipset.common.Serializer.SetsSerializer;
import automaticalechoes.equipset.equipset.common.network.EquipSetNetWork;
import automaticalechoes.equipset.equipset.config.Config;
import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.slf4j.Logger;

import java.util.Optional;

public class EquipSet
{
	public static final String MODID = "equipset";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
	public static Optional<EquipSetNetWork> NETWORK = Optional.empty();

	public static Config EQUIP_SET_CONFIG = new Config();
	public static void init() {
		ContainerType.init();
		PresetEquipSet.init();

		EntityDataSerializers.registerSerializer(SETS_SERIALIZER);
		Config.Init(Minecraft.getInstance().gameDirectory);
//		Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
//		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
	}

	@Environment(EnvType.CLIENT)
	public static class Client{
		public static int SERVER_SET_NUMS = 4;
		public static void InitServerConfig(int nums){
			SERVER_SET_NUMS = nums;
			ModKeyMappings.Init();
		}
	}

	@Environment(EnvType.SERVER)
	public static class Server{

	}

}
