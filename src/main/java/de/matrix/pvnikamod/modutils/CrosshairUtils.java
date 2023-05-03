package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

public class CrosshairUtils {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public CrosshairUtils() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void toggleCrosshair() {
        boolean custom = this.config.crosshairSettings.activated;
        this.config.crosshairSettings.activated = !custom;
    }

}
