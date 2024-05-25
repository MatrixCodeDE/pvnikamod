package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.ingame.modules.AModule;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.modules.CPSUtils;
import de.matrix.pvnikamod.modutils.modules.ClockUtils;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiCPS extends GuiIngameModuleScreen {

    private GuiButton splitButton;

    public GuiCPS(GuiScreen lastScreen) {
        super(lastScreen, PvnikaMod.getInstance().getConfig().igModules.cpsModule);
    }

    @Override
    public void initGui(){
        int i = -2;
        int j = 24;
        int w = 200;
        super.initGui(i, j, 1, w);
        buttonList.add(this.splitButton = new GuiButton(3, width / 2 - (w/2), height / 4 + 3 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.cpsModule.split, I18n.format("menu.pvnika.iginfos.cps.split"))));
        refreshButtons();
    }

    @Override
    public void refreshButtons(){
        super.refreshButtons();
        this.splitButton.displayString = BooleanColor.boolColor(this.config.igModules.cpsModule.split, I18n.format("menu.pvnika.iginfos.cps.split"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.iginfos.cps.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id){
            case 3:
                CPSUtils.toggleSplit();
                break;
        }
        refreshButtons();
    }

}
