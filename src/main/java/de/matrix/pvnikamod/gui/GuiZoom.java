package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.modutils.ZoomUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiZoom extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final GuiScreen lastScreen;
    private GuiButton smoothZoom;
    private GuiButton scrollable;
    private GuiSlider defaultZoom;

    public GuiZoom(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        this.buttonList.add(this.smoothZoom = new GuiButton(0, width / 2 - 50, height / 4 + 0 + i, 100, 20, BooleanColor.boolColor(this.config.zoomSettings.smooth, I18n.format("menu.pvnika.zoom.smooth"))));
        this.buttonList.add(this.scrollable = new GuiButton(1, width / 2 - 50, height / 4 + j + i, 100, 20, BooleanColor.boolColor(this.config.zoomSettings.scrollable, I18n.format("menu.pvnika.zoom.scrollable"))));
        this.buttonList.add(this.defaultZoom = new GuiSlider(2, width / 2 - 50, height / 4 + 2 * j + i, 100, 20, I18n.format("menu.pvnika.zoom.default") + ": ", "x", 1, 50, this.config.zoomSettings.defaultZoom, false, true));
        this.buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 7 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.smoothZoom.displayString = BooleanColor.boolColor(this.config.zoomSettings.smooth, I18n.format("menu.pvnika.zoom.smooth"));
        this.scrollable.displayString = BooleanColor.boolColor(this.config.zoomSettings.scrollable, I18n.format("menu.pvnika.zoom.scrollable"));
        this.defaultZoom.setValue(this.config.zoomSettings.defaultZoom);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.zoom.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button) throws IOException {
        ZoomUtils zoomUtils = PvnikaMod.getInstance().zoomUtils;
        switch(button.id){
            case 0:
                zoomUtils.toggleSmoothZoom();
                break;
            case 1:
                zoomUtils.toggleScrollable();
                break;
            case 10:
                Minecraft.getMinecraft().displayGuiScreen(lastScreen);
                break;
        }
    }

    @Override
    public void onGuiClosed(){
        super.onGuiClosed();
        this.config.saveConfig();
    }

    protected void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        super.mouseReleased(mouseX, mouseY, releaseButton);
        this.config.zoomSettings.defaultZoom = this.defaultZoom.getValue();
        refreshButtons();
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.config.zoomSettings.defaultZoom = this.defaultZoom.getValue();
        refreshButtons();
    }

}
