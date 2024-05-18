package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiCoords extends GuiIngameModuleScreen {

    private GuiButton showCoords;
    private GuiSlider sliderX;
    private GuiSlider sliderY;

    public GuiCoords(GuiScreen lastScreen){
        super(lastScreen, PvnikaMod.getInstance().getConfig().igModules.coordModule);
    }

    @Override
    public void initGui(){
        int i = -2;
        int j = 24;
        int w = 200;
        super.initGui(i, j, 0, w);
        refreshButtons();
    }

    public void refreshButtons(){
        super.refreshButtons();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.iginfos.coords.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
    }

}
