package de.matrix.pvnikamod.config.ingame;

import de.matrix.pvnikamod.config.ingame.modules.CoordModule;
import de.matrix.pvnikamod.config.ingame.modules.FPSModule;

public class IGModules {

    public FPSModule fpsModule;
    public CoordModule coordModule;

    public IGModules(){
        this.fpsModule = new FPSModule();
        this.coordModule = new CoordModule();
    }

}
