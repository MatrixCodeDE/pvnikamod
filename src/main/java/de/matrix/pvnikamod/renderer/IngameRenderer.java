package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.config.ingame.modules.BreakModule;
import de.matrix.pvnikamod.config.ingame.modules.MLGModule;
import de.matrix.pvnikamod.gui.modules.GuiIngameModuleScreen;
import de.matrix.pvnikamod.listener.Implementation;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.DrawUtils;
import de.matrix.pvnikamod.modutils.modules.MLGUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public class IngameRenderer extends Gui {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final Implementation implementation;
    private final MLGUtils mlgUtils;
    private long counter;

    public IngameRenderer(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.implementation = PvnikaMod.getInstance().implementation;
        this.mlgUtils = new MLGUtils();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.RenderTickEvent event) {
        /*if (this.mc.currentScreen instanceof GuiContainer && this.mc.theWorld != null){
            System.out.println("event lol1");
            DrawUtils drawUtils = new DrawUtils();
            this.mc.getTextureManager().bindTexture(PvnikaMod.pvnikaLogo);
            drawUtils.drawTexture(10, 10, 255.0D, 255.0D, 100, 100, 1.0f);
        }*/
        if (counter < System.currentTimeMillis() && counter != 0){
            counter = 0;
        }
        GuiScreen currentScreen = this.mc.currentScreen;
        if (currentScreen == null || currentScreen instanceof GuiChat || (currentScreen instanceof GuiIngameModuleScreen && RuntimeSettings.igModSlided)) {
            if (this.config.igModules.fpsModule.enabled) {
                String counter = I18n.format("menu.pvnika.iginfos.fps.name") + ": " + Minecraft.getDebugFPS();
                RenderManager.drawInfoBoxSoloRect(this.config.igModules.fpsModule.posX, this.config.igModules.fpsModule.posY, 54, counter, true);
            }
            if (this.config.igModules.coordModule.enabled) {
                int x = 0;
                int y = 0;
                int z = 0;
                double dir = 0;
                if (this.mc.thePlayer != null) {
                    x = MathHelper.floor_double(this.mc.thePlayer.posX);
                    y = MathHelper.floor_double(this.mc.thePlayer.posY);
                    z = MathHelper.floor_double(this.mc.thePlayer.posZ);
                     dir = this.mc.thePlayer.rotationYaw;
                }
                String[] coords = new String[]{"X:", String.valueOf(x), "Y:", String.valueOf(y), "Z:", String.valueOf(z)};
                int[] cordcolors = new int[]{
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),
                        ColorUtil.colorToDec(new Color(255, 128, 00)), ColorUtil.colorToDec(new Color(0, 255, 255)),};
                RenderManager.drawCoordBoxRect(this.config.igModules.coordModule.posX, this.config.igModules.coordModule.posY, 54, coords, cordcolors);
            }
            if (this.config.igModules.breakModule.enabled){
                Item targetItem = this.implementation.getTargetBlockItem();
                boolean isBed = targetItem != null && targetItem == Item.getItemById(355);
                boolean isBeacon = targetItem != null && targetItem == Item.getItemById(138);
                boolean isObsidian = targetItem != null && targetItem == Item.getItemById(49);
                boolean isBBO = isBed || isBeacon || isObsidian;
                boolean wasBBO = this.implementation.brokenBlock == Block.getBlockFromName("bed") || this.implementation.brokenBlock == Block.getBlockFromName("beacon") || this.implementation.brokenBlock == Block.getBlockFromName("obsidian");
                float fPercentage = this.implementation.getFloatBlockBreakPercentage();
                int iPercentage = this.implementation.getIntBlockBreakPercentage();
                if (isBBO && fPercentage != 0.0f){
                    counter = 0L;
                }
                BreakModule breakConfig = this.config.igModules.breakModule;
                int width = 22;
                boolean skip = false;
                if (this.implementation.getBroken() && wasBBO) {
                    fPercentage = 100.00f;
                    iPercentage = 100;
                    this.implementation.setBroken(false);
                    skip = true;
                    counter = System.currentTimeMillis() + 3000L;
                }
                String sPercentage = iPercentage + "%";
                if (this.config.igModules.breakModule.showDec) {
                    width = 36;
                    sPercentage = String.format("%.2f", fPercentage) + "%";
                }
                if (!skip) {
                    if (counter >= System.currentTimeMillis()){
                        sPercentage = "100%";
                        if (this.config.igModules.breakModule.showDec) {
                            sPercentage = "100.00%";
                        }
                        RenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                    } else
                    if ((isBed && breakConfig.bed) || (isBeacon && breakConfig.beacon) || (isObsidian && breakConfig.obsidian) || RuntimeSettings.igModSlided) {
                        if (fPercentage != 0.0f || RuntimeSettings.igModSlided) {
                            RenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                        }
                    }
                } else {
                    RenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                }
                if (RuntimeSettings.renderEnderPearl){
                    RenderManager.drawInfoBoxSoloRect(breakConfig.posX + 36, breakConfig.posY, 36, "Pearl", true);
                }
            }
            if (this.config.igModules.mlgModule.enabled){
                MLGModule mlgModule = this.config.igModules.mlgModule;
                int mlgOption = this.mlgUtils.getOption();
                int[] tcol = new int[]{ColorUtil.colorToDec(new Color(255, 128, 0)), ColorUtil.colorToDec(new Color(255, 0, 0)), ColorUtil.colorToDec(new Color(0, 128, 255))};
                String mlgText = "";
                switch (mlgOption){
                    case 1:
                        mlgText = I18n.format("menu.pvnika.iginfos.mlg.walk");
                        break;
                    case 2:
                        mlgText = I18n.format("menu.pvnika.iginfos.mlg.jump");
                        break;
                    case 3:
                        mlgText = I18n.format("menu.pvnika.iginfos.mlg.both");
                        tcol[0] = ColorUtil.colorToDec(new Color(0, 160, 0));
                        break;
                    default:
                        mlgText = I18n.format("menu.pvnika.iginfos.mlg.impossible");
                        tcol[0] = ColorUtil.colorToDec(new Color(160, 0, 0));
                        break;
                }
                float damage = this.mlgUtils.getDamage();
                int distance = this.mlgUtils.getDistance();
                String dmgString = String.format("%.1f", this.mlgUtils.getDamage()) + " ❤";
                if (damage * 2 >= this.mc.thePlayer.getHealth()){
                    dmgString = I18n.format("menu.pvnika.iginfos.mlg.dead");
                }
                String[] text = new String[]{mlgText, dmgString, distance + " ⇩"};
                RenderManager.drawInfoBoxRect(mlgModule.posX, mlgModule.posY, 56, text, true, tcol);



            }

        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event){

    }

}
