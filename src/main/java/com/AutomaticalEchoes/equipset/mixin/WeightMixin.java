package com.AutomaticalEchoes.equipset.mixin;

import com.AutomaticalEchoes.equipset.client.gui.IWeight;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractWidget.class)
public class WeightMixin implements IWeight {
    @Shadow private boolean focused;

    public void setIFocus(boolean b){
        focused = b;
    }
}
