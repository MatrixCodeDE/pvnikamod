package de.matrix.pvnikamod.utils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BooleanColor {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    public BooleanColor(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public static String boolColor(boolean istrue, String text) {
        String color = "§c";
        if (istrue){
            color = "§a";
        }
        return color + text;
    }

    public static String enableText(boolean istrue){
        String str = I18n.format("menu.pvnika.all.disabled");
        if(istrue){
            str = I18n.format("menu.pvnika.all.enabled");
        }
        return boolColor(istrue, str);
    }

}
