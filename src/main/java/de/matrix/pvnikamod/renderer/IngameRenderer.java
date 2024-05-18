package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.config.ingame.modules.BreakModule;
import de.matrix.pvnikamod.config.ingame.modules.MLGModule;
import de.matrix.pvnikamod.config.ingame.modules.ReachModule;
import de.matrix.pvnikamod.gui.modules.GuiIngameModuleScreen;
import de.matrix.pvnikamod.listener.Implementation;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.modules.MLGUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import de.matrix.pvnikamod.utils.ValueUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
    private long lastOwn;
    private double ownRange = 0.0;
    private long lastOther;
    private double otherRange = 0.0;

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
            DrawUtils drawUtils = new DrawUtils();
            this.mc.getTextureManager().bindTexture(PvnikaMod.pvnikaLogo);
            drawUtils.drawTexture(10, 10, 255.0D, 255.0D, 100, 100, 1.0f);
        }*/
        if (counter < System.currentTimeMillis() && counter != 0){
            counter = 0;
        }
        GuiScreen currentScreen = this.mc.currentScreen;
        if (currentScreen == null)
            RuntimeSettings.igModSlided = false;
        if (currentScreen == null || currentScreen instanceof GuiChat || (currentScreen instanceof GuiIngameModuleScreen && RuntimeSettings.igModSlided)) {


            /*FPS Module*/
            if (this.config.igModules.fpsModule.enabled) {
                String counter = I18n.format("menu.pvnika.iginfos.fps.name") + ": " + Minecraft.getDebugFPS();
                CustomRenderManager.drawInfoBoxSoloRect(this.config.igModules.fpsModule.posX, this.config.igModules.fpsModule.posY, 54, counter, true);
            }


            /*Coords Module*/
            if (this.config.igModules.coordModule.enabled) {
                int x = 0;
                int y = 0;
                int z = 0;
                String sDir = "";
                if (this.mc.thePlayer != null) {
                    x = MathHelper.floor_double(this.mc.thePlayer.posX);
                    y = MathHelper.floor_double(this.mc.thePlayer.posY);
                    z = MathHelper.floor_double(this.mc.thePlayer.posZ);
                    sDir = ValueUtil.rotationToDirection(this.mc.thePlayer.rotationYaw);
                }
                String[] coords = new String[]{"X:", String.valueOf(x), "Y:", String.valueOf(y), "Z:", String.valueOf(z), "D:", sDir};
                int[] cordcolors = new int[]{
                        ColorUtil.colorToDec(new Color(255, 64, 64)),
                        ColorUtil.colorToDec(new Color(255, 96, 96)),
                        ColorUtil.colorToDec(new Color(128, 255, 0)),
                        ColorUtil.colorToDec(new Color(128, 255, 32)),
                        ColorUtil.colorToDec(new Color(0, 128, 255)),
                        ColorUtil.colorToDec(new Color(32, 128, 255)),
                        ColorUtil.colorToDec(new Color(255, 128, 0)),
                        ColorUtil.colorToDec(new Color(255, 128, 64))};
                CustomRenderManager.drawCoordBoxRect(this.config.igModules.coordModule.posX, this.config.igModules.coordModule.posY, 54, coords, cordcolors);
            }


            /*Break Module*/
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
                        CustomRenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                    } else
                    if ((isBed && breakConfig.bed) || (isBeacon && breakConfig.beacon) || (isObsidian && breakConfig.obsidian) || RuntimeSettings.igModSlided) {
                        if (fPercentage != 0.0f || RuntimeSettings.igModSlided) {
                            boolean test = RuntimeSettings.igModSlided;
                            CustomRenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                        }
                    }
                } else {
                    CustomRenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                }
                if (RuntimeSettings.renderEnderPearl){
                    CustomRenderManager.drawInfoBoxSoloRect(breakConfig.posX + 36, breakConfig.posY, 36, "Pearl", true);
                }
            }


            /*MLG Module*/
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
                CustomRenderManager.drawInfoBoxRect(mlgModule.posX, mlgModule.posY, 56, text, true, tcol);

            }


            /*Coords Module*/
            if (this.config.igModules.reachModule.enabled){
                ReachModule reachModule = this.config.igModules.reachModule;
                String ownText = "Hasn't attacked";
                if (lastOwn + 2000L > System.currentTimeMillis()){
                    ownText = String.format("%.2f", ownRange);
                }
                CustomRenderManager.drawInfoBoxSoloRect(reachModule.posX, reachModule.posY, 80, ownText, true);
            }

        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        // Own Reach
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && this.mc.objectMouseOver.entityHit.getEntityId() == event.target.getEntityId()){
            Vec3 vector = this.mc.getRenderViewEntity().getPositionEyes(1.0f);
            ownRange = this.mc.objectMouseOver.hitVec.distanceTo(vector);
            lastOwn = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event){

    }

}
