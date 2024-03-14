package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
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
        INSTANCE.messageBuilder(FeedBack.class,1)
                .encoder(FeedBack::encode)
                .decoder(FeedBack::decode)
                .consumerMainThread(FeedBack::onMessage)
                .add();
        INSTANCE.messageBuilder(UpdatePreset.class,3)
                .encoder(UpdatePreset::encode)
                .decoder(UpdatePreset::decode)
                .consumerMainThread(UpdatePreset::onMessage)
                .add();
        INSTANCE.messageBuilder(UpdatePresetPartStatus.class,4)
                .encoder(UpdatePresetPartStatus::encode)
                .decoder(UpdatePresetPartStatus::decode)
                .consumerMainThread(UpdatePresetPartStatus::onMessage)
                .add();
        INSTANCE.messageBuilder(UpdateSetName.class,5)
                .encoder(UpdateSetName::encode)
                .decoder(UpdateSetName::decode)
                .consumerMainThread(UpdateSetName::onMessage)
                .add();
        return INSTANCE;
    }
}
