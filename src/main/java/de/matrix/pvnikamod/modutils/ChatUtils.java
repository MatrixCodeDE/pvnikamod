package de.matrix.pvnikamod.modutils;

import net.minecraft.client.resources.I18n;

public class ChatUtils extends ModUtil{

    private String[] texts = new String[5];
    private int[] modes = new int[5];

    public ChatUtils(){
        super();
    }

    public final String[] translateMode = {
            "§c" + I18n.format("menu.pvnika.autochat.off"),
            "§a" + I18n.format("menu.pvnika.autochat.always"),
            "§6" + I18n.format("menu.pvnika.autochat.win"),
            "§e" + I18n.format("menu.pvnika.autochat.lose")
    };

    public void toggleMode(int key){
        this.config.chatSettings.modes[key] = (this.config.chatSettings.modes[key] + 1) % 4;
    }

}
