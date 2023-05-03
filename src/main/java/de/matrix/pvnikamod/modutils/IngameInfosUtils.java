package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

public class IngameInfosUtils {

    private static final PvnikaMod mod = PvnikaMod.getInstance();
    private static final Config config = mod.getConfig();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void toggleFPS(){
        config.igModules.fpsModule.enabled = !config.igModules.fpsModule.enabled;
    }

    public static void toggleCoords(){
        config.igModules.coordModule.enabled = !config.igModules.coordModule.enabled;
    }

    public static void toggleBreak(){
        config.igModules.breakModule.enabled = !config.igModules.breakModule.enabled;
    }

    public static void toggleDecimal(){
        config.igModules.breakModule.showDec = !config.igModules.breakModule.showDec;
    }

    public static void toggleBreakBed(){
        config.igModules.breakModule.bed = !config.igModules.breakModule.bed;
    }

    public static void toggleBreakBeacon(){
        config.igModules.breakModule.beacon = !config.igModules.breakModule.beacon;
    }

    public static void toggleBreakObsidian(){
        config.igModules.breakModule.obsidian = !config.igModules.breakModule.obsidian;
    }

}
