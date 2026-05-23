package com.automaticalechoes.equipset.common.network;



import com.automaticalechoes.equipset.Constants;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;


public class Packets {
    public static final Identifier FEED_BACK = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"feed_back");
    public static final Identifier SERVER_CONFIG = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"server_config");
    public static final Identifier UPDATE_PRESET = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"update_preset");
    public static final Identifier UPDATE_PRESET_PART_STATUS = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"update_preset_status");
    public static final Identifier UPDATE_PRESET_NAME = Identifier.fromNamespaceAndPath(Constants.MOD_ID,"update_preset_name");
    public static final CustomPacketPayload.Type<FeedBack> FEED_BACK_TYPE = new CustomPacketPayload.Type<>(Packets.FEED_BACK);
    public static final CustomPacketPayload.Type<SendServerConfig> SERVER_CONFIG_TYPE = new CustomPacketPayload.Type<>(Packets.SERVER_CONFIG);
    public static final CustomPacketPayload.Type<UpdatePreset> UPDATE_PRESET_TYPE = new CustomPacketPayload.Type<>(Packets.UPDATE_PRESET);
    public static final CustomPacketPayload.Type<UpdatePresetPartStatus> UPDATE_PRESET_PART_STATUS_TYPE = new CustomPacketPayload.Type<>(Packets.UPDATE_PRESET_PART_STATUS);
    public static final CustomPacketPayload.Type<UpdateSetName> UPDATE_PRESET_NAME_TYPE = new CustomPacketPayload.Type<>(Packets.UPDATE_PRESET_NAME);
//    public static final PacketType<FeedBack> TYPE_FEED_BACK = new PacketType<>(PacketFlow.SERVERBOUND, FEED_BACK);
//    public static final PacketType<SendServerConfig> FABRIC_SERVER_CONFIG = PacketType.create(SERVER_CONFIG, FabricSendServerConfig::decode);
//    public static final PacketType<UpdatePreset> FABRIC_UPDATE_PRESET = PacketType.create(UPDATE_PRESET, FabricUpdatePreset::decode);
//    public static final PacketType<UpdatePresetPartStatus> FABRIC_UPDATE_PRESET_PART_STATUS = PacketType.create(UPDATE_PRESET_PART_STATUS, FabricUpdatePresetPartStatus::decode);
//    public static final PacketType<UpdateSetName> FABRIC_UPDATE_SET_NAME = PacketType.create(UPDATE_PRESET_NAME, FabricUpdateSetName::decode);
//    public static void Init(){
//        ClientPlayNetworking.registerGlobalReceiver(FABRIC_FEED_BACK, (packet, player, responseSender) -> packet.handleMessage());
//        ClientPlayNetworking.registerGlobalReceiver(FABRIC_SERVER_CONFIG, (packet, player, responseSender) -> packet.handleMessage());
//        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET, (packet, player, responseSender) -> packet.handleMessage(player));
//        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_PRESET_PART_STATUS, (packet, player, responseSender) -> packet.handleMessage(player));
//        ServerPlayNetworking.registerGlobalReceiver(FABRIC_UPDATE_SET_NAME, (packet, player, responseSender) -> packet.handleMessage(player));
//        EquipSet.NETWORK = Optional.of(new FabricNetworkImp());
//    }

}
