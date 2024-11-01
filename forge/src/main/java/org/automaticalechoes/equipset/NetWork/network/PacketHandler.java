package org.automaticalechoes.equipset.NetWork.network;


import automaticalechoes.equipset.equipset.EquipSet;
import automaticalechoes.equipset.equipset.forge.Events.CommonModEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final ResourceLocation CHANNEL_NAME=new ResourceLocation(EquipSet.MODID,"network");
    private static final String PROTOCOL_VERSION = new ResourceLocation(EquipSet.MODID,"1").toString();
    public static SimpleChannel RegisterPacket(){
        final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(()->PROTOCOL_VERSION)
                .simpleChannel();
        CommonModEvents.NetWork=INSTANCE;
        INSTANCE.messageBuilder(ForgeFeedBack.class,1)
                .encoder(ForgeFeedBack::encode)
                .decoder(ForgeFeedBack::decode)
                .consumerMainThread(ForgeFeedBack::onMessage)
                .add();
        INSTANCE.messageBuilder(ForgeUpdatePreset.class,3)
                .encoder(ForgeUpdatePreset::encode)
                .decoder(ForgeUpdatePreset::decode)
                .consumerMainThread(ForgeUpdatePreset::onMessage)
                .add();
        INSTANCE.messageBuilder(ForgeUpdatePresetPartStatus.class,4)
                .encoder(ForgeUpdatePresetPartStatus::encode)
                .decoder(ForgeUpdatePresetPartStatus::decode)
                .consumerMainThread(ForgeUpdatePresetPartStatus::onMessage)
                .add();
        INSTANCE.messageBuilder(ForgeUpdateSetName.class,5)
                .encoder(ForgeUpdateSetName::encode)
                .decoder(ForgeUpdateSetName::decode)
                .consumerMainThread(ForgeUpdateSetName::onMessage)
                .add();
//        INSTANCE.messageBuilder(ForgeAskConfig.class,6)
//                .encoder(ForgeAskConfig::encode)
//                .decoder(ForgeAskConfig::decode)
//                .consumerMainThread(ForgeAskConfig::onMessage)
//                .add();
        INSTANCE.messageBuilder(ForgeSendServerConfig.class,7)
                .encoder(ForgeSendServerConfig::encode)
                .decoder(ForgeSendServerConfig::decode)
                .consumerMainThread(ForgeSendServerConfig::onMessage)
                .add();
        return INSTANCE;
    }
}
