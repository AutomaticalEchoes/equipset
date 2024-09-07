package automaticalechoes.equipset.equipset.fabric.NetWork.network;


import automaticalechoes.equipset.equipset.EquipSet;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class PacketHandler {
    public static final ResourceLocation FEED_BACK = new ResourceLocation(EquipSet.MODID,"feed_back");
    public static final ResourceLocation SERVER_CONFIG = new ResourceLocation(EquipSet.MODID,"server_config");
    public static final ResourceLocation UPDATE_PRESET = new ResourceLocation(EquipSet.MODID,"update_preset");
    public static final ResourceLocation UPDATE_PRESET_PART_STATUS = new ResourceLocation(EquipSet.MODID,"update_preset_status");
    public static final ResourceLocation UPDATE_PRESET_NAME = new ResourceLocation(EquipSet.MODID,"update_preset_name");
    public static final PacketType<FabricFeedBack> FABRIC_FEED_BACK = PacketType.create(FEED_BACK, FabricFeedBack::decode);
    public static final PacketType<FabricSendServerConfig> FABRIC_SERVER_CONFIG = PacketType.create(SERVER_CONFIG, FabricSendServerConfig::decode);
    public static final PacketType<FabricUpdatePreset> FABRIC_UPDATE_PRESET = PacketType.create(UPDATE_PRESET, FabricUpdatePreset::decode);
    public static final PacketType<FabricUpdatePresetPartStatus> FABRIC_UPDATE_PRESET_PART_STATUS = PacketType.create(UPDATE_PRESET_PART_STATUS, FabricUpdatePresetPartStatus::decode);
    public static final PacketType<FabricUpdateSetName> FABRIC_UPDATE_SET_NAME = PacketType.create(UPDATE_PRESET_NAME, FabricUpdateSetName::decode);
    public static void Init(){
        ClientPlayNetworking.registerGlobalReceiver(FABRIC_FEED_BACK, (packet, player, responseSender) -> packet.handleMessage());
        ClientPlayNetworking.registerGlobalReceiver(FABRIC_SERVER_CONFIG, (packet, player, responseSender) -> packet.handleMessage());
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET, (packet, player, responseSender) -> packet.handleMessage(player));
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET_PART_STATUS, (packet, player, responseSender) -> packet.handleMessage(player));
        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_SET_NAME, (packet, player, responseSender) -> packet.handleMessage(player));
        EquipSet.NETWORK = Optional.of(new FabricNetworkImp());
    }

}
