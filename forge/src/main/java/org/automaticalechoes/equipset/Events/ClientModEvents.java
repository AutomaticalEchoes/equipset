package org.automaticalechoes.equipset.Events;



import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.automaticalechoes.equipset.Constants;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
//        event.enqueueWork(() -> {
//            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(), EquipmentSettingsScreen::new);
//        });
    }



}
