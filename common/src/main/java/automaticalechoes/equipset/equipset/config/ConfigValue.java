package automaticalechoes.equipset.equipset.config;

import com.google.common.base.Function;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

public record ConfigValue<T>(String name, Data<T> data){
    public void set(T value){
        data.setData(value);
    }

    public T get(){
        return data.getData();
    }
}
