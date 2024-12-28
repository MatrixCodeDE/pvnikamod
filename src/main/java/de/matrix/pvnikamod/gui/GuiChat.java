package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.modutils.ChatUtils;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.io.IOException;

public class GuiChat extends GuiBase {

    private GuiTextField maxLen;
    private GuiButton noSpam;
    private ChatUtils chatUtils = new ChatUtils();

    public GuiChat(GuiScreen lastScreen) {
        super(lastScreen);
    }

    public void initGui(){
        super.initGui();
        int i = -2;
        int j = 24;
        this.maxLen = new GuiTextField(0, fontRendererObj, width / 2 - 50, height / 4 + 0 * j + i, 100, 20);
        buttonList.add(this.noSpam = new GuiButton(1, width / 2 - 50, height / 4 + 1 * j + i, 100, 20, I18n.format("menu.pvnika.chat.nospam")));
        buttonList.add(new GuiButton(2, width / 2 - 50, height / 4 + 2 * j + i, 100, 20, I18n.format("menu.pvnika.autochat.name")));
        setBackButton(height / 4 + 3 * j + i);
        refresh();
    }

    public void refresh() {
        this.maxLen.setText(String.valueOf(this.config.chatSettings.maxLen));
        this.noSpam.displayString = BooleanColor.boolColor(this.config.chatSettings.noSpam, I18n.format("menu.pvnika.chat.nospam"));
        super.refresh();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.maxLen.drawTextBox();
        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.chat.name"), width / 2, 40, ColorUtil.colorToDec(new Color(0, 160, 0)));
    }

    @Override
    public void updateScreen() {
        this.maxLen.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (isNumericKey(typedChar, keyCode)) {
            this.maxLen.textboxKeyTyped(typedChar, keyCode);
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.maxLen.mouseClicked(mouseX, mouseY, mouseButton);
        this.config.chatSettings.maxLen = Integer.parseInt(this.maxLen.getText());
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.config.saveConfig();
    }

    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        int id = button.id;
        switch(id){
            case 1:
                chatUtils.toggleNoSpam();
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAutoChat(this));
                break;
        }

    }

}
