package automaticalechoes.equipset.equipset.common.network;

import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;

public class EquipSetPacketListener implements PacketListener {
    @Override
    public void onDisconnect(Component component) {

    }

    @Override
    public boolean isAcceptingMessages() {
        return false;
    }
}
