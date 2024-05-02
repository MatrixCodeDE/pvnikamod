package de.matrix.pvnikamod.config;

import de.matrix.pvnikamod.config.ingame.IGModules;
import de.matrix.pvnikamod.config.settings.*;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public GeneralSettings generalSettings;
    public ParticleSettings particleSettings;
    public HitboxSettings hitboxSettings;
    public CrosshairSettings crosshairSettings;
    public ZoomSettings zoomSettings;
    public MovementSettings movementSettings;
    public VisualsSettings visualsSettings;
    public IGModules igModules;

    public Configuration config;

    public Config(File configFile, File modulesFile){
        this.config = new Configuration(configFile);
        this.igModules = new IGModules(modulesFile);
        this.generalSettings = new GeneralSettings();
        this.particleSettings = new ParticleSettings();
        this.hitboxSettings = new HitboxSettings();
        this.crosshairSettings = new CrosshairSettings();
        this.zoomSettings = new ZoomSettings();
        this.movementSettings = new MovementSettings();
        this.visualsSettings = new VisualsSettings();
    }

    public void loadConfig() {
        this.config.load();

        this.generalSettings.customMenu = this.config.get("general", "customMenu", false).getBoolean();
        this.generalSettings.logoInMenu = this.config.get("general", "logoInMenu", false).getBoolean();
        this.generalSettings.ownNameTag = this.config.get("general", "ownNameTag", false).getBoolean();
        this.generalSettings.pingOnTab = this.config.get("general", "pingOnTab", false).getBoolean();
        this.generalSettings.confirmDisconnect = this.config.get("general", "confirmDisconnect", false).getBoolean();
        this.generalSettings.fullBright = this.config.get("general", "fullBright", false).getBoolean();

        this.particleSettings.particleMultiplier = this.config.get("particles", "multiplier", 1.0).getDouble();
        this.particleSettings.alwaysParticles = this.config.get("particles", "alwaysParticles", false).getBoolean();
        this.particleSettings.alwaysSharpnessParticles = this.config.get("particles", "alwaysSharpnessParticles", false).getBoolean();

        this.hitboxSettings.advanced = this.config.get("hitbox", "advanced", false).getBoolean();
        this.hitboxSettings.group = this.config.get("hitbox", "group", 0).getInt();
        this.hitboxSettings.lastGroup = this.config.get("hitbox", "last", 1).getInt();
        this.hitboxSettings.activated = this.config.get("hitbox", "enabled", new boolean[7]).getBooleanList();
        this.hitboxSettings.red = this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.green = this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.blue = this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.alpha = this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.chroma = this.config.get("hitbox", "chroma", new boolean[7]).getBooleanList();
        this.hitboxSettings.speed = this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).getIntList();

        this.crosshairSettings.activated = this.config.get("crosshair", "enabled", false).getBoolean();
        this.crosshairSettings.gap = this.config.get("crosshair", "gap", 0).getInt();
        this.crosshairSettings.height = this.config.get("crosshair", "height", 4).getInt();
        this.crosshairSettings.width = this.config.get("crosshair", "width", 4).getInt();
        this.crosshairSettings.thickness = this.config.get("crosshair", "thickness", 1).getInt();
        this.crosshairSettings.outthick = this.config.get("crosshair", "outthick", 1).getInt();
        this.crosshairSettings.size = this.config.get("crosshair", "size", 1).getInt();

        this.zoomSettings.smooth = this.config.get("zoom", "smooth", false).getBoolean();
        this.zoomSettings.scrollable = this.config.get("zoom", "scrollable", false).getBoolean();
        this.zoomSettings.defaultZoom = this.config.get("zoom", "defaultZoom", 3.0).getDouble();

        this.movementSettings.toggleSneak = this.config.get("movement", "toggleSneak", false).getBoolean();
        this.movementSettings.toggleSprint = this.config.get("movement", "toggleSprint", false).getBoolean();

        this.visualsSettings.customMenu = this.config.get("visuals", "customMenu", false).getBoolean();
        this.visualsSettings.disableShift = this.config.get("visuals", "disableShift", false).getBoolean();
        this.visualsSettings.pingOnTab = this.config.get("visuals", "pingOnTab", false).getBoolean();
        this.visualsSettings.fullBright = this.config.get("visuals", "fullBright", false).getBoolean();

        this.visualsSettings.guiSizeHotbar = this.config.get("guiSize", "hotbar", -1).getInt();

        this.config.save();
    }

    public void saveConfig() {
        this.config.get("general", "customMenu", false).set(this.generalSettings.customMenu);
        this.config.get("general", "logoInMenu", false).set(this.generalSettings.logoInMenu);
        this.config.get("general", "ownNameTag", false).set(this.generalSettings.ownNameTag);
        this.config.get("general", "pingOnTab", false).set(this.generalSettings.pingOnTab);
        this.config.get("general", "confirmDisconnect", false).set(this.generalSettings.confirmDisconnect);
        this.config.get("general", "fullBright", false).set(this.generalSettings.fullBright);

        this.config.get("particles", "multiplier", 1.0).set(this.particleSettings.particleMultiplier);
        this.config.get("particles", "alwaysParticles", false).set(this.particleSettings.alwaysParticles);
        this.config.get("particles", "alwaysSharpnessParticles", false).set(this.particleSettings.alwaysSharpnessParticles);

        this.config.get("hitbox", "advanced", false).set(this.hitboxSettings.advanced);
        this.config.get("hitbox", "group", 0).set(this.hitboxSettings.group);
        this.config.get("hitbox", "last", 1).set(this.hitboxSettings.lastGroup);
        this.config.get("hitbox", "enabled", new boolean[7]).set(this.hitboxSettings.activated);
        this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.red);
        this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.green);
        this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.blue);
        this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.alpha);
        this.config.get("hitbox", "chroma", new boolean[7]).set(this.hitboxSettings.chroma);
        this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).set(this.hitboxSettings.speed);

        this.config.get("crosshair", "enabled", false).set(this.crosshairSettings.activated);
        this.config.get("crosshair", "gap", 0).set(this.crosshairSettings.gap);
        this.config.get("crosshair", "height", 4).set(this.crosshairSettings.height);
        this.config.get("crosshair", "width", 4).set(this.crosshairSettings.width);
        this.config.get("crosshair", "thickness", 1).set(this.crosshairSettings.thickness);
        this.config.get("crosshair", "outthick", 1).set(this.crosshairSettings.outthick);
        this.config.get("crosshair", "size", 1).set(this.crosshairSettings.size);

        this.config.get("zoom", "smooth", false).set(this.zoomSettings.smooth);
        this.config.get("zoom", "scrollable", false).set(this.zoomSettings.scrollable);
        this.config.get("zoom", "defaultZoom", 3.0).set(this.zoomSettings.defaultZoom);

        this.config.get("movement", "toggleSneak", false).set(this.movementSettings.toggleSneak);
        this.config.get("movement", "toggleSprint", false).set(this.movementSettings.toggleSprint);

        this.config.get("visuals", "customMenu", false).set(this.visualsSettings.customMenu);
        this.config.get("visuals", "disableShift", false).set(this.visualsSettings.disableShift);
        this.config.get("visuals", "pingOnTab", false).set(this.visualsSettings.pingOnTab);
        this.config.get("visuals", "fullBright", false).set(this.visualsSettings.fullBright);

        this.config.save();
    }
}

