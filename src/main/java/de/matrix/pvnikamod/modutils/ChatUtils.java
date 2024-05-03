package de.matrix.pvnikamod.modutils;

import net.minecraft.client.resources.I18n;

public class ChatUtils extends ModUtil{

    private String[] texts = new String[5];
    private int[] modes = new int[5];

    public ChatUtils(){
        super();
    }

    public final String[] translateMode = {
            I18n.format("menu.pvnika.chat.off"),
            I18n.format("menu.pvnika.chat.always"),
            I18n.format("menu.pvnika.chat.win"),
            I18n.format("menu.pvnika.chat.lose")
    };

    public void toggleMode(int key){
        this.config.chatSettings.modes[key] = (this.config.chatSettings.modes[key] + 1) % 4;
    }

}
