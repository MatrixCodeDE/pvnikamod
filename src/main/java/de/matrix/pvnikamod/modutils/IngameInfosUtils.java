package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

public class IngameInfosUtils {

    private static final PvnikaMod mod = PvnikaMod.getInstance();
    private static final Config config = mod.getConfig();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void toggleFPS(){
        config.iginfos_showFPS = !config.iginfos_showFPS;
    }

    public static void setFPSPos(int x, int y){
        config.iginfos_PosX[0] = x;
        config.iginfos_PosY[0] = y;
    }

    public static void toggleCoords(){
        config.iginfos_showCoords = !config.iginfos_showCoords;
    }

    public static void setCoordsPos(int x, int y){
        config.iginfos_PosX[1] = x;
        config.iginfos_PosY[1] = y;
    }

}
