package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiIngameInfos extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private GuiButton fpsButton;
    private GuiButton coordButton;
    private GuiScreen lastScreen;

    public GuiIngameInfos(GuiScreen lastScreen){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.lastScreen = lastScreen;
    }

    @Override
    public void initGui(){
        super.initGui();
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.fpsButton = new GuiButton(0, width / 2 - 60, height / 4 + 0 * j + i, 120, 20, I18n.format("menu.pvnika.iginfos.fps.name")));
        buttonList.add(this.coordButton = new GuiButton(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.iginfos.coords.name")));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 2 * j + i, 60, 20, I18n.format("gui.back")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.iginfos.fps.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id){
            case 0:
                this.mc.displayGuiScreen(new GuiFPS(this));
                break;
            case 1:
                // this.mc.displayGuiScreen(new GuiCoords(this));
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
                break;
        }
    }



}
