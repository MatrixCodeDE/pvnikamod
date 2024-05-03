package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public abstract class GuiBase extends GuiScreen {

    public final PvnikaMod mod;
    public final Config config;
    public final Minecraft mc;
    public final GuiScreen lastScreen;
    protected GuiButton backButton;

    public GuiBase(GuiScreen lastScreen) {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.lastScreen = lastScreen;
    }

    public void initGui() {
        super.initGui();
    }

    public void setBackButton(int y) {
        setBackButton(width / 2 - 30, y);
    }

    public void setBackButton(int x, int y){
        setBackButton(x, y, 60, 20);
    }

    public void setBackButton(int x, int y, int widthIn, int heightIn){
        buttonList.add(this.backButton = new GuiButton(10, x, y, widthIn, heightIn, I18n.format("gui.back")));
    }

    public void drawScreenNoBG(int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void refresh() {

    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 10){
            this.mc.displayGuiScreen(lastScreen);
        }
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {}

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
