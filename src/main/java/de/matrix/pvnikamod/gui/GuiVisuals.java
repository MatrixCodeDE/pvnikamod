package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.VisualsUtils;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiVisuals extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;

    private GuiButton disableShift;
    private GuiButton fullBright;
    private GuiButton guiSize;



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

        buttonList.add(this.disableShift = new GuiButton(0, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.visuals.disableShift")));
        buttonList.add(this.fullBright = new GuiButton(1, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.visuals.fullBright")));
        buttonList.add(this.guiSize = new GuiButton(2, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, I18n.format("menu.pvnika.visuals.guiSize")));
        buttonList.add(new GuiButton(10, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.disableShift.displayString = BooleanColor.boolColor(this.config.visualsSettings.disableShift, I18n.format("menu.pvnika.visuals.disableShift"));
        this.fullBright.displayString = BooleanColor.boolColor(this.config.visualsSettings.fullBright, I18n.format("menu.pvnika.visuals.fullBright"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.visuals.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException{
        switch (button.id){
            case 0:
                VisualsUtils.toggleDisableShift();
                break;
            case 1:
                VisualsUtils.toggleFullBright();
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
                break;
        }
        refreshButtons();
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }
}
