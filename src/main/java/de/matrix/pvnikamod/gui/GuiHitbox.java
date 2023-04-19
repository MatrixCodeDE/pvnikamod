package de.matrix.pvnikamod.gui;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.BooleanColor;
import de.matrix.pvnikamod.modutils.HitboxUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;

public class GuiHitbox extends GuiScreen {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private final GuiScreen lastScreen;
    private GuiButton advancedButton;
    private GuiSlider groupSlider;
    private GuiButton activated;
    private GuiSlider rSlider;
    private GuiSlider gSlider;
    private GuiSlider bSlider;
    private GuiSlider aSlider;
    private GuiButton chromaButton;
    private GuiSlider speedSlider;
    private GuiButton backButton;
    private final String[] translateGroup = {
            I18n.format("menu.pvnika.hitbox.index.none"),
            I18n.format("menu.pvnika.hitbox.index.player"),
            I18n.format("menu.pvnika.hitbox.index.animals"),
            I18n.format("menu.pvnika.hitbox.index.monsters"),
            I18n.format("menu.pvnika.hitbox.index.items"),
            I18n.format("menu.pvnika.hitbox.index.projectiles"),
            I18n.format("menu.pvnika.hitbox.index.other")
    };

    public GuiHitbox(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void initGui() {
        super.initGui();
        int i = -2;
        int j = 24;
        buttonList.add(this.advancedButton = new GuiButton(0, width / 2 - 60, height / 4 + 0 + i, 120, 20, I18n.format("menu.pvnika.all.advanced")));
        buttonList.add(this.groupSlider = new GuiSlider(1, width / 2 - 60, height / 4 + j + i, 120, 20, I18n.format("menu.pvnika.hitbox.index.name") + ": ", "", 1, 6, this.config.hitbox_group, false, true));
        buttonList.add(this.activated = new GuiButton(2, width / 2 - 60, height / 4 + 2 * j + i, 120, 20, I18n.format("menu.pvnika.all.activated")));
        buttonList.add(this.chromaButton = new GuiButton(3, width / 2 - 60, height / 4 + 3 * j + i, 120, 20, I18n.format("menu.pvnika.all.chroma.name")));
        buttonList.add(this.rSlider = new GuiSlider(4, width / 2 - 60, height / 4 + 4 * j + i, 120, 20, I18n.format("menu.pvnika.all.color.red") + ": ", "", 0, 255, this.config.hitbox_r[this.config.hitbox_group], false, true));
        buttonList.add(this.gSlider = new GuiSlider(5, width / 2 - 60, height / 4 + 5 * j + i, 120, 20, I18n.format("menu.pvnika.all.color.green") + ": ", "", 0, 255, this.config.hitbox_g[this.config.hitbox_group], false, true));
        buttonList.add(this.bSlider = new GuiSlider(6, width / 2 - 60, height / 4 + 6 * j + i, 120, 20, I18n.format("menu.pvnika.all.color.blue") + ": ", "", 0, 255, this.config.hitbox_b[this.config.hitbox_group], false, true));
        buttonList.add(this.aSlider = new GuiSlider(7, width / 2 - 60, height / 4 + 7 * j + i, 120, 20, I18n.format("menu.pvnika.all.color.alpha") + ": ", "", 0, 255, this.config.hitbox_a[this.config.hitbox_group], false, true));
        buttonList.add(this.speedSlider = new GuiSlider(9, width / 2 - 60, height / 4 + 8 * j + i, 120, 20, I18n.format("menu.pvnika.all.chroma.speed") + ": ", "", 1, 10, this.config.hitbox_r[this.config.hitbox_group], false, true));
        buttonList.add(this.backButton = new GuiButton(10, width / 2 - 30, height / 4 + 9 * j + i, 60, 20, I18n.format("gui.back")));
        refreshButtons();
    }

    public void refreshButtons(){
        this.groupSlider.setValue(this.config.hitbox_group);
        this.groupSlider.displayString = translateGroup[this.config.hitbox_group];
        this.rSlider.setValue(this.config.hitbox_r[this.config.hitbox_group]);
        this.gSlider.setValue(this.config.hitbox_g[this.config.hitbox_group]);
        this.bSlider.setValue(this.config.hitbox_b[this.config.hitbox_group]);
        this.aSlider.setValue(this.config.hitbox_a[this.config.hitbox_group]);
        this.rSlider.updateSlider();
        this.gSlider.updateSlider();
        this.bSlider.updateSlider();
        this.aSlider.updateSlider();
        this.speedSlider.setValue(this.config.hitbox_speed[this.config.hitbox_group]);
        this.speedSlider.updateSlider();
        int i = -2;
        int j = 24;
        this.advancedButton.displayString = BooleanColor.boolColor(this.config.hitbox_advanced, I18n.format("menu.pvnika.all.advanced"));
        if (!this.config.hitbox_advanced) {
            this.config.hitbox_group = 0;
            this.groupSlider.visible = false;
        } else {
            if (this.config.hitbox_group == 0) {
                this.config.hitbox_group = this.config.hitbox_last;
            }
            this.groupSlider.setValue(this.config.hitbox_group);
            this.groupSlider.visible = true;
            this.groupSlider.yPosition = height / 4 + j + i;
            i += 24;
        }
        this.activated.yPosition = height / 4 + j + i;
        this.activated.displayString = BooleanColor.boolColor(this.config.hitbox_activated[this.config.hitbox_group], I18n.format("menu.pvnika.all.activated"));
        this.chromaButton.yPosition = height / 4 + 2 * j + i;
        this.chromaButton.displayString = BooleanColor.boolColor(this.config.hitbox_chroma[this.config.hitbox_group], I18n.format("menu.pvnika.all.chroma.name"));
        if (!this.config.hitbox_chroma[this.config.hitbox_group]) {
            this.rSlider.visible = true;
            this.gSlider.visible = true;
            this.bSlider.visible = true;
            this.aSlider.visible = true;
            this.rSlider.setValue(this.config.hitbox_r[this.config.hitbox_group]);
            this.gSlider.setValue(this.config.hitbox_g[this.config.hitbox_group]);
            this.bSlider.setValue(this.config.hitbox_b[this.config.hitbox_group]);
            this.aSlider.setValue(this.config.hitbox_a[this.config.hitbox_group]);
            this.speedSlider.visible = false;
            this.rSlider.yPosition = height / 4 + 3 * j + i;
            this.gSlider.yPosition = height / 4 + 4 * j + i;
            this.bSlider.yPosition = height / 4 + 5 * j + i;
            this.aSlider.yPosition = height / 4 + 6 * j + i;
            this.backButton.yPosition = height / 4 + 7 * j + i;
        } else {
            this.rSlider.visible = false;
            this.gSlider.visible = false;
            this.bSlider.visible = false;
            this.aSlider.visible = false;
            this.speedSlider.visible = true;
            this.speedSlider.setValue(this.config.hitbox_speed[this.config.hitbox_group]);
            this.speedSlider.yPosition = height / 4 + 3 * j + i;
            this.backButton.yPosition = height / 4 + 4 * j + i;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.drawCenteredString(fontRendererObj, I18n.format("menu.pvnika.hitbox.name"), width / 2, 40, ColorUtil.rgbToDec(85, 255, 255));

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        HitboxUtils hitboxUtils = new HitboxUtils();
        switch(button.id){
            case 0:
                hitboxUtils.toggleHitboxAdvanced();
                refreshButtons();
                break;
            case 2:
                hitboxUtils.toggleHitboxActivated();
                refreshButtons();
                break;
            case 3:
                hitboxUtils.toggleHitboxChroma();
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
        this.config.hitbox_r[this.config.hitbox_group] = this.rSlider.getValueInt();
        this.config.hitbox_g[this.config.hitbox_group] = this.gSlider.getValueInt();
        this.config.hitbox_b[this.config.hitbox_group] = this.bSlider.getValueInt();
        this.config.hitbox_a[this.config.hitbox_group] = this.aSlider.getValueInt();
        this.config.hitbox_speed[this.config.hitbox_group] = this.speedSlider.getValueInt();
        if (this.config.hitbox_group != 0) {
            this.config.hitbox_group = this.groupSlider.getValueInt();
            this.config.hitbox_last = this.config.hitbox_group;
        }
        refreshButtons();
    }

}
