package com.AutomaticalEchoes.equipset.common.network;

import com.AutomaticalEchoes.equipset.EquipSet;
import com.AutomaticalEchoes.equipset.common.CommonModEvents;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    private static int Ids = 0;
    public static SimpleNetworkWrapper RegisterPacket(){
        final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EquipSet.MODID);
        CommonModEvents.NetWork=INSTANCE;
        INSTANCE.registerMessage(FeedBack.class, FeedBack.class, Ids++, Side.CLIENT);
//        INSTANCE.messageBuilder(FeedBack.class,1)
//                .encoder(FeedBack::encode)
//                .decoder(FeedBack::decode)
//                .consumer(FeedBack::onMessage)
//                .add();
        INSTANCE.registerMessage(UpdatePreset.class, UpdatePreset.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(UpdatePreset.class,3)
//                .encoder(UpdatePreset::encode)
//                .decoder(UpdatePreset::decode)
//                .consumer(UpdatePreset::onMessage)
//                .add();
        INSTANCE.registerMessage(UpdatePresetPartStatus.class, UpdatePresetPartStatus.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(UpdatePresetPartStatus.class,4)
//                .encoder(UpdatePresetPartStatus::encode)
//                .decoder(UpdatePresetPartStatus::decode)
//                .consumer(UpdatePresetPartStatus::onMessage)
//                .add();
        INSTANCE.registerMessage(UpdateSetName.class, UpdateSetName.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(UpdateSetName.class,5)
//                .encoder(UpdateSetName::encode)
//                .decoder(UpdateSetName::decode)
//                .consumer(UpdateSetName::onMessage)
//                .add();
        return INSTANCE;
    }
}
