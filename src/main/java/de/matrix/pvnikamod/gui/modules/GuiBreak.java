package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.gui.modules.GuiIngameModuleScreen;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.IngameInfosUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiBreak extends GuiIngameModuleScreen {

    private GuiButton decimalButton;
    private GuiButton bedButton;
    private GuiButton beaconButton;
    private GuiButton obsidianButton;

    public GuiBreak(GuiScreen lastScreen){
        super(lastScreen, PvnikaMod.getInstance().getConfig().igModules.breakModule);
    }

    @Override
    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.enabled = new GuiButton(0, width / 2 - 60, height / 4 + 0 * j + i, 120, 20, BooleanColor.enableText(this.config.igModules.breakModule.enabled)));
        buttonList.add(this.sliderX = new GuiSlider(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, I18n.format("menu.pvnika.all.posx") + ": ", "", 0, this.mc.displayWidth, RenderManager.translateXFromConfig(this.config.igModules.breakModule.posX), false, true));
        buttonList.add(this.sliderY = new GuiSlider(2, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.all.posy") + ": ", "", 0, this.mc.displayHeight, RenderManager.translateYFromConfig(this.config.igModules.breakModule.posY), false, true));
        buttonList.add(this.decimalButton = new GuiButton(3, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, BooleanColor.boolColor(this.config.igModules.breakModule.showDec, I18n.format("menu.pvnika.iginfos.break.dec"))));
        buttonList.add(this.bedButton = new GuiButton(4, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, BooleanColor.boolColor(this.config.igModules.breakModule.bed, I18n.format("tile.obsidian.name"))));
        buttonList.add(this.beaconButton = new GuiButton(5, width / 2 - 60, height / 4 + 5 * j + i, 120, 20, BooleanColor.boolColor(this.config.igModules.breakModule.beacon, I18n.format("tile.beacon.name"))));
        buttonList.add(this.obsidianButton = new GuiButton(6, width / 2 - 60, height / 4 + 6 * j + i, 120, 20, BooleanColor.boolColor(this.config.igModules.breakModule.obsidian, I18n.format("tile.obsidian.name"))));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 7 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.enabled.displayString = BooleanColor.enableText(this.config.igModules.breakModule.enabled);
        this.sliderX.maxValue = this.mc.displayWidth;
        this.sliderY.maxValue = this.mc.displayHeight;
        this.sliderX.setValue(RenderManager.translateXFromConfig(this.config.igModules.breakModule.posX));
        this.sliderY.setValue(RenderManager.translateYFromConfig(this.config.igModules.breakModule.posY));
        this.decimalButton.displayString = BooleanColor.boolColor(this.config.igModules.breakModule.showDec, I18n.format("menu.pvnika.iginfos.break.dec"));
        this.bedButton.displayString = BooleanColor.boolColor(this.config.igModules.breakModule.bed, I18n.format("tile.bed.name"));
        this.beaconButton.displayString = BooleanColor.boolColor(this.config.igModules.breakModule.beacon, I18n.format("tile.beacon.name"));
        this.obsidianButton.displayString = BooleanColor.boolColor(this.config.igModules.breakModule.obsidian, I18n.format("tile.obsidian.name"));

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
                IngameInfosUtils.toggleBreak();
                break;
            case 3:
                IngameInfosUtils.toggleDecimal();
                break;
            case 4:
                IngameInfosUtils.toggleBreakBed();
                break;
            case 5:
                IngameInfosUtils.toggleBreakBeacon();
                break;
            case 6:
                IngameInfosUtils.toggleBreakObsidian();
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        super.mouseReleased(mouseX, mouseY, releaseButton);
        this.config.igModules.breakModule.posX = RenderManager.translateXToConfig(this.sliderX.getValueInt());
        this.config.igModules.breakModule.posY = RenderManager.translateYToConfig(this.sliderY.getValueInt());
        refreshButtons();
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.config.igModules.breakModule.posX = RenderManager.translateXToConfig(this.sliderX.getValueInt());
        this.config.igModules.breakModule.posY = RenderManager.translateYToConfig(this.sliderY.getValueInt());
        refreshButtons();
    }

}
