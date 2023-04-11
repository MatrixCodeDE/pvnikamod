package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
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

}
