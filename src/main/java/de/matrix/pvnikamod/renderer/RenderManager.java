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

    static void drawRectangle(int xPos, int yPos, int width, int height, Color color, int alpha){
        int x2 = xPos + width;
        int y2 = yPos + height;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)alpha / 255.0F);
        GL11.glBegin(7);
        GL11.glVertex2f(xPos, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, yPos);
        GL11.glVertex2f(xPos, yPos);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    static void drawRoundedRect(int x0, int y0, int x1, int y1, float radius, int color, int alpha) {
        int i = 18;
        float f = 90.0F / (float)i;
        float f2 = (float)(color >> 16 & 255) / 255.0F;
        float f3 = (float)(color >> 8 & 255) / 255.0F;
        float f4 = (float)(color & 255) / 255.0F;
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f2, f3, f4, alpha);
        GL11.glBegin(5);
        GL11.glVertex2f((float)x0 + radius, (float)y0);
        GL11.glVertex2f((float)x0 + radius, (float)y1);
        GL11.glVertex2f((float)x1 - radius, (float)y0);
        GL11.glVertex2f((float)x1 - radius, (float)y1);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f((float)x0, (float)y0 + radius);
        GL11.glVertex2f((float)x0 + radius, (float)y0 + radius);
        GL11.glVertex2f((float)x0, (float)y1 - radius);
        GL11.glVertex2f((float)x0 + radius, (float)y1 - radius);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f((float)x1, (float)y0 + radius);
        GL11.glVertex2f((float)x1 - radius, (float)y0 + radius);
        GL11.glVertex2f((float)x1, (float)y1 - radius);
        GL11.glVertex2f((float)x1 - radius, (float)y1 - radius);
        GL11.glEnd();
        GL11.glBegin(6);
        float f5 = (float)x1 - radius;
        float f6 = (float)y0 + radius;
        GL11.glVertex2f(f5, f6);

        for(int j = 0; j <= i; ++j) {
            float f7 = (float)j * f;
            GL11.glVertex2f((float)((double)f5 + (double)radius * Math.cos(Math.toRadians((double)f7))), (float)((double)f6 - (double)radius * Math.sin(Math.toRadians((double)f7))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f5 = (float)x0 + radius;
        f6 = (float)y0 + radius;
        GL11.glVertex2f(f5, f6);

        for(int k = 0; k <= i; ++k) {
            float f8 = (float)k * f;
            GL11.glVertex2f((float)((double)f5 - (double)radius * Math.cos(Math.toRadians((double)f8))), (float)((double)f6 - (double)radius * Math.sin(Math.toRadians((double)f8))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f5 = (float)x0 + radius;
        f6 = (float)y1 - radius;
        GL11.glVertex2f(f5, f6);

        for(int l = 0; l <= i; ++l) {
            float f9 = (float)l * f;
            GL11.glVertex2f((float)((double)f5 - (double)radius * Math.cos(Math.toRadians((double)f9))), (float)((double)f6 + (double)radius * Math.sin(Math.toRadians((double)f9))));
        }

        GL11.glEnd();
        GL11.glBegin(6);
        f5 = (float)x1 - radius;
        f6 = (float)y1 - radius;
        GL11.glVertex2f(f5, f6);

        for(int i1 = 0; i1 <= i; ++i1) {
            float f10 = (float)i1 * f;
            GL11.glVertex2f((float)((double)f5 + (double)radius * Math.cos(Math.toRadians((double)f10))), (float)((double)f6 + (double)radius * Math.sin(Math.toRadians((double)f10))));
        }

        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
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
