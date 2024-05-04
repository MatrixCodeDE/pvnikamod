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

public class GuiGeneral extends GuiBase {

    private GuiButton customMenu;
    private GuiButton logoInMenu;
    private GuiButton pingOnTab;
    private GuiButton ownNameTag;
    private GuiButton confirmDisconnect;
    private final GeneralUtils generalUtils = new GeneralUtils();

    public GuiGeneral(GuiScreen lastScreen) {
        super(lastScreen);
    }

    public void initGui() {
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.customMenu = new GuiButton(0, width / 2 - 50, height / 4 + 0 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.customMenu"))));
        buttonList.add(this.logoInMenu = new GuiButton(1, width / 2 - 50, height / 4 + 1 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.logoInMenu, I18n.format("menu.pvnika.general.logoInMenu"))));
        buttonList.add(this.pingOnTab = new GuiButton(2, width / 2 - 50, height / 4 + 2 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.pingOnTab, I18n.format("menu.pvnika.general.pingOnTab"))));
        buttonList.add(this.ownNameTag = new GuiButton(3, width / 2 - 50, height / 4 + 3 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"))));
        buttonList.add(this.confirmDisconnect = new GuiButton(2, width / 2 - 50, height / 4 + 4 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"))));
        setBackButton(height / 4 + 5 * j + i);
        refresh();
    }

    public void refresh(){
        this.customMenu.displayString = BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.customMenu"));
        this.logoInMenu.displayString = BooleanColor.boolColor(this.config.generalSettings.logoInMenu, I18n.format("menu.pvnika.general.logoInMenu"));
        this.pingOnTab.displayString = BooleanColor.boolColor(this.config.generalSettings.pingOnTab, I18n.format("menu.pvnika.general.pingOnTab"));
        this.ownNameTag.displayString = BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"));
        this.confirmDisconnect.displayString = BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"));
    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.general.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id != 10) {
            switch (button.id) {
                case 0:
                    generalUtils.toggleCustomMenu();
                    break;
                case 1:
                    generalUtils.toggleLogoInMenu();
                    break;
                case 2:
                    generalUtils.togglePingOnTab();
                    break;
                case 3:
                    generalUtils.toggleOwnNameTag();
                    break;
                case 4:
                    generalUtils.toggleConfirmDisconnect();
                    break;
                case 10:
                    Minecraft.getMinecraft().displayGuiScreen(lastScreen);
                    break;
            }
            refresh();
        }
    }

}
