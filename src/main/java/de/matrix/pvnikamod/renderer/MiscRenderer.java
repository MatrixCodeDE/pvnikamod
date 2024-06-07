package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import scala.tools.nsc.ast.parser.Scanners;

import javax.swing.text.ParagraphView;

public class MiscRenderer {

    public static void toggleFullBright(){
        if (PvnikaMod.getInstance().getConfig().visualsSettings.fullBright){
            PvnikaMod.getInstance().mc.gameSettings.gammaSetting = 10.0f;
        } else {
            PvnikaMod.getInstance().mc.gameSettings.gammaSetting = 1.0f;
        }
    }

}
