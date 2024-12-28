package org.automaticalechoes.equipset;


import com.tiviacz.travelersbackpack.capability.AttachmentUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.automaticalechoes.equipset.NetWork.network.NeoForgeNetworkImp;
import org.automaticalechoes.equipset.api.ContainerType;

import java.util.Optional;

@Mod(Constants.MOD_ID)
public class Equipset {

    public Equipset(IEventBus eventBus) {
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.
        Constants.NETWORK = Optional.of(new NeoForgeNetworkImp());
        Constants.init();
        if(ModList.get().isLoaded("travelersbackpack")){
            ContainerType.TYPE_TRAVELERSBACKPACK = new ContainerType("travelersbackpack",
                    serverPlayer -> AttachmentUtils.getBackpackInv(serverPlayer) != null,
                    (serverPlayer, slotNum, itemStack) -> AttachmentUtils.getBackpackInv(serverPlayer).getHandler().setStackInSlot(slotNum, itemStack),
                    (serverPlayer, integer) -> AttachmentUtils.getBackpackInv(serverPlayer).getHandler().getStackInSlot(integer),
                    serverPlayer -> AttachmentUtils.getBackpackInv(serverPlayer).getHandler().getSlots());
            ContainerType.TYPES.put(ContainerType.TYPE_TRAVELERSBACKPACK.Name(), ContainerType.TYPE_TRAVELERSBACKPACK);
        }
    }
}
