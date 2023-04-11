package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.MiscRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GeneralUtils {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public GeneralUtils(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void toggleCustomMenu() {
        boolean customMenu = this.config.customMenu;
        this.config.customMenu = !customMenu;
    }

    public void toggleOwnNameTag() {
        boolean ownNameTag = this.config.ownNameTag;
        this.config.ownNameTag = !ownNameTag;
    }

    public void togglePingOnTab() {
        boolean pingOnTab = this.config.pingOnTab;
        this.config.pingOnTab = !pingOnTab;
    }

    public void toggleConfirmDisconnect() {
        boolean confirmDisconnect = this.config.confirmDisconnect;
        this.config.confirmDisconnect = !confirmDisconnect;
    }

    public void toggleFullBright(){
        boolean fullBright = this.config.fullBright;
        this.config.fullBright = !fullBright;
        MiscRenderer.toggleFullBright();
    }
}
