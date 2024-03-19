package com.AutomaticalEchoes.equipset.client.gui;

import com.AutomaticalEchoes.equipset.client.screen.EquipmentSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class IEditBox extends EditBox {
    private final Consumer<String> runnable;
    public IEditBox(Font p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, Component p_94112_, Consumer<String> runnable) {
        super(p_94106_, p_94107_, p_94108_, p_94109_, p_94110_, null, p_94112_);
        setCanLoseFocus(true);
        setFocused(false);
        this.runnable = runnable;
    }

    public boolean lostFocus(Screen screen){
        screen.setFocused(null);
        this.runnable.accept(this.getValue());
        this.setFocused(false);
        return true;
    }

    @Override
    public void onClick(double p_279417_, double p_279437_) {
        super.onClick(p_279417_, p_279437_);
        if(!(Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen)) return;
        if(screen.getFocused() == this){
            lostFocus(screen);
        }else {
            this.setFocused(true);
            screen.setFocused(this);
        }
    }


}
