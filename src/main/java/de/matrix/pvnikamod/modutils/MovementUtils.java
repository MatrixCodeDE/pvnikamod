package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

public class MovementUtils {

    private final PvnikaMod mod = PvnikaMod.getInstance();
    private final Config config = mod.getConfig();
    private final Minecraft mc = Minecraft.getMinecraft();

    public void toggleSneak(){
        this.config.movementSettings.toggleSneak = !this.config.movementSettings.toggleSneak;
    }

    public void toggleSprint(){
        this.config.movementSettings.toggleSprint = !this.config.movementSettings.toggleSprint;
    }
}
