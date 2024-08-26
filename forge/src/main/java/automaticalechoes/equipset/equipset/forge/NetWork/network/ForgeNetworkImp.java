package automaticalechoes.equipset.equipset.forge.NetWork.network;

import automaticalechoes.equipset.equipset.common.network.EquipSetNetWork;
import automaticalechoes.equipset.equipset.forge.Events.CommonModEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class ForgeNetworkImp implements EquipSetNetWork {
    @Override
    public void AskConfig() {
        CommonModEvents.NetWork.sendToServer(new ForgeAskConfig());
    }

    @Override
    public void SendFeedBack(ServerPlayer serverPlayer, Component component) {
        CommonModEvents.NetWork.sendTo(new ForgeFeedBack(component), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public void SendUpdatePreset(int targetNum, int cases) {
        CommonModEvents.NetWork.sendToServer(new ForgeUpdatePreset(targetNum,cases));
    }

    @Override
    public void SendUpdatePresetPartStatus(int targetNum, String partName, boolean enable) {
        CommonModEvents.NetWork.sendToServer(new ForgeUpdatePresetPartStatus(targetNum, partName, enable));
    }

    @Override
    public void SendUpdateSetName(int suitNum, String suitName) {
        CommonModEvents.NetWork.sendToServer(new ForgeUpdateSetName(suitNum, suitName));
    }
}
