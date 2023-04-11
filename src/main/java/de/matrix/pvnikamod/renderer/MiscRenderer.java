package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import scala.tools.nsc.ast.parser.Scanners;

import javax.swing.text.ParagraphView;

public class MiscRenderer {

    public static void toggleFullBright(){
        if (PvnikaMod.getInstance().getConfig().fullBright){
            PvnikaMod.getInstance().mc.gameSettings.gammaSetting = 15.0f;
        } else {
            PvnikaMod.getInstance().mc.gameSettings.gammaSetting = 1.0f;
        }
    }

}
