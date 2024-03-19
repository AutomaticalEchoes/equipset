package com.AutomaticalEchoes.equipset.api;

import net.minecraft.client.gui.GuiButton;

public interface OnPress<T extends GuiButton> {
    void action(T t);
}
