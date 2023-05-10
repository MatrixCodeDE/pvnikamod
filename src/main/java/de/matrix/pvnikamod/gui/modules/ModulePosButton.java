package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.gui.GuiButton;

public class ModulePosButton extends GuiButton {

    public ModulePosButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public void mouseReleased(int par1, int par2){
        super.mouseReleased(par1, par2);
        RuntimeSettings.igModSlided = false;
    }
}
