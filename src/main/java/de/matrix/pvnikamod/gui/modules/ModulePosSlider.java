package de.matrix.pvnikamod.gui.modules;

import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiSlider;

public class ModulePosSlider extends GuiSlider {
    public ModulePosSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr) {
        super(id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr);
    }


    public void mouseReleased(int par1, int par2){
        super.mouseReleased(par1, par2);
        PvnikaMod.getInstance().getConfig().igModules.saveModuleConfig();
        RuntimeSettings.igModSlided = false;
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3){
        //System.out.println(super.mousePressed(par1Minecraft, par2, par3));
        return super.mousePressed(par1Minecraft, par2, par3);
    }

    public void updateSlider(){
        super.updateSlider();
        RuntimeSettings.igModSlided = true;
    }
}
