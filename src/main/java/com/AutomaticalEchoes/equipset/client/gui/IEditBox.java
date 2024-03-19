package com.AutomaticalEchoes.equipset.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class IEditBox extends GuiTextField {
    private final Consumer<String> runnable;
    public IEditBox(int id, FontRenderer p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, Consumer<String> runnable) {
        super(id, p_94106_, p_94107_, p_94108_, p_94109_, p_94110_);
        setCanLoseFocus(true);
        setFocused(false);
        this.runnable = runnable;
    }

    public boolean lostFocus(){
        this.setFocused(false);
        this.runnable.accept(this.getText());
        this.setFocused(false);
        return true;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(this.isFocused()) return lostFocus();
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if(isFocused() && keyCode == 28) {
            this.lostFocus();
            return true;
        }
        return super.textboxKeyTyped(typedChar, keyCode);
    }

    //    @Override
//    public void onMouse(double p_279417_, double p_279437_) {
//        super.onClick(p_279417_, p_279437_);
//        if(!(Minecraft.getInstance().screen instanceof EquipmentSettingsScreen screen)) return;
//        if(screen.getFocused() == this){
//            lostFocus(screen);
//        }else {
//            this.setFocused(true);
//            screen.setFocused(this);
//        }
//    }


}
