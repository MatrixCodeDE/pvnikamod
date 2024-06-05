package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.IngameInfosUtils;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;

public class GuiReach extends GuiIngameModuleScreen{

    private GuiButton showOwn;
    private GuiButton showOther;

    public GuiReach(GuiScreen lastScreen) {
        super(lastScreen, PvnikaMod.getInstance().getConfig().igModules.reachModule);
    }

    @Override
    public void initGui(){
        int i = -2;
        int j = 24;
        int w = 200;
        super.initGui(i, j, 2, w);
        buttonList.add(this.showOwn = new GuiButton(1, width / 2 - (w/2), height / 4 + 3 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.reachModule.showOwn, I18n.format("menu.pvnika.iginfos.reach.showOwn"))));
        buttonList.add(this.showOther = new GuiButton(2, width / 2 - (w/2), height / 4 + 4 * j + i, w, 20, BooleanColor.boolColor(this.config.igModules.reachModule.showOther, I18n.format("menu.pvnika.iginfos.reach.showOther"))));
        refreshButtons();
    }

    public void refreshButtons(){
        super.refreshButtons();
        this.showOwn.displayString = BooleanColor.boolColor(this.config.igModules.reachModule.showOwn, I18n.format("menu.pvnika.iginfos.reach.showOther"));
        this.showOther.displayString = BooleanColor.boolColor(this.config.igModules.reachModule.showOther, I18n.format("menu.pvnika.iginfos.reach.showOther"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.iginfos.reach.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id){
            case 1:
                IngameInfosUtils.toggleOwnReach();
                break;
            case 2:
                IngameInfosUtils.toggleOtherReach();
                break;
        }
        refreshButtons();
    }

}
