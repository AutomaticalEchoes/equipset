@file:JvmName("Config")
package com.automaticalechoes.equipset.config

import com.automaticalechoes.equipset.Constants
import com.google.common.base.Charsets
import com.google.common.base.Splitter
import com.google.common.io.Files
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

val OPTION_SPLITTER = Splitter.on(':').limit(2)
const val CONFIG_FILE = "config\\echoes_mods_client_config.txt"
const val `EQUIP$KEY_QUICK_CHANGE` = "equipset_key_quick_change"
const val `EQUIP$KEY_SELECT_CHANGE` = "equipset_key_select_change"
var KEY_QUICK_CHANGE = true
var KEY_SELECT_CHANGE = true

fun KeymappingNums(): Boolean {
    return KEY_SELECT_CHANGE
}

fun KeymappingR(): Boolean {
    return KEY_QUICK_CHANGE
}

fun isTrue(string: String?): Boolean {
    return "true" == string
}


fun save() {
    val configDir = File(Minecraft.getInstance().gameDirectory, CONFIG_FILE)
    save(configDir)
}

fun save(configDir: File) {
    try {
        val printWriter = PrintWriter(OutputStreamWriter(FileOutputStream(configDir), StandardCharsets.UTF_8))

        try {
            printWriter.println(`EQUIP$KEY_QUICK_CHANGE` + ":" + KEY_QUICK_CHANGE)
            printWriter.println(`EQUIP$KEY_SELECT_CHANGE` + ":" + KEY_SELECT_CHANGE)
        } catch (var5: Throwable) {
            try {
                printWriter.close()
            } catch (var4: Throwable) {
                var5.addSuppressed(var4)
            }
            throw var5
        }
        printWriter.close()
    } catch (var6: Exception) {
        var6.printStackTrace()
    }
}

fun load() {
    val configDir = File(Minecraft.getInstance().gameDirectory, CONFIG_FILE)
    try {
        if (!configDir.exists()) {
            save(configDir)
            return
        }

        val compoundTag = CompoundTag()
        val bufferedReader = Files.newReader(configDir, Charsets.UTF_8)

        try {
            bufferedReader.lines().forEach { string: String? ->
                try {
                    val iterator: MutableIterator<String?> = OPTION_SPLITTER.split(string!!).iterator()
                    compoundTag.putString(iterator.next()!!, iterator.next().toString())
                } catch (var3: Exception) {
                    Constants.LOG.warn("Skipping bad option: {}", string)
                }
            }
        } catch (var6: Throwable) {
            try {
                bufferedReader.close()
            } catch (var5: Throwable) {
                var6.addSuppressed(var5)
            }

            throw var6
        }
        bufferedReader.close()
        if (compoundTag.contains(`EQUIP$KEY_QUICK_CHANGE`)) {
            KEY_QUICK_CHANGE = isTrue(compoundTag.getString(`EQUIP$KEY_QUICK_CHANGE`).get())
        }
        if (compoundTag.contains(`EQUIP$KEY_SELECT_CHANGE`)) {
            KEY_SELECT_CHANGE = isTrue(compoundTag.getString(`EQUIP$KEY_SELECT_CHANGE`).get())
        }
        KeyMapping.resetMapping()
    } catch (var7: Exception) {
        Constants.LOG.error("Failed to load options", var7)
    }
}

