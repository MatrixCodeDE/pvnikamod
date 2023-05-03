package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.ingame.modules.AModule;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.modules.ModuleUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import de.matrix.pvnikamod.utils.BooleanColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public abstract class GuiIngameModuleScreen extends GuiScreen {

    public PvnikaMod mod;
    public Config config;
    public Minecraft mc;
    public GuiScreen lastScreen;

    public GuiButton enabled;
    public GuiSlider sliderX;
    public GuiSlider sliderY;
    public AModule aModule;


    public GuiIngameModuleScreen(GuiScreen lastScreen, AModule aModule){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.lastScreen = lastScreen;
        this.aModule = aModule;
    }

    public void initGui(int i, int j){
        super.initGui();
        buttonList.add(this.enabled = new GuiButton(0, width / 2 - 60, height / 4 + 0 * j + i, 120, 20, BooleanColor.enableText(this.aModule.enabled)));
        buttonList.add(this.sliderX = new ModulePosSlider(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.all.posx") + ": ", "", 0, this.mc.displayWidth, RenderManager.translateXFromConfig(this.aModule.posX), false, true));
        buttonList.add(this.sliderY = new ModulePosSlider(2, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.all.posy") + ": ", "", 0, this.mc.displayHeight, RenderManager.translateYFromConfig(this.aModule.posY), false, true));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 3 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.enabled.displayString = BooleanColor.enableText(this.aModule.enabled);
        this.sliderX.maxValue = this.mc.displayWidth;
        this.sliderY.maxValue = this.mc.displayHeight;
        this.sliderX.setValue(RenderManager.translateXFromConfig(this.aModule.posX));
        this.sliderY.setValue(RenderManager.translateYFromConfig(this.aModule.posY));
    }

    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.igModules.saveModuleConfig();
    }

    public void actionPerformed(GuiButton button) throws IOException{
        switch (button.id){
            case 0:
                ModuleUtils.toggleEnabled(aModule);
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
                break;
        }
    }
}
