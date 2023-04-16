package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.BooleanColor;
import de.matrix.pvnikamod.modutils.MovementUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiMovement extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private GuiScreen lastScreen;

    private GuiButton toggleSneak;
    private GuiButton toggleSprint;


    public GuiMovement(GuiScreen lastScreen){
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.toggleSneak = new GuiButton(0, width / 2 - 60, height / 4 + 0 * j + i, 120, 20, BooleanColor.boolColor(this.config.movement_toggleSneak, I18n.format("menu.pvnika.movement.toggleSneak"))));
        buttonList.add(this.toggleSprint = new GuiButton(1, width / 2 - 60, height / 4 + 1 * j + i, 120, 20, BooleanColor.boolColor(this.config.movement_toggleSneak, I18n.format("menu.pvnika.movement.toggleSprint"))));
        buttonList.add(new GuiButton(10, width / 2 - 30, height / 4 + 2 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.toggleSneak.displayString = BooleanColor.boolColor(this.config.movement_toggleSneak, "Toggle Sneak");
        this.toggleSprint.displayString = BooleanColor.boolColor(this.config.movement_toggleSprint, "Toggle Sprint");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.movement.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        MovementUtils movementUtils = new MovementUtils();
        switch(button.id){
            case 0:
                movementUtils.toggleSneak();
                refreshButtons();
                break;
            case 1:
                movementUtils.toggleSprint();
                refreshButtons();
                break;
            case 10:
                this.mc.displayGuiScreen(lastScreen);
                break;
        }
    }
}
