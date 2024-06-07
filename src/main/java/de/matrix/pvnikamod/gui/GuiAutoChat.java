package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.modutils.ChatUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.io.IOException;

public class GuiAutoChat extends GuiBase {

    private ChatUtils chatUtils = new ChatUtils();
    private final GuiTextField[] texts = new GuiTextField[5];
    private final GuiButton[] modes = new GuiButton[5];


    public GuiAutoChat(GuiScreen lastScreen) {
        super(lastScreen);
    }


    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        for (int cnt = 0; cnt < texts.length; cnt++) {
            this.texts[cnt] = new GuiTextField(0, fontRendererObj, width / 2 - 115, height / 4 + cnt * j + i, 160, 20);
            buttonList.add(this.modes[cnt] = new GuiButton(cnt, width / 2 + 50, height / 4 + cnt * j + i, 60, 20, I18n.format("menu.pvnika.autochat.off")));
        }
        setBackButton(height / 4 + texts.length * j + i);
        refresh();
    }

    public void refresh() {
        for (int cnt = 0; cnt < texts.length; cnt++) {
            String text = this.config.chatSettings.autoTexts[cnt];
            int mode = this.config.chatSettings.autoModes[cnt];
            this.modes[cnt].displayString = chatUtils.translateMode[mode];
            this.texts[cnt].setText(this.config.chatSettings.autoTexts[cnt]);
            this.texts[cnt].setEnabled(mode != 0);
        }
        super.refresh();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (GuiTextField tF : texts) {
            tF.drawTextBox();
        }
        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.autochat.name"), width / 2, 40, ColorUtil.colorToDec(new Color(0, 160, 0)));
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
        for (int cnt = 0; cnt < texts.length; cnt++) {
            texts[cnt].mouseClicked(mouseX, mouseY, mouseButton);
            this.config.chatSettings.autoTexts[cnt] = texts[cnt].getText();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.config.saveConfig();
    }

    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        int id = button.id;
        if (id != 10){
            if (0 <= id && id <= 4){
                chatUtils.toggleAutoMode(id);
            }
            refresh();
        }
    }





}
