package de.matrix.pvnikamod.modutils;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class DrawUtils extends Gui {

    private PvnikaMod mod;
    private Config config;
    private Minecraft mc;

    public DrawUtils(){
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
    }

    public void drawATexture(double x, double y, double width, double height, double maxWidth, double maxHeight, float alpha){
        GL11.glPushMatrix();
        double d0 = maxWidth / width;
        double d1 = maxHeight / height;
        GL11.glScaled(d0, d1, 0.0D);

        if (alpha <= 1.0F)
        {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        }

        this.drawTexturedModalRect(x / d0, y / d1, x / d0 + width, y / d1 + height);

        if (alpha <= 1.0F)
        {
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
        }

        GL11.glPopMatrix();
    }

    public void drawTexture(double x, double y, double scaleX, double scaleY, double pixelX, double pixelY, float alpha){
        GL11.glPushMatrix();
        double fx = scaleX * 255.0;
        double fy = scaleY * 255.0;
        double sx = pixelX / fx;
        double sy = pixelY / fy;
        GL11.glScaled(sx, sy, 0.0d);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
        this.drawTexturedModalRect(x / sx, y / sy, x / sx + fx, y / sy + fy);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    /*
     *  x:      Pos x
     *  y:      Pos y
     *  sx:     Size x
     *  sy:     Size y
     *  tsx:    Texture Size x
     *  tsy:    Texture Size y
     *  offx:   Offset x
     *  offy:   Offset y
     *  osx:    Size x of Rendered Texture
     *  osy:    Size y of Rendered Texture
     */
    public void drawPartedTexture(double x, double y, double sx, double sy, int tsx, int tsy, int offx, int offy, int osx, int osy){
        GL11.glPushMatrix();
        double scx = 1.0d; //fix glScaled x -> ( x / ( scx * sx ) )
        double scy = 1.0d; //fix glScaled y -> ( y / ( scy * sy ) )
        if (tsx < tsy){
            scx *= (double) tsx / (double) tsy;
        } else {
            scy *= (double) tsy / (double) tsx;
        }
        double skx = sx * scx;
        double sky = sy * scy;
        int maxx = (int) ((double) osx / (double) tsx * 256.0);
        int maxy = (int) ((double) osy / (double) tsy * 256.0);
        int fox = (int) ((double) offx / (double) tsx * 256.0);
        int foy = (int) ((double) offy / (double) tsy * 256.0);
        GL11.glScaled(skx, sky, 0.0d);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        super.drawTexturedModalRect((float) (x/(scx * sx)), (float) (y/(scy * sy)), fox, foy, maxx, maxy);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    void drawTexturedModalRect(double left, double top, double right, double bottom)
    {
        double d0 = 0.0D;
        double d1 = 0.0D;
        double d2 = right - left;
        double d3 = bottom - top;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(left + 0.0D, top + d3, (double)this.zLevel).tex((double)((float)(d0 + 0.0D) * f), (double)((float)(d1 + d3) * f1)).endVertex();
        worldRenderer.pos(left + d2, top + d3, (double)this.zLevel).tex((double)((float)(d0 + d2) * f), (double)((float)(d1 + d3) * f1)).endVertex();
        worldRenderer.pos(left + d2, top + 0.0D, (double)this.zLevel).tex((double)((float)(d0 + d2) * f), (double)((float)(d1 + 0.0D) * f1)).endVertex();
        worldRenderer.pos(left + 0.0D, top + 0.0D, (double)this.zLevel).tex((double)((float)(d0 + 0.0D) * f), (double)((float)(d1 + 0.0D) * f1)).endVertex();
        tessellator.draw();
    }

}
