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
        int i = -2;
        int j = 24;
        int w = 200;
        super.initGui(i, j, 4, w);
        buttonList.add(this.decimalButton = new GuiButton(3, width / 2 - (w/2), height / 4 + 3 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.breakModule.showDec, I18n.format("menu.pvnika.iginfos.break.dec"))));
        buttonList.add(this.bedButton = new GuiButton(4, width / 2 - (w/2), height / 4 + 4 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.breakModule.bed, I18n.format("tile.obsidian.name"))));
        buttonList.add(this.beaconButton = new GuiButton(5, width / 2 - (w/2), height / 4 + 5 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.breakModule.beacon, I18n.format("tile.beacon.name"))));
        buttonList.add(this.obsidianButton = new GuiButton(6, width / 2 - (w/2), height / 4 + 6 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.breakModule.obsidian, I18n.format("tile.obsidian.name"))));
        refreshButtons();
    }

    public void refreshButtons(){
        super.refreshButtons();
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
        super.actionPerformed(button);
        switch (button.id){
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
        }
        refreshButtons();
    }

}
