package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.config.ingame.modules.AModule;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.modules.ModuleUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import de.matrix.pvnikamod.utils.BooleanColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;
import java.sql.SQLOutput;

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
        RuntimeSettings.igModSlided = false;
    }

    public void initGui(int i, int j, int k, int w){
        super.initGui();
        buttonList.add(this.enabled = new GuiButton(0, width / 2 - (w/2), height / 4 + 0 * j + i, w, 20, BooleanColor.enableText(this.aModule.enabled)));

        buttonList.add(this.sliderX = new ModulePosSlider(this.aModule, 1, width / 2 - (w/2), height / 4 + 1 * j + i, w, 20, I18n.format("menu.pvnika.all.posx") + ": ", "", 0, this.mc.displayWidth, RenderManager.translateXFromConfig(this.aModule.posX), false, true));
        buttonList.add(new ModulePosButton(100, (width / 2 - (w/2)) - 48, height / 4 + 1 * j + i, 20, 20, "-"));
        buttonList.add(new ModulePosButton(101, (width / 2 - (w/2)) - 24, height / 4 + 1 * j + i, 20, 20, "--"));
        buttonList.add(new ModulePosButton(102, (width / 2 + (w/2)) + 4, height / 4 + 1 * j + i, 20, 20, "++"));
        buttonList.add(new ModulePosButton(103, (width / 2 + (w/2)) + 28, height / 4 + 1 * j + i, 20, 20, "+"));

        buttonList.add(this.sliderY = new ModulePosSlider(this.aModule, 2, width / 2 - (w/2), height / 4 + 2 * j + i, w, 20, I18n.format("menu.pvnika.all.posy") + ": ", "", 0, this.mc.displayHeight, RenderManager.translateYFromConfig(this.aModule.posY), false, true));
        buttonList.add(new ModulePosButton(110, (width / 2 - (w/2)) - 48, height / 4 + 2 * j + i, 20, 20, "-"));
        buttonList.add(new ModulePosButton(111, (width / 2 - (w/2)) - 24, height / 4 + 2 * j + i, 20, 20, "--"));
        buttonList.add(new ModulePosButton(112, (width / 2 + (w/2)) + 4, height / 4 + 2 * j + i, 20, 20, "++"));
        buttonList.add(new ModulePosButton(113, (width / 2 + (w/2)) + 28, height / 4 + 2 * j + i, 20, 20, "+"));

        buttonList.add(new GuiButton(10, width / 2 - (w/2), height / 4 + (3 + k) * j + i, w, 20, I18n.format("gui.back")));
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
        RuntimeSettings.igModSlided = false;
    }

    public void actionPerformed(GuiButton button) throws IOException{
        System.out.println(this.aModule.posX + " " + this.aModule.posY);
        System.out.println(this.aModule.posX + RenderManager.translateXToConfig(2) + " " + (this.aModule.posY + RenderManager.translateYToConfig(2)));
        int slX = RenderManager.translateXFromConfig(this.aModule.posX);
        int slY = RenderManager.translateYFromConfig(this.aModule.posY);
        System.out.println("B: " + slX + " " + slY);
        boolean update = false;
        switch (button.id){
            case 0:
                ModuleUtils.toggleEnabled(aModule);
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
                break;

            case 100:
                slX -= 1;
                break;
            case 101:
                slX -= 10;
                break;
            case 102:
                slX += 10;
                break;
            case 103:
                slX += 1;
                break;

            case 110:
                slY -= 1;
                break;
            case 111:
                slY -= 10;
                break;
            case 112:
                slY += 10;
                break;
            case 113:
                slY += 1;
                break;
        }
        System.out.println("A1: " + slX + " " + slY);
        slX = MathHelper.clamp_int(slX, 0, this.mc.displayWidth);
        slY = MathHelper.clamp_int(slY, 0, this.mc.displayHeight);
        System.out.println("A2: " + slX + " " + slY);
        if (button.id >= 100 && button.id <= 103) {
            this.sliderX.setValue(slX);
        }
        if (button.id >= 110 && button.id <= 113) {
            this.sliderY.setValue(slY);
        }
        System.out.println("A3: " + slX + " " + slY);
        this.sliderX.updateSlider();
        this.sliderY.updateSlider();
        this.aModule.posX = RenderManager.translateXToConfig(this.sliderX.getValueInt());
        this.aModule.posY = RenderManager.translateYToConfig(this.sliderY.getValueInt());
        System.out.println("A4: " + slX + " " + slY + "\n");
        refreshButtons();
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (RuntimeSettings.igModSlided) {
            this.aModule.posX = RenderManager.translateXToConfig(this.sliderX.getValueInt());
            this.aModule.posY = RenderManager.translateYToConfig(this.sliderY.getValueInt());
        }
    }
}
