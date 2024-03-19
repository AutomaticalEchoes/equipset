package com.AutomaticalEchoes.equipset.common.Serializer;

import com.AutomaticalEchoes.equipset.api.PresetManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

import java.io.IOException;

public class SetsSerializer implements DataSerializer<PresetManager> {
    @Override
    public void write(PacketBuffer buf, PresetManager value) {
        buf.writeCompoundTag(value.toTag());
    }

    @Override
    public PresetManager read(PacketBuffer buf) throws IOException {
        return PresetManager.FromTag(buf.readCompoundTag());
    }

    @Override
    public DataParameter<PresetManager> createKey(int id) {
        return new DataParameter<>(id,this);
    }

    @Override
    public PresetManager copyValue(PresetManager value) {
        return value;
    }
}
