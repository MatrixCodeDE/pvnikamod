package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraftforge.fml.client.GuiModList;

import java.io.IOException;

public class GuiCustomIngameMenu extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private GuiButton selectedButton;
    private final GuiScreen lastScreen;

    private boolean confirm = false;
    private final boolean confirmDisconnect;

    public GuiCustomIngameMenu(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.confirmDisconnect = this.config.confirmDisconnect;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        int i = -16;
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, 98, 20, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu")));
        this.buttonList.add(new GuiButton(20, this.width / 2 + 2, this.height / 4 + 24 + i, I18n.format("menu.pvnika.name")));
        if (!this.mc.isIntegratedServerRunning()) {
            this.buttonList.get(1).displayString = I18n.format("menu.disconnect");
            this.buttonList.get(1).xPosition = this.width / 2 + 2;
            this.buttonList.get(1).yPosition = this.height / 4 + 24 + i;
            this.buttonList.get(1).width = 98;

            this.buttonList.get(2).xPosition = this.width / 2 - 100;
            this.buttonList.get(2).yPosition = this.height / 4 + 120 + i;
        } else {
            this.buttonList.get(2).width = 98;
        }


        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(12, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("fml.menu.modoptions")));
        GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 - 100, this.height / 4 + 72 + i, 200, 20, I18n.format("menu.shareToLan")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements")));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats")));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    public void refreshButtons(){
        if (!confirm) {
            this.buttonList.get(1).displayString = "§c" + I18n.format("menu.disconnect");
        } else {
            this.buttonList.get(1).displayString = "§c" + I18n.format("menu.pvnika.general.confirmDisconnect");
        }
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id)
        {

            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                confirm = false;
                break;
            case 1:
                if (!confirm && confirmDisconnect && !this.mc.isIntegratedServerRunning()){
                    confirm = true;
                    refreshButtons();
                    break;
                }
                confirm = false;
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.isConnectedToRealms();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;
            case 4:
                confirm = false;
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 5:
                confirm = false;
                if (this.mc.thePlayer != null)
                    this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            case 6:
                confirm = false;
                if (this.mc.thePlayer != null)
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            case 7:
                confirm = false;
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 12:
                confirm = false;
                this.mc.displayGuiScreen(new GuiModList(this));
                break;
            case 20:
                confirm = false;
                this.mc.displayGuiScreen(new GuiClientOptions(this));
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width / 2, 40, ColorUtil.rgbToDec(255, 255, 255));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
