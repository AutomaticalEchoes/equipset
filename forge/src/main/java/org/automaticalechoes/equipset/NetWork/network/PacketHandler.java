package org.automaticalechoes.equipset.NetWork.network;


import io.netty.util.AttributeKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import org.automaticalechoes.equipset.Constants;
import org.automaticalechoes.equipset.common.network.*;

import java.util.function.BiConsumer;

public class PacketHandler {
    private static final ResourceLocation CHANNEL_NAME = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"network");
    private static final int PROTOCOL_VERSION = 1;
    public static SimpleChannel RegisterPacket(){
        final SimpleChannel INSTANCE = ChannelBuilder.named(CHANNEL_NAME)
                .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
                .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
                .networkProtocolVersion(PROTOCOL_VERSION)
                .simpleChannel();
        INSTANCE.messageBuilder(FeedBack.class,1)
                .codec(FeedBack.CODEC)
                .consumerMainThread((feedBack, context) -> feedBack.handleMessage())
                .add();
        INSTANCE.messageBuilder(SendServerConfig.class,3)
                .codec(SendServerConfig.CODEC)
                .consumerMainThread((sendServerConfig, context) -> sendServerConfig.handleMessage())
                .add();
        INSTANCE.messageBuilder(UpdatePresetPartStatus.class,4)
                .codec(UpdatePresetPartStatus.CODEC)
                .consumerMainThread((updatePresetPartStatus, context) -> updatePresetPartStatus.handleMessage(context.getSender()))
                .add();
        INSTANCE.messageBuilder(UpdateSetName.class,5)
                .codec(UpdateSetName.CODEC)
                .consumerMainThread((updateSetName, context) -> updateSetName.handleMessage(context.getSender()))
                .add();
        INSTANCE.messageBuilder(UpdatePreset.class,7)
                .codec(UpdatePreset.CODEC)
                .consumerMainThread((updatePreset, context) -> updatePreset.handleMessage(context.getSender()))
                .add();
        return INSTANCE;
    }
}
