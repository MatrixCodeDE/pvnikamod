package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiClientOptions extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private GuiButton selectedButton;
    private final GuiScreen lastScreen;

    public GuiClientOptions(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        super.initGui();

        int i = -12;
        int j = 24;

        buttonList.add(new GuiButton(0, width / 2 - 60, height / 4 + j + i, 120, 20, I18n.format("menu.pvnika.general.name")));
        buttonList.add(new GuiButton(1, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.particles.name")));
        buttonList.add(new GuiButton(2, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, I18n.format("menu.pvnika.hitbox.name")));
        buttonList.add(new GuiButton(3, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.name")));
        buttonList.add(new GuiButton(4, width / 2 - 60, height / 4 + 5 * j + i, 120, 20, I18n.format("menu.pvnika.zoom.name")));
        buttonList.add(new GuiButton(5, width / 2 - 60, height / 4 + 6 * j + i, 120, 20, I18n.format("menu.pvnika.movement.name")));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 7 * j + i, 60, 20, I18n.format("gui.back")));
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, "Pvnika Mod V1", width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch(button.id){
            case 0:
                this.mc.displayGuiScreen(new GuiGeneral(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiParticles(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiHitbox(this));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiCrosshair(this));
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiZoom(this));
                break;
            case 5:
                this.mc.displayGuiScreen(new GuiMovement(this));
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
        }
    }

}
