package automaticalechoes.equipset.equipset.fabric;

import automaticalechoes.equipset.equipset.EquipSet;
import net.fabricmc.api.ModInitializer;

public class EquipSetFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EquipSet.init();
    }
}