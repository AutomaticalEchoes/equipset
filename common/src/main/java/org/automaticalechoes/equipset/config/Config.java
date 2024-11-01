package org.automaticalechoes.equipset.config;


import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;

import net.minecraft.client.*;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.automaticalechoes.equipset.Constants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class Config {
    private static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);
    private static final String CONFIG_FILE = "config\\equipset_client_config.txt";
    static final String EQUIP$KEY_R = "equip_set_keymapping_r";
    static final String EQUIP$KEY_NUMS = "equip_set_keymapping_nums";
    protected static Boolean KEYMAPPING_R = true;
    protected static Boolean KEYMAPPING_NUMS = true;


    public static boolean KeymappingNums() {
        return KEYMAPPING_NUMS;
    }

    public static boolean KeymappingR() {
        return KEYMAPPING_R;
    }


    public static void save(){
        File configDir = new File(Minecraft.getInstance().gameDirectory, CONFIG_FILE);
        save(configDir);
    }

    public static void save(File configDir) {
        try {
            final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(configDir), StandardCharsets.UTF_8));

            try {
                printWriter.println(EQUIP$KEY_R + ":" + KEYMAPPING_R);
                printWriter.println(EQUIP$KEY_NUMS + ":" + KEYMAPPING_NUMS);
            } catch (Throwable var5) {
                try {
                    printWriter.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }
                throw var5;
            }
            printWriter.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
    }

    public static void load() {
        File configDir = new File(Minecraft.getInstance().gameDirectory, CONFIG_FILE);
        try {
            if (!configDir.exists()) {
                save(configDir);
                return;
            }

            CompoundTag compoundTag = new CompoundTag();
            BufferedReader bufferedReader = Files.newReader(configDir, Charsets.UTF_8);

            try {
                bufferedReader.lines().forEach((string) -> {
                    try {
                        Iterator<String> iterator = OPTION_SPLITTER.split(string).iterator();
                        compoundTag.putString(iterator.next(), (String)iterator.next());
                    } catch (Exception var3) {
                        Constants.LOG.warn("Skipping bad option: {}", string);
                    }

                });
            } catch (Throwable var6) {
                try {
                    bufferedReader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }
            bufferedReader.close();
            if (compoundTag.contains(EQUIP$KEY_R)) {
                KEYMAPPING_R = isTrue(compoundTag.getString(EQUIP$KEY_R));
            }
            if (compoundTag.contains(EQUIP$KEY_NUMS)) {
                KEYMAPPING_NUMS = isTrue(compoundTag.getString(EQUIP$KEY_NUMS));
            }
            KeyMapping.resetMapping();
        } catch (Exception var7) {
            Constants.LOG.error("Failed to load options", var7);
        }

    }

    static boolean isTrue(String string) {
        return "true".equals(string);
    }
}
