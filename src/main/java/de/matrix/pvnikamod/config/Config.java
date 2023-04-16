package de.matrix.pvnikamod.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    // GuiGeneral 0 - 2
    public boolean customMenu;
    public boolean ownNameTag;
    public boolean pingOnTab;
    public boolean confirmDisconnect;
    public boolean fullBright;

    // GuiParticles 3 - 4
    public double particleMultiplier = 1;
    public boolean alwaysParticles;
    public boolean alwaysSharpnessParticles;

    // GuiHitbox 5 - 13
    public boolean hitbox_advanced;
    public int hitbox_group;
    public int hitbox_last;
    public boolean[] hitbox_activated = new boolean[7];
    public int[] hitbox_a = new int[7];
    public int[] hitbox_r = new int[7];
    public int[] hitbox_g = new int[7];
    public int[] hitbox_b = new int[7];
    public boolean[] hitbox_chroma = new boolean[7];
    public int[] hitbox_speed = new int[7];

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

    public Configuration config;

    public Config(File configFile){
        this.config = new Configuration(configFile);
    }

    public void loadConfig() {
        this.config.load();

        this.customMenu = this.config.get("general", "customMenu", false).getBoolean();
        this.ownNameTag = this.config.get("general", "ownNameTag", false).getBoolean();
        this.pingOnTab = this.config.get("general", "pingOnTab", false).getBoolean();
        this.confirmDisconnect = this.config.get("general", "confirmDisconnect", false).getBoolean();
        this.fullBright = this.config.get("general", "fullBright", false).getBoolean();

        this.particleMultiplier = this.config.get("particles", "multiplier", 1.0).getDouble();
        this.alwaysParticles = this.config.get("particles", "alwaysParticles", false).getBoolean();
        this.alwaysSharpnessParticles = this.config.get("particles", "alwaysSharpnessParticles", false).getBoolean();

        this.hitbox_advanced = this.config.get("hitbox", "advanced", false).getBoolean();
        this.hitbox_group = this.config.get("hitbox", "group", 0).getInt();
        this.hitbox_last = this.config.get("hitbox", "last", 1).getInt();
        this.hitbox_activated = this.config.get("hitbox", "activated", new boolean[7]).getBooleanList();
        this.hitbox_r = this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitbox_g = this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitbox_b = this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitbox_a = this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).getIntList();
        this.hitbox_chroma = this.config.get("hitbox", "chroma", new boolean[7]).getBooleanList();
        this.hitbox_speed = this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).getIntList();

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

        this.movement_toggleSneak = this.config.get("movement", "toggleSneak", true).getBoolean();
        this.movement_toggleSprint = this.config.get("movement", "toggleSprint", false).getBoolean();

        this.config.save();
    }

    public void saveConfig() {
        this.config.get("general", "customMenu", false).set(this.customMenu);
        this.config.get("general", "ownNameTag", false).set(this.ownNameTag);
        this.config.get("general", "pingOnTab", false).set(this.pingOnTab);
        this.config.get("general", "confirmDisconnect", false).set(this.confirmDisconnect);
        this.config.get("general", "fullBright", false).set(this.fullBright);

        this.config.get("particles", "multiplier", 1.0).set(this.particleMultiplier);
        this.config.get("particles", "alwaysParticles", false).set(this.alwaysParticles);
        this.config.get("particles", "alwaysSharpnessParticles", false).set(this.alwaysSharpnessParticles);

        this.config.get("hitbox", "advanced", false).set(this.hitbox_advanced);
        this.config.get("hitbox", "group", 0).set(this.hitbox_group);
        this.config.get("hitbox", "last", 1).set(this.hitbox_last);
        this.config.get("hitbox", "activated", new boolean[7]).set(this.hitbox_activated);
        this.config.get("hitbox", "red", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitbox_r);
        this.config.get("hitbox", "green", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitbox_g);
        this.config.get("hitbox", "blue", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitbox_b);
        this.config.get("hitbox", "alpha", new int[] {255, 255, 255, 255, 255, 255, 255}).set(this.hitbox_a);
        this.config.get("hitbox", "chroma", new boolean[7]).set(this.hitbox_chroma);
        this.config.get("hitbox", "speed", new int[] {1, 1, 1, 1, 1, 1, 1}).set(this.hitbox_speed);

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

        this.config.get("movement", "toggleSneak", true).set(this.movement_toggleSneak);
        this.config.get("movement", "toggleSprint", false).set(this.movement_toggleSprint);

        this.config.save();
    }
}
