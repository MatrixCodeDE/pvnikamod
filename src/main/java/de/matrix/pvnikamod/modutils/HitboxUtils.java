package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.IngameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

import java.awt.*;

public class HitboxUtils {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public HitboxUtils() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void toggleHitboxAdvanced() {
        boolean isAdvanced = this.config.hitboxSettings.advanced;
        this.config.hitboxSettings.advanced = !isAdvanced;
    }

    public void toggleHitboxActivated() {
        boolean isActive = this.config.hitboxSettings.activated[this.config.hitboxSettings.group];
        this.config.hitboxSettings.activated[this.config.hitboxSettings.group] = !isActive;
    }

    public void toggleHitboxChroma() {
        boolean isChroma = this.config.hitboxSettings.chroma[this.config.hitboxSettings.group];
        this.config.hitboxSettings.chroma[this.config.hitboxSettings.group] = !isChroma;
    }

    public int getColorRGB(int group) {
        return (new Color(this.config.hitboxSettings.red[group], this.config.hitboxSettings.green[group], this.config.hitboxSettings.blue[group], this.config.hitboxSettings.alpha[group])).getRGB();
    }

    public int getChromaRGB(int group) {
        Color chroma = getChroma(group);
        return (new Color(chroma.getRed(), chroma.getGreen(), chroma.getBlue(), chroma.getAlpha())).getRGB();
    }

    public Color getChroma(int group) {
        Color color =  Color.getHSBColor((float)(System.currentTimeMillis() % (5000L / config.hitboxSettings.speed[group])), 0.8F, 0.8F);
        return color;
    }

}
