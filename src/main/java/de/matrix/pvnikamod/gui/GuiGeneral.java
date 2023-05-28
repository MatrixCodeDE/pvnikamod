package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.modutils.GeneralUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiGeneral extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;
    private GuiButton customMenu;
    private GuiButton logoInMenu;
    private GuiButton ownNameTag;
    private GuiButton pingOnTab;
    private GuiButton confirmDisconnect;
    private GuiButton fullBright;

    public GuiGeneral(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.customMenu = new GuiButton(0, width / 2 - 50, height / 4 + 0 + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.customMenu"))));
        buttonList.add(this.logoInMenu = new GuiButton(1, width / 2 - 50, height / 4 + j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.logoInMenu"))));
        buttonList.add(this.ownNameTag = new GuiButton(2, width / 2 - 50, height / 4 + 2 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"))));
        buttonList.add(this.pingOnTab = new GuiButton(3, width / 2 - 50, height / 4 + 3 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.pingOnTab, I18n.format("menu.pvnika.general.pingOnTab"))));
        buttonList.add(this.confirmDisconnect = new GuiButton(4, width / 2 - 50, height / 4 + 4 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"))));
        buttonList.add(this.fullBright = new GuiButton(5, width / 2 - 50, height / 4 + 5 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.fullBright, I18n.format("menu.pvnika.general.fullBright"))));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 6 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.customMenu.displayString = BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.customMenu"));
        this.logoInMenu.displayString = BooleanColor.boolColor(this.config.generalSettings.logoInMenu, I18n.format("menu.pvnika.general.logoInMenu"));
        this.ownNameTag.displayString = BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"));
        this.pingOnTab.displayString = BooleanColor.boolColor(this.config.generalSettings.pingOnTab, I18n.format("menu.pvnika.general.pingOnTab"));
        this.confirmDisconnect.displayString = BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"));
        this.fullBright.displayString = BooleanColor.boolColor(this.config.generalSettings.fullBright, I18n.format("menu.pvnika.general.fullBright"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.general.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        GeneralUtils generalUtils = new GeneralUtils();
        switch(button.id){
            case 0:
                generalUtils.toggleCustomMenu();
                refreshButtons();
                break;
            case 1:
                generalUtils.toggleLogoInMenu();
                refreshButtons();
                break;
            case 2:
                generalUtils.toggleOwnNameTag();
                refreshButtons();
                break;
            case 3:
                generalUtils.togglePingOnTab();
                refreshButtons();
                break;
            case 4:
                generalUtils.toggleConfirmDisconnect();
                refreshButtons();
                break;
            case 5:
                generalUtils.toggleFullBright();
                refreshButtons();
                break;
            case 10:
                Minecraft.getMinecraft().displayGuiScreen(lastScreen);
                break;
        }
    }

}
