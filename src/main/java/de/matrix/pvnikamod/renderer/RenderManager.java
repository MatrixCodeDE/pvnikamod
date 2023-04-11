package de.matrix.pvnikamod.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public interface RenderManager {

    static void pre() {
        GL11.glPushMatrix();
    }

    static void post() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    static void displayFilledRectangle(float x1, float y1, float x2, float y2, Color color) {
        GL11.glPushMatrix();
        if(x1 < x2) {
            float i = x1;
            x1 = x2;
            x2 = i;
        }

        if(y1 < y2) {
            float j = y1;
            y1 = y2;
            y2 = j;
        }

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(7);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    static void drawLines(float[] points, Color color){
        float sx = points[2] - points[0];
        float sy = points[3] - points[1];
        for (float x = 0; x < sx; x++) {
            for (float y = 0; y < sy; y++) {
                drawDot(x, y, 1.0F, color);
            }
        }
    }

    static void drawDot(float x, float y, float size, Color color) {
        GL11.glPointSize(size);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(0);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

}
