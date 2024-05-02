package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public abstract class GuiBase extends GuiScreen {

    public final PvnikaMod mod;
    public final Config config;
    public final Minecraft mc;
    public final GuiScreen lastScreen;

    public GuiBase(GuiScreen lastScreen) {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.lastScreen = lastScreen;
    }

    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void refreshButtons() {

    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

}
