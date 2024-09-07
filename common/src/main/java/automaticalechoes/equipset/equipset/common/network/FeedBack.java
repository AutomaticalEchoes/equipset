package automaticalechoes.equipset.equipset.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

public interface FeedBack {

    Component component();
    default void handleMessage() {
        Minecraft.getInstance().gui.setOverlayMessage(this.component(),false);
    }


}
