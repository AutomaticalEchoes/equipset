package automaticalechoes.equipset.equipset;

import automaticalechoes.equipset.equipset.api.ContainerType;
import automaticalechoes.equipset.equipset.api.PresetEquipSet;
import automaticalechoes.equipset.equipset.client.keyMapping.ModKeyMappings;
import automaticalechoes.equipset.equipset.common.Serializer.SetsSerializer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataSerializers;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class EquipSet
{
	public static final String MODID = "equipset";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final SetsSerializer SETS_SERIALIZER = new SetsSerializer();
	public static void init() {
		ContainerType.init();
		PresetEquipSet.init();

		EntityDataSerializers.registerSerializer(SETS_SERIALIZER);

//		Minecraft.getInstance().options.keyMappings = ArrayUtils.addAll(Minecraft.getInstance().options.keyMappings, ModKeyMappings.KEY_MAPPING.keySet().toArray(new KeyMapping[0]));
//		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EquipSetConfig.SPEC,"equipset-config.toml");
	}
}
