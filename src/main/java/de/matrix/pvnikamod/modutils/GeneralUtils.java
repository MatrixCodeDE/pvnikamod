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
        boolean customMenu = this.config.generalSettings.customMenu;
        this.config.generalSettings.customMenu = !customMenu;
    }

    public void toggleLogoInMenu() {
        boolean logo = this.config.generalSettings.logoInMenu;
        this.config.generalSettings.logoInMenu = !logo;
    }

    public void toggleOwnNameTag() {
        boolean ownNameTag = this.config.generalSettings.ownNameTag;
        this.config.generalSettings.ownNameTag = !ownNameTag;
    }

    public void togglePingOnTab() {
        boolean pingOnTab = this.config.generalSettings.pingOnTab;
        this.config.generalSettings.pingOnTab = !pingOnTab;
    }

    public void toggleConfirmDisconnect() {
        boolean confirmDisconnect = this.config.generalSettings.confirmDisconnect;
        this.config.generalSettings.confirmDisconnect = !confirmDisconnect;
    }
}
