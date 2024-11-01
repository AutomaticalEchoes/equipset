package org.automaticalechoes.equipset;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.automaticalechoes.equipset.api.ContainerType;
import org.automaticalechoes.equipset.api.PresetEquipSet;
import org.automaticalechoes.equipset.client.keyMapping.ModKeyMappings;
import org.automaticalechoes.equipset.common.network.EquipSetNetWork;
import org.automaticalechoes.equipset.config.Config;

import java.util.Optional;

public class EquipSet
{
//	public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
	public static Optional<EquipSetNetWork> NETWORK = Optional.empty();
	public static void init() {
		ContainerType.init();
		PresetEquipSet.init();
//		ModGameRule.init();
//		EntityDataSerializers.registerSerializer(SETS_SERIALIZER);
//		Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
//		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
	}

	@OnlyIn(Dist.CLIENT)
	public static class Client{
		public static int SERVER_SET_NUMS = 4;
		public static void InitServerConfig(int nums){
			Config.load();
			SERVER_SET_NUMS = nums;
			ModKeyMappings.Init();
		}
	}

}
