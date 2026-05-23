package com.automaticalechoes.equipset.client.gui;

import com.automaticalechoes.equipset.client.screen.EquipmentSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

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
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        super.onClick(event, doubleClick);
        if(!(Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen)) return;
        if(screen.getFocused() == this){
            lostFocus(screen);
        }else {
            this.setFocused(true);
            screen.setFocused(this);
        }
    }


}
