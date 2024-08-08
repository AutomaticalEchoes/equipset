package automaticalechoes.equipset.equipset.common.Serializer;


import automaticalechoes.equipset.equipset.api.PresetManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;


public class SetsSerializer implements EntityDataSerializer<PresetManager> {
    @Override
    public void write(FriendlyByteBuf p_135025_, PresetManager p_135026_) {
        p_135025_.writeNbt(p_135026_.toTag());
    }

    @Override
    public @NotNull PresetManager read(FriendlyByteBuf p_135024_) {
        return PresetManager.FromTag(p_135024_.readNbt());
    }

    @Override
    public @NotNull PresetManager copy(PresetManager p_135023_) {
        return p_135023_;
    }
}
