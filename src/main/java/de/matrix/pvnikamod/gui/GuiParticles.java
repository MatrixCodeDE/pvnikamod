package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.modutils.ParticlesUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiParticles extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private final GuiScreen lastScreen;
    private GuiSlider multiplierSlider;
    private GuiButton alwaysParticles;
    private GuiButton sharpPartButton;

    public GuiParticles(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.mod;
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.multiplierSlider = new GuiSlider(0, width / 2 - 60, height / 4 + j + i, 120, 20, I18n.format("menu.pvnika.particles.multiplier") + ": ", "", 1.0, 14.0, this.config.particleMultiplier, false, true));
        buttonList.add(this.alwaysParticles = new GuiButton(1, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, BooleanColor.boolColor(this.config.alwaysParticles, I18n.format("menu.pvnika.particles.alwaysParticles"))));
        buttonList.add(this.sharpPartButton = new GuiButton(2, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, BooleanColor.boolColor(this.config.alwaysSharpnessParticles, I18n.format("menu.pvnika.particles.alwaysSharpnessParticles"))));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 4 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.alwaysParticles.displayString = BooleanColor.boolColor(this.config.alwaysParticles, I18n.format("menu.pvnika.particles.alwaysParticles"));
        this.sharpPartButton.displayString = BooleanColor.boolColor(this.config.alwaysSharpnessParticles, I18n.format("menu.pvnika.particles.alwaysSharpnessParticles"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.particles.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        ParticlesUtils particlesUtils = new ParticlesUtils();
        switch(button.id){
            case 0:
                refreshButtons();
                break;
            case 1:
                particlesUtils.toggleAlwaysParticles();
                refreshButtons();
                break;
            case 2:
                particlesUtils.toggleSharpnessParticles();
                refreshButtons();
                break;
            case 10:
                Minecraft.getMinecraft().displayGuiScreen(lastScreen);
        }
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }

    protected void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        super.mouseReleased(mouseX, mouseY, releaseButton);
        this.config.particleMultiplier = this.multiplierSlider.getValueInt();
    }

}
