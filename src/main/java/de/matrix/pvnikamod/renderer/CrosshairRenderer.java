package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.CrosshairDraw;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;

import java.awt.*;

public class CrosshairRenderer {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private final CrosshairDraw crosshairDraw;

    public CrosshairRenderer() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.crosshairDraw = new CrosshairDraw();
    }

    public void render(int x, int y, float health){
        if (!this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI) {
            RenderManager.pre();
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);

            Color white = new Color(255, 255, 255, 255);
            Color black = new Color(0, 0, 0, 255);
            Color red = new Color(255, 0, 0, 255);
            EntityPlayerSP playerSP = this.mc.thePlayer;
            if (playerSP == null){
                crosshairDraw.drawDot(x, y, red, black);
                crosshairDraw.displayCrossCrosshair(x, y, red, black);
            } else
            if (health > 8.0F && !playerSP.isSpectator() || playerSP.capabilities.isCreativeMode) {
                //RenderManager.drawDot(x, y, 2.0F, white);
                crosshairDraw.drawDot(x, y, white, black);
                crosshairDraw.displayCrossCrosshair(x, y, white, black);
            } else {
                //RenderManager.drawDot(x, y, 2.0F, Color.WHITE);
                crosshairDraw.drawDot(x, y, red, black);
                crosshairDraw.displayCrossCrosshair(x, y, red, black);
            }
            RenderManager.post();
        }
    }

    public void clear(){
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, scaledResolution.getScaledWidth_double(), scaledResolution.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

}
