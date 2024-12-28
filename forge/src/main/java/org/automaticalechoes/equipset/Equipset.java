package org.automaticalechoes.equipset;

import com.tiviacz.travelersbackpack.capability.CapabilityUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.function.TriConsumer;
import org.automaticalechoes.equipset.NetWork.network.ForgeNetworkImp;
import org.automaticalechoes.equipset.api.ContainerType;
import org.automaticalechoes.equipset.api.IPlayerInterface;
import org.automaticalechoes.equipset.config.ModGameRule;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mod(Constants.MOD_ID)
public class Equipset {

    public Equipset() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        Constants.NETWORK = Optional.of(new ForgeNetworkImp());
        Constants.init();
        if(ModList.get().isLoaded("travelersbackpack")){
            ContainerType.TYPE_TRAVELERSBACKPACK = new ContainerType("travelersbackpack",
                    serverPlayer -> CapabilityUtils.getBackpackInv(serverPlayer) != null,
                    (serverPlayer, slotNum, itemStack) -> CapabilityUtils.getBackpackInv(serverPlayer).getHandler().setStackInSlot(slotNum, itemStack),
                    (serverPlayer, integer) -> CapabilityUtils.getBackpackInv(serverPlayer).getHandler().getStackInSlot(integer),
                    serverPlayer -> CapabilityUtils.getBackpackInv(serverPlayer).getHandler().getSlots());
            ContainerType.TYPES.put(ContainerType.TYPE_TRAVELERSBACKPACK.Name(), ContainerType.TYPE_TRAVELERSBACKPACK);
        }
    }
}
