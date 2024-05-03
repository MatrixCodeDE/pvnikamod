package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiChat extends GuiBase {

    private final GuiTextField[] texts = new GuiTextField[5];
    private final GuiButton[] controls = new GuiButton[5];


    public GuiChat(GuiScreen lastScreen) {
        super(lastScreen);
    }


    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        for (int cnt = 0; cnt < texts.length; cnt++) {
            this.texts[cnt] = new GuiTextField(0, fontRendererObj, width / 2 - 115, height / 4 + cnt * j + i, 160, 20);
            buttonList.add(this.controls[cnt] = new GuiButton(cnt, width / 2 + 50, height / 4 + cnt * j + i, 60, 20, I18n.format("menu.pvnika.chat.off")));
        }
        setBackButton(height / 4 + texts.length * j + i);
        refreshButtons();
    }

    @Override
    public void refreshButtons() {

        super.refreshButtons();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (GuiTextField tF : texts) {
            tF.drawTextBox();
        }
    }

    @Override
    public void updateScreen() {
        for (GuiTextField tF : texts) {
            tF.updateCursorCounter();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (GuiTextField tF : texts) {
            tF.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiTextField tF : texts) {
            tF.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }





}
