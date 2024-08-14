package automaticalechoes.equipset.equipset.config;

import automaticalechoes.equipset.equipset.EquipSet;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class Config {

    static final ArrayList<ConfigValue<?>> VALUES = new ArrayList<>();
    static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
    static File optionsFile = null;
    static CompoundTag configTag = new CompoundTag();


    public static void Init(File GameDist) {
        optionsFile = new File(GameDist, "\\config\\equipset-config.txt");

    }

    @Environment(EnvType.SERVER)
    public static class Server extends Config {
        static final String EQUIP$CURSE_CHECK = "equip_curse_check";
        static final String EQUIP$NUMS = "equip_set_nums";
        @Environment(EnvType.SERVER)
        protected static ConfigValue<Boolean> CURSE_CHECK = new ConfigValue<>(EQUIP$CURSE_CHECK, new Data<>(false), Server::getCurse);
        @Environment(EnvType.SERVER)
        protected static ConfigValue<Integer> NUMS = new ConfigValue<>(EQUIP$CURSE_CHECK, new Data<>(4), Server::getNums);
        public static int NUMS() {
            return NUMS.data().getData();
        }
        public static boolean CurseCheck() {
            return CURSE_CHECK.data().getData();
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Client extends Config {
        static final String EQUIP$KEY_R = "equip_set_keymapping_r";
        static final String EQUIP$KEY_NUMS = "equip_set_keymapping_nums";
        @Environment(EnvType.CLIENT)
        protected static ConfigValue<Boolean> KEYMAPPING_R = new ConfigValue<>(EQUIP$KEY_R, new Data<>(true), );
        @Environment(EnvType.CLIENT)
        protected static ConfigValue<Boolean> KEYMAPPING_NUMS = new ConfigValue<>(EQUIP$KEY_NUMS, new Data<>(true),);

        public static boolean KeymappingNums() {
            return KEYMAPPING_NUMS.data().getData();
        }

        public static boolean KeymappingR() {
            return KEYMAPPING_R.data().getData();
        }
    }
}
