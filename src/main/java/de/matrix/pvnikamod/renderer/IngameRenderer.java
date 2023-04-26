package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.listener.Implementation;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class IngameRenderer extends Gui {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final Implementation implementation;

    public IngameRenderer(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.implementation = PvnikaMod.getInstance().implementation;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.RenderTickEvent event) {
        if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiChat) {
            if (this.config.iginfos_showFPS) {
                String counter = I18n.format("menu.pvnika.iginfos.fps.name") + ": " + Minecraft.getDebugFPS();
                RenderManager.drawInfoBoxSoloRect(this.config.iginfos_PosX[0], this.config.iginfos_PosY[0], 54, counter, true);
            }
            if (this.config.iginfos_showCoords || true) {
                int x = MathHelper.floor_double(this.mc.thePlayer.posX);
                int y = MathHelper.floor_double(this.mc.thePlayer.posY);
                int z = MathHelper.floor_double(this.mc.thePlayer.posZ);
                double dir = this.mc.thePlayer.rotationYaw;
                String[] coords = new String[]{"X:", String.valueOf(x), "Y:", String.valueOf(y), "Z:", String.valueOf(z)};
                int[] cordcolors = new int[]{
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),};
                RenderManager.drawCoordBoxRect(this.config.iginfos_PosX[1], this.config.iginfos_PosY[1], 54, coords, cordcolors);
            }
            if (true){
                Item targetItem = this.implementation.getTargetBlockItem();
                boolean isBed = targetItem != null && targetItem == Item.getItemById(355);
                boolean isCore = targetItem != null && targetItem == Item.getItemById(138);
                boolean isObsidian = targetItem != null && targetItem == Item.getItemById(49);
                if (isBed || isCore || isObsidian){
                    this.mc.fontRendererObj.drawStringWithShadow(PvnikaMod.getInstance().implementation.getBlockBreakPercentage() +"%", 50, 50, ColorUtil.rgbToDec(255, 255, 255));
                }
            }
        }
    }



}
