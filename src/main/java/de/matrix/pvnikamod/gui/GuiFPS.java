package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.BooleanColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiFPS extends GuiScreen {

    private PvnikaMod mod;
    private Config config;
    private Minecraft mc;
    private GuiScreen lastScreen;

    private GuiButton showFPS;
    private GuiSlider sliderX;
    private GuiSlider sliderY;

    public GuiFPS(GuiScreen lastScreen){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.lastScreen = lastScreen;
    }

    @Override
    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.showFPS = new GuiButton(0, width / 2 - 60, height / 4 + 0 * j + i, 120, 20, BooleanColor.enableText(this.config.iginfos_showFPS)));
        buttonList.add(this.sliderX = new GuiSlider(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.all.posx") + ": ", "", 0.0, 100.0, this.config.iginfos_PosX[0], true, true));
        buttonList.add(this.sliderY = new GuiSlider(1, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.all.posy") + ": ", "", 0.0, 100.0, this.config.iginfos_PosY[0], true, true));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 3 * j + i, 60, 20, I18n.format("gui.back")));
    }



}
