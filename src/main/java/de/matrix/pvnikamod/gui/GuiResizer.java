package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiResizer extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;
    private static final String[] GUISCALES = new String[]{"menu.pvnika.all.default", "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};

    public GuiResizer(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }
}
