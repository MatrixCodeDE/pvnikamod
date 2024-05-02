package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class GuiChat extends GuiBase {

    private GuiTextField text1;
    private GuiButton ctrl1;
    private GuiTextField text2;
    private GuiButton ctrl2;
    private GuiTextField text3;
    private GuiButton ctrl3;


    public GuiChat(GuiScreen lastScreen) {
        super(lastScreen);
    }


    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        this.text1 = new GuiTextField(0, fontRendererObj, width / 2 - 50, height / 4 + 0 + i, 100, 20);
        buttonList.add(this.ctrl1 = new GuiButton(1, width / 2 - 50, height / 4 + j + i, 100, 20, "Ctrl"));
    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        text1.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }


}
