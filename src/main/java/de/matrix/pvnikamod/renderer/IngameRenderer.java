package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.config.RuntimeSettings;
import de.matrix.pvnikamod.config.ingame.modules.BreakModule;
import de.matrix.pvnikamod.gui.modules.GuiIngameModuleScreen;
import de.matrix.pvnikamod.listener.Implementation;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
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
    protected static final ResourceLocation pvnikaLogo = new ResourceLocation("assets/pvnikamod/images/icon.png");

    public IngameRenderer(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.implementation = PvnikaMod.getInstance().implementation;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.RenderTickEvent event) {
        if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiChat || (this.mc.currentScreen instanceof GuiIngameModuleScreen && RuntimeSettings.igModSlided)) {
            if (this.config.igModules.fpsModule.enabled) {
                String counter = I18n.format("menu.pvnika.iginfos.fps.name") + ": " + Minecraft.getDebugFPS();
                RenderManager.drawInfoBoxSoloRect(this.config.igModules.fpsModule.posX, this.config.igModules.fpsModule.posY, 54, counter, true);
            }
            if (this.config.igModules.coordModule.enabled) {
                int x = MathHelper.floor_double(this.mc.thePlayer.posX);
                int y = MathHelper.floor_double(this.mc.thePlayer.posY);
                int z = MathHelper.floor_double(this.mc.thePlayer.posZ);
                double dir = this.mc.thePlayer.rotationYaw;
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
                float fPercentage = PvnikaMod.getInstance().implementation.getFloatBlockBreakPercentage();
                int iPercentage = PvnikaMod.getInstance().implementation.getIntBlockBreakPercentage();
                BreakModule breakConfig = this.config.igModules.breakModule;
                int width = 22;
                String sPercentage = iPercentage + "%";
                if (this.config.igModules.breakModule.showDec){
                    width = 36;
                    sPercentage = fPercentage + "%";
                }
                if ((isBed && breakConfig.bed) || (isBeacon && breakConfig.beacon) || (isObsidian && breakConfig.obsidian)){
                    if  (fPercentage != 0.0f){
                        RenderManager.drawInfoBoxSoloRect(breakConfig.posX, breakConfig.posY, width, sPercentage, true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event){
        if (event.gui instanceof GuiInventory){
            this.mc.getTextureManager().bindTexture(pvnikaLogo);
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 100, 100, 100.0f, 100.0f);
        }
    }

}
