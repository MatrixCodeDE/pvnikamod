package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.IngameInfosUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

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
        buttonList.add(this.sliderX = new GuiSlider(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.all.posx") + ": ", "", 0, this.mc.displayWidth, RenderManager.translateXFromConfig(this.config.iginfos_PosX[0]), false, true));
        buttonList.add(this.sliderY = new GuiSlider(2, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.all.posy") + ": ", "", 0, this.mc.displayHeight, RenderManager.translateYFromConfig(this.config.iginfos_PosY[0]), false, true));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 3 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.showFPS.displayString = BooleanColor.enableText(this.config.iginfos_showFPS);
        this.sliderX.maxValue = this.mc.displayWidth;
        this.sliderY.maxValue = this.mc.displayHeight;
        this.sliderX.setValue(RenderManager.translateXFromConfig(this.config.iginfos_PosX[0]));
        this.sliderY.setValue(RenderManager.translateYFromConfig(this.config.iginfos_PosY[0]));
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
                IngameInfosUtils.toggleFPS();
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
        }
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }

    protected void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        super.mouseReleased(mouseX, mouseY, releaseButton);
        this.config.iginfos_PosX[0] = RenderManager.translateXToConfig(this.sliderX.getValueInt());
        this.config.iginfos_PosY[0] = RenderManager.translateYToConfig(this.sliderY.getValueInt());
        refreshButtons();
    }

}
