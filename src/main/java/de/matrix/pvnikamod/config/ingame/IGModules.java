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
    public ReachModule reachModule;
    public CPSModule cpsModule;
    public ClockModule clockModule;


    public IGModules(File modulesFile){
        this.moduleConfig = new Configuration(modulesFile);
        this.fpsModule = new FPSModule();
        this.coordModule = new CoordModule();
        this.breakModule = new BreakModule();
        this.mlgModule = new MLGModule();
        this.reachModule = new ReachModule();
        this.cpsModule = new CPSModule();
        this.clockModule = new ClockModule();
    }

    public void loadModuleConfig(){
        this.moduleConfig.load();

        this.loadDefaults(this.fpsModule);

        this.loadDefaults(this.coordModule);

        this.loadDefaults(this.breakModule);
        this.breakModule.showDec = this.getBoolean(this.breakModule, "showDec");
        this.breakModule.bed = this.getBoolean(this.breakModule, "bed");
        this.breakModule.beacon = this.getBoolean(this.breakModule, "beacon");
        this.breakModule.obsidian = this.getBoolean(this.breakModule, "obsidian");

        this.loadDefaults(this.mlgModule);
        this.mlgModule.showDamage = this.getBoolean(this.mlgModule, "showDamage");

        this.loadDefaults(this.reachModule);
        this.reachModule.showOwn = this.getBoolean(this.reachModule, "own");
        this.reachModule.showOther = this.getBoolean(this.reachModule, "other");

        this.loadDefaults(this.cpsModule);
        this.cpsModule.split = this.getBoolean(this.cpsModule, "split");

        this.loadDefaults(this.clockModule);
        this.clockModule.twoperiods = this.getBoolean(this.clockModule, "twoperiods");

        this.moduleConfig.save();
    }

    public void saveModuleConfig(){

        this.saveDefaults(this.fpsModule);

        this.saveDefaults(this.coordModule);

        this.saveDefaults(this.breakModule);
        this.setBoolean(this.breakModule, "showDec", this.breakModule.showDec);
        this.setBoolean(this.breakModule, "bed", this.breakModule.bed);
        this.setBoolean(this.breakModule, "beacon", this.breakModule.beacon);
        this.setBoolean(this.breakModule, "obsidian", this.breakModule.obsidian);

        this.saveDefaults(this.mlgModule);
        this.setBoolean(this.mlgModule, "showDamage", this.mlgModule.showDamage);

        this.saveDefaults(this.reachModule);
        this.setBoolean(this.reachModule, "own", this.reachModule.showOwn);
        this.setBoolean(this.reachModule, "other", this.reachModule.showOther);

        this.loadDefaults(this.cpsModule);
        this.setBoolean(this.cpsModule, "split", this.cpsModule.split);

        this.saveDefaults(this.clockModule);
        this.setBoolean(this.clockModule, "twoperiods", this.clockModule.twoperiods);

        this.moduleConfig.save();
    }

    public void loadDefaults(AModule module){
        module.enabled = this.getBoolean(module, "enabled");
        module.posX = this.getDouble(module, "posX");
        module.posY = this.getDouble(module, "posY");
    }

    public void saveDefaults(AModule module){
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
