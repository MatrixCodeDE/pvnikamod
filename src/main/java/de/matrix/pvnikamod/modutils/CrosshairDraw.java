package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.CustomRenderManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CrosshairDraw {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private int renderGap;
    private int height;
    private int width;
    private int thickness;
    private int outthick;
    private int size;
    private float factor;

    public CrosshairDraw() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    private void updateValues(){
        this.size = this.config.crosshairSettings.size;
        this.renderGap = this.config.crosshairSettings.gap;
        this.height = this.config.crosshairSettings.height;
        this.width = this.config.crosshairSettings.width;
        this.thickness = this.config.crosshairSettings.thickness;
        this.outthick = this.config.crosshairSettings.outthick;
    }

    public void drawBase(float x, float y, float size, Color color, Color color2) {
        int screenWidth = (int) x - 1;
        int screenHeight = (int) y - 1;
        int i = thickness / 2;

        CustomRenderManager.displayFilledRectangle(screenWidth, screenHeight - 2, screenWidth + 1 , screenHeight - 5, color);
        CustomRenderManager.displayFilledRectangle(screenWidth + 3, screenHeight, screenWidth + 6, screenHeight + 1, color);
        CustomRenderManager.displayFilledRectangle(screenWidth, screenHeight + 3, screenWidth + 1, screenHeight + 6, color);
        CustomRenderManager.displayFilledRectangle(screenWidth - 2, screenHeight, screenWidth - 5, screenHeight + 1, color);
        GL11.glLineWidth(2.0F);
    }

    public void drawDot(int x, int y, Color color, Color outlineColor){
        updateValues();
        CrosshairSquare chsOutDot = new CrosshairSquare();
        chsOutDot.setValues((float) x - thickness*factor - outthick*factor, (float) y - thickness*factor - outthick*factor, (float) x + thickness*factor + outthick*factor, (float) y + thickness*factor + outthick*factor);
        CustomRenderManager.displayFilledRectangle(chsOutDot.minX, chsOutDot.minY, chsOutDot.maxX, chsOutDot.maxY, outlineColor);
        CustomRenderManager.displayFilledRectangle((float) x - thickness*factor, (float) y - thickness*factor, (float) x + thickness*factor, (float) y + thickness*factor, color);
    }

    public void displayCrossCrosshair(int x, int y, Color color, Color outlineColor) {
        updateValues();
        this.factor = (float) size * (1.0f/3.0f);
        CrosshairSquare chsTop = new CrosshairSquare();
        CrosshairSquare chsBottom = new CrosshairSquare();
        CrosshairSquare chsLeft = new CrosshairSquare();
        CrosshairSquare chsRight = new CrosshairSquare();

        CrosshairSquare chsOutTop = new CrosshairSquare();
        CrosshairSquare chsOutBottom = new CrosshairSquare();
        CrosshairSquare chsOutLeft = new CrosshairSquare();
        CrosshairSquare chsOutRight = new CrosshairSquare();

        chsTop.setValues((float) x - thickness*factor, (float) y - thickness*factor - height*factor - renderGap*factor, (float) x + thickness*factor, (float) y - renderGap*factor - thickness*factor);
        chsBottom.setValues((float) x - thickness*factor, (float) y + thickness*factor + renderGap*factor, (float) x + thickness*factor, (float) y + renderGap*factor + height*factor + thickness*factor);
        chsLeft.setValues((float) x - thickness*factor - width*factor - renderGap*factor, (float) y - thickness*factor, (float) x - thickness*factor - renderGap*factor, (float) y + thickness*factor);
        chsRight.setValues((float) x + thickness*factor + renderGap*factor, (float) y - thickness*factor, (float) x + thickness*factor + renderGap*factor + width*factor, (float) y + thickness*factor);

        chsOutTop.setValues(chsTop.minX - outthick*factor, chsTop.minY - outthick*factor, chsTop.maxX + outthick*factor, chsTop.maxY + outthick*factor);
        chsOutBottom.setValues(chsBottom.minX - outthick*factor, chsBottom.minY - outthick*factor, chsBottom.maxX + outthick*factor, chsBottom.maxY + outthick*factor);
        chsOutLeft.setValues(chsLeft.minX - outthick*factor, chsLeft.minY - outthick*factor, chsLeft.maxX + outthick*factor, chsLeft.maxY + outthick*factor);
        chsOutRight.setValues(chsRight.minX - outthick*factor, chsRight.minY - outthick*factor, chsRight.maxX + outthick*factor, chsRight.maxY + outthick*factor);


        CustomRenderManager.displayFilledRectangle(chsOutTop.minX, chsOutTop.minY, chsOutTop.maxX, chsOutTop.maxY, outlineColor);
        CustomRenderManager.displayFilledRectangle(chsOutBottom.minX, chsOutBottom.minY, chsOutBottom.maxX, chsOutBottom.maxY, outlineColor);
        CustomRenderManager.displayFilledRectangle(chsOutLeft.minX, chsOutLeft.minY, chsOutLeft.maxX, chsOutLeft.maxY, outlineColor);
        CustomRenderManager.displayFilledRectangle(chsOutRight.minX, chsOutRight.minY, chsOutRight.maxX, chsOutRight.maxY, outlineColor);

        CustomRenderManager.displayFilledRectangle(chsTop.minX, chsTop.minY, chsTop.maxX, chsTop.maxY, color);
        CustomRenderManager.displayFilledRectangle(chsBottom.minX, chsBottom.minY, chsBottom.maxX, chsBottom.maxY, color);
        CustomRenderManager.displayFilledRectangle(chsLeft.minX, chsLeft.minY, chsLeft.maxX, chsLeft.maxY, color);
        CustomRenderManager.displayFilledRectangle(chsRight.minX, chsRight.minY, chsRight.maxX, chsRight.maxY, color);

        GL11.glLineWidth(2.0F);
    }

}
