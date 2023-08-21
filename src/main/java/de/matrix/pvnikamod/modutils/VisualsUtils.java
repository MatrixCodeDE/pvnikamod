package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.IConfigElement;

public class VisualsUtils {

    private static final PvnikaMod mod = PvnikaMod.getInstance();
    private static final Config config = mod.getConfig();
    private static final Minecraft mc = Minecraft.getMinecraft();


    public static void toggleCustomMenu(){
        config.visualsSettings.customMenu = !config.visualsSettings.customMenu;
    }

    public static void toggleDisableShift(){
        config.visualsSettings.disableShift = !config.visualsSettings.disableShift;
    }

    public static void togglePingOnTab(){
        config.visualsSettings.pingOnTab = !config.visualsSettings.pingOnTab;
    }

    public static void toggleFullBright(){
        config.visualsSettings.fullBright = !config.visualsSettings.fullBright;
    }

}
