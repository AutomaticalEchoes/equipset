package org.automaticalechoes.equipset;


import org.automaticalechoes.equipset.api.ContainerType;
import org.automaticalechoes.equipset.api.PresetEquipSet;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;
import org.automaticalechoes.equipset.common.network.EquipSetNetWork;
import org.automaticalechoes.equipset.config.Config;
import org.automaticalechoes.equipset.config.ModGameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Constants
{
	public static final String MOD_ID = "equipset";
	public static final String MOD_NAME = "equipset";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
//	public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
	public static Optional<EquipSetNetWork> NETWORK = Optional.empty();
	public static void init() {
		ContainerType.init();
		PresetEquipSet.init();
		ModGameRule.init();
//		EntityDataSerializers.registerSerializer(SETS_SERIALIZER);
//		Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
//		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
	}

	public static class Client{
		public static int SERVER_SET_NUMS = 4;
		public static void InitServerConfig(int nums){
			Config.load();
			SERVER_SET_NUMS = nums;
			ModKeyMappings.Init();
		}
	}

}
