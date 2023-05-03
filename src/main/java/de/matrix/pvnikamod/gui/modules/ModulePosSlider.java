package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.config.ingame.IGModules;
import de.matrix.pvnikamod.config.ingame.modules.AModule;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiSlider;

public class ModulePosSlider extends GuiSlider {

    private AModule aModule;

    public ModulePosSlider(AModule aModule , int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
        this.aModule = aModule;
    }


    public void mouseReleased(int par1, int par2){
        super.mouseReleased(par1, par2);
        RuntimeSettings.igModSlided = false;
        PvnikaMod.getInstance().getConfig().igModules.saveModuleConfig();
    }

    public void updateSlider(){
        super.updateSlider();
        RuntimeSettings.igModSlided = true;
        PvnikaMod.getInstance().getConfig().igModules.saveModuleConfig();
    }
}
