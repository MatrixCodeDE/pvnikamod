package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiVisuals extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;

    private GuiButton customMenu;
    private GuiButton disableShift;
    private GuiButton pingOnTab;
    private GuiButton fullBright;



    public GuiVisuals(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        super.initGui();

        int i = -24;
        int j = 24;

        buttonList.add(this.customMenu = new GuiButton(0, width / 2 - 60, height / 4 + j + i, 120, 20, I18n.format("menu.pvnika.general.name")));
        buttonList.add(this.disableShift = new GuiButton(1, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.particles.name")));
        buttonList.add(this.pingOnTab = new GuiButton(2, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, I18n.format("menu.pvnika.hitbox.name")));
        buttonList.add(this.fullBright = new GuiButton(3, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.name")));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 5 * j + i, 120, 20, I18n.format("gui.back")));
    }
}
