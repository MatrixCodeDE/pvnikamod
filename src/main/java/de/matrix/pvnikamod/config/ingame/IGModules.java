package de.matrix.pvnikamod.config.ingame;

import de.matrix.pvnikamod.config.ingame.modules.*;
import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class IGModules {

    public Configuration moduleConfig;

    public FPSModule fpsModule;
    public CoordModule coordModule;
    public BreakModule breakModule;
    public MLGModule mlgModule;


    public IGModules(File modulesFile){
        this.moduleConfig = new Configuration(modulesFile);
        this.fpsModule = new FPSModule();
        this.coordModule = new CoordModule();
        this.breakModule = new BreakModule();
        this.mlgModule = new MLGModule();
    }

    public void loadModuleConfig(){
        this.moduleConfig.load();

        this.loadDefaults(this.fpsModule, "fps");

        this.loadDefaults(this.coordModule, "coordinates");

        this.loadDefaults(this.breakModule, "break");
        this.breakModule.showDec = this.getBoolean(this.breakModule, "showDec");
        this.breakModule.bed = this.getBoolean(this.breakModule, "bed");
        this.breakModule.beacon = this.getBoolean(this.breakModule, "beacon");
        this.breakModule.obsidian = this.getBoolean(this.breakModule, "obsidian");

        this.loadDefaults(this.mlgModule, "mlg");
        this.mlgModule.showDamage = this.getBoolean(this.mlgModule, "showDamage");

        this.moduleConfig.save();
    }

    public void saveModuleConfig(){

        this.saveDefaults(this.fpsModule, "fps");

        this.saveDefaults(this.coordModule, "coordinates");

        this.saveDefaults(this.breakModule, "break");
        this.setBoolean(this.breakModule, "showDec", this.breakModule.showDec);
        this.setBoolean(this.breakModule, "bed", this.breakModule.bed);
        this.setBoolean(this.breakModule, "beacon", this.breakModule.beacon);
        this.setBoolean(this.breakModule, "obsidian", this.breakModule.obsidian);

        this.saveDefaults(this.mlgModule, "mlg");
        this.setBoolean(this.mlgModule, "showDamage", this.mlgModule.showDamage);

        this.moduleConfig.save();
    }

    public void loadDefaults(AModule module, String name){
        module.enabled = this.getBoolean(module, "enabled");
        module.posX = this.getDouble(module, "posX");
        module.posY = this.getDouble(module, "posY");
    }

    public void saveDefaults(AModule module, String name){
        this.setBoolean(module, "enabled", module.enabled);
        this.setDouble(module, "posX", module.posX);
        this.setDouble(module, "posY", module.posY);
    }

    public boolean getBoolean(AModule module, String key){
        return this.moduleConfig.get(module.name, key, false).getBoolean();
    }

    public double getDouble(AModule module, String key){
        return this.moduleConfig.get(module.name, key, 0.0).getDouble();
    }

    public void setBoolean(AModule module, String key, Boolean value){
        this.moduleConfig.get(module.name, key, false).set(value);
    }

    public void setDouble(AModule module, String key, Double value){
        this.moduleConfig.get(module.name, key, 0.0).set(value);
    }

}
