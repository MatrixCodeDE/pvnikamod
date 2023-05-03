package de.matrix.pvnikamod.modutils.modules;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.ingame.modules.AModule;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;

public abstract class ModuleUtils {

    private static final PvnikaMod mod = PvnikaMod.getInstance();
    private static final Config config = mod.getConfig();
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void toggleEnabled(AModule aModule){
        aModule.enabled = !aModule.enabled;
    }
}
