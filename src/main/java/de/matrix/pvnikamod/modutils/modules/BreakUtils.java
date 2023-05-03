package de.matrix.pvnikamod.modutils.modules;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;

public class BreakUtils {

    private static final Config config = PvnikaMod.getInstance().getConfig();

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
