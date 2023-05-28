package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.DrawUtils;
import de.matrix.pvnikamod.utils.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

    static void drawRectangle(int xPos, int yPos, int width, int height, int color, int alpha){
        int x2 = xPos + width;
        int y2 = yPos + height;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(red, green, blue, (float)alpha / 255.0F);
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

    static int[] translateScreenPercentage(double x, double y){
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int posx = (int) ((x/100) * scaledResolution.getScaledWidth());
        int posy = (int) ((y/100) * scaledResolution.getScaledHeight());
        return new int[]{posx, posy};
    }

    static double[] translatePercentageToConfig(int x, int y){
        Minecraft mc = Minecraft.getMinecraft();
        double rx = (double)x / mc.displayWidth * 100;
        double ry = (double)y / mc.displayHeight * 100;
        return new double[]{rx, ry};
    }

    static double translateXToConfig(int x){
        Minecraft mc = Minecraft.getMinecraft();
        return translatePercentageToConfig(x, 0)[0];
    }

    static double translateYToConfig(int y){
        Minecraft mc = Minecraft.getMinecraft();
        return translatePercentageToConfig(0, y)[1];
    }

    static int[] translatePercentageFromConfig(double rx, double ry){
        Minecraft mc = Minecraft.getMinecraft();
        int x = (int) ((rx / 100) * mc.displayWidth);
        int y = (int) ((ry / 100) * mc.displayHeight);
        return new int[]{x, y};
    }

    static int translateXFromConfig(double rx){
        Minecraft mc = Minecraft.getMinecraft();
        return translatePercentageFromConfig(rx, 0)[0];
    }

    static int translateYFromConfig(double ry){
        Minecraft mc = Minecraft.getMinecraft();
        return translatePercentageFromConfig(0, ry)[1];
    }

    static void drawInfoBoxRect(double x, double y, int width, String[] text, boolean centered){
        Minecraft mc = Minecraft.getMinecraft();
        int[] positions = translateScreenPercentage(x, y);
        int height = text.length * 10 + 1 ;
        drawRectangle(positions[0], positions[1], width, height, ColorUtil.colorToDec(new Color(44, 44, 44)), 128);
        int i = 0;
        for (String s : text) {
            int strx = positions[0] + 2;
            if (centered){
                strx += (width - mc.fontRendererObj.getStringWidth(s)) / 2;
            }
            int stry = positions[1] + (i * 10) + 2;
            mc.fontRendererObj.drawStringWithShadow(s, strx, stry, 16777215);
            i++;
        }
    }

    static void drawCoordBoxRect(double x, double y, int width, String[] text, int[] colors){
        Minecraft mc = Minecraft.getMinecraft();
        int[] positions = translateScreenPercentage(x, y);
        int height = (text.length / 2) * 10 + 1 ;

        int i = 0;
        int j = 0;
        int k = 0;
        boolean flag = false;
        for (int l=0; l<text.length; l++){
            String bare = text[l].replace("-","");
            int barelen = mc.fontRendererObj.getStringWidth(bare);
            if (l % 2 == 1 && j < barelen){
                k = l;
                j = barelen;
            }
            flag = text[l].contains("-") || flag;
        }
        int w2 = 20 + j;
        if (flag){
            w2 += 6;
        }
        drawRectangle(positions[0], positions[1], w2, height, ColorUtil.colorToDec(new Color(44, 44, 44)), 128);
        for (String s : text) {
            String s2 = s.replace("-", "");
            int strx = 0;
            if (i % 2 == 1){
                int dif = 0;
                if (s.contains("-")){
                    dif = -6;
                }
                strx = (positions[0] + w2) - j + dif - 2;

            } else {
                strx = positions[0] + 2;
            }
            int stry = positions[1] + ((i / 2) * 10) + 2;
            int col = 0;
            try{
                col = colors[i];
            } catch (ArrayIndexOutOfBoundsException e){
                col = 16777215;
            }
            mc.fontRendererObj.drawStringWithShadow(s, strx, stry, col);
            i++;
        }
    }

    static void drawInfoBoxSoloRect(double x, double y, int width, String text, boolean centered){
        Minecraft mc = Minecraft.getMinecraft();
        int[] positions = translateScreenPercentage(x, y);
        int height = 8 + 4;
        drawRectangle(positions[0], positions[1], width, height, ColorUtil.colorToDec(new Color(44, 44, 44)), 128);
        int strx = positions[0];
        if (centered){
            strx += (width - mc.fontRendererObj.getStringWidth(text)) / 2;
        }
        int stry = positions[1] + 2;
        mc.fontRendererObj.drawStringWithShadow(text, strx, stry, 16777215);
    }

    static int[] translateInvLogoPosition(int invSize){
        int width = Minecraft.getMinecraft().currentScreen.width;
        int height = Minecraft.getMinecraft().currentScreen.height;

        int minSpace = 40;
        int space = (height - invSize) / 2;

        int x = (width / 2) - (minSpace * 3 / 2);
        int y = space - minSpace;

        if (space < minSpace){
            x = width - (minSpace * 3);
            y = height - minSpace;
        }

        return new int[] {x, y};

    }

    static int[] translateInvLogoPosition(int invSize, int slots){
        int width = Minecraft.getMinecraft().currentScreen.width;
        int height = Minecraft.getMinecraft().currentScreen.height;

        int minSpace = 40;
        int space = (height - (slots * 18 + 114)) / 2;

        int x = (width / 2) - (minSpace * 3 / 2);
        int y = space - minSpace;

        if (space < minSpace){
            x = width - (minSpace * 3);
            y = height - minSpace;
        }

        return new int[] {x, y};

    }

    static void renderPvnikaLogo(){
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        if (PvnikaMod.getInstance().getConfig().generalSettings.logoInMenu)
            return;
        Minecraft.getMinecraft().getTextureManager().bindTexture(PvnikaMod.pvnikaHeader);
        int[] values = translateInvLogoPosition(166);
        new DrawUtils().drawTexture(values[0], values[1], 1, 1, 120, 40, 1.0f);
    }

    static void renderPvnikaLogo(int rows){
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        if (PvnikaMod.getInstance().getConfig().generalSettings.logoInMenu)
            return;
        Minecraft.getMinecraft().getTextureManager().bindTexture(PvnikaMod.pvnikaHeader);
        int[] values = translateInvLogoPosition(166, rows);
        new DrawUtils().drawTexture(values[0], values[1], 1, 1, 120, 40, 1.0f);
    }

}
