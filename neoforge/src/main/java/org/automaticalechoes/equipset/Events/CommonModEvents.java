package org.automaticalechoes.equipset.Events;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.automaticalechoes.equipset.common.network.*;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    public static final String EQUIPSET_PAYLOAD = "equipset_payload";
    @SubscribeEvent
    public static void payloads(RegisterPayloadHandlersEvent event){
        PayloadRegistrar registrar = event.registrar(EQUIPSET_PAYLOAD);
        registrar.commonToClient(Packets.FEED_BACK_TYPE, FeedBack.CODEC, (feedBack, iPayloadContext) -> feedBack.handleMessage());
        registrar.commonToServer(Packets.UPDATE_PRESET_NAME_TYPE, UpdateSetName.CODEC, (payload, iPayloadContext) -> payload.handleMessage((ServerPlayer)iPayloadContext.player()));
        registrar.commonToServer(Packets.UPDATE_PRESET_TYPE, UpdatePreset.CODEC, (payload, iPayloadContext) -> payload.handleMessage((ServerPlayer)iPayloadContext.player()));
        registrar.commonToServer(Packets.UPDATE_PRESET_PART_STATUS_TYPE, UpdatePresetPartStatus.CODEC, (payload, iPayloadContext) -> payload.handleMessage((ServerPlayer)iPayloadContext.player()));
        registrar.commonToClient(Packets.SERVER_CONFIG_TYPE, SendServerConfig.CODEC,(payload, context) -> payload.handleMessage());
    }
}
