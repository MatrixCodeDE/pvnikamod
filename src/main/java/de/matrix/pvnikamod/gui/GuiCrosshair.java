package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.CrosshairRenderer;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.modutils.CrosshairUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiCrosshair extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;
    private final CrosshairUtils crosshairUtils;
    private final CrosshairRenderer crosshairRenderer;
    private GuiButton toggleButton;
    private GuiSlider sizeSlider;
    private GuiSlider gapSlider;
    private GuiSlider heightSlider;
    private GuiSlider widthSlider;
    private GuiSlider thickSlider;
    private GuiSlider outSlider;

    public GuiCrosshair(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.crosshairUtils = new CrosshairUtils();
        this.crosshairRenderer = new CrosshairRenderer();
    }

    public void initGui() {
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.toggleButton = new GuiButton(0, width / 2 - 60, height / 4 + 0 + i, 120, 20, BooleanColor.boolColor(this.config.crosshair_custom, I18n.format("menu.pvnika.all.custom"))));
        buttonList.add(this.sizeSlider = new GuiSlider(1, width / 2 - 60, height / 4 + j + i, 120, 20, I18n.format("menu.pvnika.crosshair.size") + ": ", "", 1, 10, this.config.crosshair_size, false, true));
        buttonList.add(this.gapSlider = new GuiSlider(2, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.gap") + ": ", "", 0, 10, this.config.crosshair_gap, false, true));
        buttonList.add(this.heightSlider = new GuiSlider(3, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.height") + ": ", "", 1, 10, this.config.crosshair_height, false, true));
        buttonList.add(this.widthSlider = new GuiSlider(4, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.width") + ": ", "", 1, 10, this.config.crosshair_width, false, true));
        buttonList.add(this.thickSlider = new GuiSlider(5, width / 2 - 60, height / 4 + 5 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.thickness") + ": ", "", 1, 10, this.config.crosshair_thickness, false, true));
        buttonList.add(this.outSlider = new GuiSlider(6, width / 2 - 60, height / 4 + 6 * j + i, 120, 20, I18n.format("menu.pvnika.crosshair.outthick") + ": ", "", 1, 10, this.config.crosshair_outthick, false, true));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 7 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons() {
        this.toggleButton.displayString = BooleanColor.boolColor(this.config.crosshair_custom, I18n.format("menu.pvnika.all.custom"));
        this.sizeSlider.setValue(this.config.crosshair_size);
        this.gapSlider.setValue(this.config.crosshair_gap);
        this.heightSlider.setValue(this.config.crosshair_height);
        this.widthSlider.setValue(this.config.crosshair_width);
        this.thickSlider.setValue(this.config.crosshair_thickness);
        this.outSlider.setValue(this.config.crosshair_outthick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.crosshair.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));
        ScaledResolution resolution = new ScaledResolution(this.mc);
        int sw = resolution.getScaledWidth();
        int sh = resolution.getScaledHeight();
        int x = sw / 4;
        int y = sh / 2;

        crosshairRenderer.render(x, y, 20.0f);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch(button.id){
            case 0:
                this.crosshairUtils.toggleCrosshair();
                refreshButtons();
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
        }
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }

    protected void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        super.mouseReleased(mouseX, mouseY, releaseButton);
        this.config.crosshair_size = this.sizeSlider.getValueInt();
        this.config.crosshair_gap = this.gapSlider.getValueInt();
        this.config.crosshair_height = this.heightSlider.getValueInt();
        this.config.crosshair_width = this.widthSlider.getValueInt();
        this.config.crosshair_thickness = this.thickSlider.getValueInt();
        this.config.crosshair_outthick = this.outSlider.getValueInt();
        refreshButtons();
    }

}
