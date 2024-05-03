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
    private GuiButton logoInMenu;
    private GuiButton ownNameTag;
    private GuiButton confirmDisconnect;

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
        buttonList.add(this.logoInMenu = new GuiButton(0, width / 2 - 50, height / 4 + 0 *j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.customMenu, I18n.format("menu.pvnika.general.logoInMenu"))));
        buttonList.add(this.ownNameTag = new GuiButton(1, width / 2 - 50, height / 4 + 1 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"))));
        buttonList.add(this.confirmDisconnect = new GuiButton(2, width / 2 - 50, height / 4 + 2 * j + i, 100, 20, BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"))));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 3 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.logoInMenu.displayString = BooleanColor.boolColor(this.config.generalSettings.logoInMenu, I18n.format("menu.pvnika.general.logoInMenu"));
        this.ownNameTag.displayString = BooleanColor.boolColor(this.config.generalSettings.ownNameTag, I18n.format("menu.pvnika.general.ownNameTag"));
        this.confirmDisconnect.displayString = BooleanColor.boolColor(this.config.generalSettings.confirmDisconnect, I18n.format("menu.pvnika.general.confirmDisconnect"));
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
                generalUtils.toggleLogoInMenu();
                refreshButtons();
                break;
            case 1:
                generalUtils.toggleOwnNameTag();
                refreshButtons();
                break;
            case 2:
                generalUtils.toggleConfirmDisconnect();
                refreshButtons();
                break;
            case 10:
                Minecraft.getMinecraft().displayGuiScreen(lastScreen);
                break;
        }
    }

}
