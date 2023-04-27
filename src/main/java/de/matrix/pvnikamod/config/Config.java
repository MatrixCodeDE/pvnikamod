package de.matrix.pvnikamod.config;

import de.matrix.pvnikamod.config.ingame.IGModules;
import de.matrix.pvnikamod.config.settings.*;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public boolean crosshair_custom;
    public int crosshair_gap;
    public int crosshair_height;
    public int crosshair_width;
    public int crosshair_thickness;
    public int crosshair_outthick;
    public int crosshair_size;

    public boolean zoom_smooth;
    public boolean zoom_scrollable;
    public double zoom_default;

    public boolean movement_toggleSneak;
    public boolean movement_toggleSprint;

    public boolean iginfos_showFPS;
    public double[] iginfos_PosX = new double[2];
    public double[] iginfos_PosY = new double[2];
    public boolean iginfos_showCoords;

    public GeneralSettings generalSettings;
    public ParticleSettings particleSettings;
    public HitboxSettings hitboxSettings;
    public CrosshairSettings crosshairSettings;
    public ZoomSettings zoomSettings;
    public MovementSettings movementSettings;
    public IGModules igModules;

    public Configuration config;

    public Config(File configFile){
        this.config = new Configuration(configFile);
        this.igModules = new IGModules();
        this.generalSettings = new GeneralSettings();
        this.particleSettings = new ParticleSettings();
        this.hitboxSettings = new HitboxSettings();
        this.crosshairSettings = new CrosshairSettings();
        this.zoomSettings = new ZoomSettings();
        this.movementSettings = new MovementSettings();
    }

    public void loadConfig() {
        this.config.load();

        this.generalSettings.customMenu = this.config.get("general", "customMenu", false).getBoolean();
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
        this.hitboxSettings.activated = this.config.get("hitbox", "activated", new boolean[7]).getBooleanList();
        this.hitboxSettings.red = this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.green = this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.blue = this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.alpha = this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitboxSettings.chroma = this.config.get("hitbox", "chroma", new boolean[7]).getBooleanList();
        this.hitboxSettings.speed = this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).getIntList();

        this.crosshair_custom = this.config.get("crosshair", "custom", false).getBoolean();
        this.crosshair_gap = this.config.get("crosshair", "gap", 0).getInt();
        this.crosshair_height = this.config.get("crosshair", "height", 4).getInt();
        this.crosshair_width = this.config.get("crosshair", "width", 4).getInt();
        this.crosshair_thickness = this.config.get("crosshair", "thickness", 1).getInt();
        this.crosshair_outthick = this.config.get("crosshair", "outthick", 1).getInt();
        this.crosshair_size = this.config.get("crosshair", "size", 1).getInt();

        this.zoom_smooth = this.config.get("zoom", "smooth", false).getBoolean();
        this.zoom_scrollable = this.config.get("zoom", "scrollable", false).getBoolean();
        this.zoom_default = this.config.get("zoom", "default", 3.0).getDouble();

        this.movement_toggleSneak = this.config.get("movement", "toggleSneak", false).getBoolean();
        this.movement_toggleSprint = this.config.get("movement", "toggleSprint", false).getBoolean();

        this.iginfos_showFPS = this.config.get("ingameinfos", "showFPS", false).getBoolean();
        this.iginfos_PosX = this.config.get("ingameinfos", "posx", new double[]{0, 0}).getDoubleList();
        this.iginfos_PosY = this.config.get("ingameinfos", "posy", new double[]{0, 0}).getDoubleList();
        this.iginfos_showCoords = this.config.get("ingameinfos", "showCoords", false).getBoolean();

        this.config.save();
    }

    public void saveConfig() {
        this.config.get("general", "customMenu", false).set(this.generalSettings.customMenu);
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
        this.config.get("hitbox", "activated", new boolean[7]).set(this.hitboxSettings.activated);
        this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.red);
        this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.green);
        this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.blue);
        this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitboxSettings.alpha);
        this.config.get("hitbox", "chroma", new boolean[7]).set(this.hitboxSettings.chroma);
        this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).set(this.hitboxSettings.speed);

        this.config.get("crosshair", "custom", false).set(this.crosshair_custom);
        this.config.get("crosshair", "gap", 0).set(this.crosshair_gap);
        this.config.get("crosshair", "height", 4).set(this.crosshair_height);
        this.config.get("crosshair", "width", 4).set(this.crosshair_width);
        this.config.get("crosshair", "thickness", 1).set(this.crosshair_thickness);
        this.config.get("crosshair", "outthick", 1).set(this.crosshair_outthick);
        this.config.get("crosshair", "size", 1).set(this.crosshair_size);

        this.config.get("zoom", "smooth", false).set(this.zoom_smooth);
        this.config.get("zoom", "scrollable", false).set(this.zoom_scrollable);
        this.config.get("zoom", "default", 3.0).set(this.zoom_default);

        this.config.get("movement", "toggleSneak", false).set(this.movement_toggleSneak);
        this.config.get("movement", "toggleSprint", false).set(this.movement_toggleSprint);

        this.config.get("ingameinfos", "showFPS", false).set(this.iginfos_showFPS);
        this.config.get("ingameinfos", "posx", new double[]{0, 0}).set(this.iginfos_PosX);
        this.config.get("ingameinfos", "posy", new double[]{0, 0}).set(this.iginfos_PosY);
        this.config.get("ingameinfos", "showCoords", false).set(this.iginfos_showCoords);

        this.config.save();
    }
}

