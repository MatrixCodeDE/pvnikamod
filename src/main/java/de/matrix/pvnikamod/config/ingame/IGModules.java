package de.matrix.pvnikamod.config.ingame;

import de.matrix.pvnikamod.config.ingame.modules.BreakModule;
import de.matrix.pvnikamod.config.ingame.modules.CoordModule;
import de.matrix.pvnikamod.config.ingame.modules.FPSModule;
import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class IGModules {

    public Configuration moduleConfig;

    public FPSModule fpsModule;
    public CoordModule coordModule;
    public BreakModule breakModule;


    public IGModules(File modulesFile){
        this.moduleConfig = new Configuration(modulesFile);
        this.fpsModule = new FPSModule();
        this.coordModule = new CoordModule();
        this.breakModule = new BreakModule();
    }

    public void loadModuleConfig(){
        this.moduleConfig.load();

        this.fpsModule.enabled = this.moduleConfig.get("fps", "enabled", false).getBoolean();
        this.fpsModule.posX = this.moduleConfig.get("fps", "posX", 0.0).getDouble();
        this.fpsModule.posY = this.moduleConfig.get("fps", "posY", 0.0).getDouble();

        this.coordModule.enabled = this.moduleConfig.get("coordinates", "enabled", false).getBoolean();
        this.coordModule.posX = this.moduleConfig.get("coordinates", "posX", 0.0).getDouble();
        this.coordModule.posY = this.moduleConfig.get("coordinates", "posY", 0.0).getDouble();

        this.breakModule.enabled = this.moduleConfig.get("break", "enabled", false).getBoolean();
        this.breakModule.posX = this.moduleConfig.get("break", "posX", 0.0).getDouble();
        this.breakModule.posY = this.moduleConfig.get("break", "posY", 0.0).getDouble();
        this.breakModule.showDec = this.moduleConfig.get("break", "showDec", false).getBoolean();
        this.breakModule.bed = this.moduleConfig.get("break", "bed", false).getBoolean();
        this.breakModule.beacon = this.moduleConfig.get("break", "beacon", false).getBoolean();
        this.breakModule.obsidian = this.moduleConfig.get("break", "obsidian", false).getBoolean();

        this.moduleConfig.save();
    }

    public void saveModuleConfig(){

        this.moduleConfig.get("FPS", "enabled", false).set(this.fpsModule.enabled);
        this.moduleConfig.get("FPS", "posX", 0.0).set(this.fpsModule.posX);
        this.moduleConfig.get("FPS", "posY", 0.0).set(this.fpsModule.posY);

        this.moduleConfig.get("Coordinates", "enabled", false).set(this.coordModule.enabled);
        this.moduleConfig.get("Coordinates", "posX", 0.0).set(this.coordModule.posX);
        this.moduleConfig.get("Coordinates", "posY", 0.0).set(this.coordModule.posY);

        this.moduleConfig.get("break", "enabled", false).set(this.breakModule.enabled);
        this.moduleConfig.get("break", "posX", 0.0).set(this.breakModule.posX);
        this.moduleConfig.get("break", "posY", 0.0).set(this.breakModule.posY);
        this.moduleConfig.get("break", "showDec", false).set(this.breakModule.showDec);
        this.moduleConfig.get("break", "bed", false).set(this.breakModule.bed);
        this.moduleConfig.get("break", "beacon", false).set(this.breakModule.beacon);
        this.moduleConfig.get("break", "obsidian", false).set(this.breakModule.obsidian);


        this.moduleConfig.save();
    }

}
