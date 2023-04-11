package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

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
        boolean isAdvanced = this.config.hitbox_advanced;
        this.config.hitbox_advanced = !isAdvanced;
    }

    public void toggleHitboxActivated() {
        boolean isActive = this.config.hitbox_activated[this.config.hitbox_group];
        this.config.hitbox_activated[this.config.hitbox_group] = !isActive;
    }

    public void toggleHitboxChroma() {
        boolean isChroma = this.config.hitbox_chroma[this.config.hitbox_group];
        this.config.hitbox_chroma[this.config.hitbox_group] = !isChroma;
    }

    public int getColorRGB(int group) {
        return (new Color(this.config.hitbox_r[group], this.config.hitbox_g[group], this.config.hitbox_b[group], this.config.hitbox_a[group])).getRGB();
    }

    public int getChromaRGB(int group) {
        Color chroma = getChroma(group);
        return (new Color(chroma.getRed(), chroma.getGreen(), chroma.getBlue(), chroma.getAlpha())).getRGB();
    }

    public Color getChroma(int group) {
        Color color =  Color.getHSBColor((float)(System.currentTimeMillis() % (5000L / config.hitbox_speed[group])), 0.8F, 0.8F);
        return color;
    }

    public int hexToARGB(String hexARGB) throws IllegalArgumentException {

        if (!hexARGB.startsWith("#")) {
            hexARGB = "#" + hexARGB;
        }
        if (!(hexARGB.length() == 7 || hexARGB.length() == 9)) {

            throw new IllegalArgumentException("False hex string");
        }

        int[] intARGB = new int[4];

        if (hexARGB.length() == 9) {
            intARGB[0] = Integer.valueOf(hexARGB.substring(1, 3), 16); // alpha
            intARGB[1] = Integer.valueOf(hexARGB.substring(3, 5), 16); // red
            intARGB[2] = Integer.valueOf(hexARGB.substring(5, 7), 16); // green
            intARGB[3] = Integer.valueOf(hexARGB.substring(7), 16); // blue
        } else hexToARGB("#FF" + hexARGB.substring(1));

        return intARGB[0] | intARGB[1] | intARGB[2] | intARGB[3];
    }

}
