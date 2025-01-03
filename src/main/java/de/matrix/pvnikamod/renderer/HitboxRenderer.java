package de.matrix.pvnikamod.renderer;

import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.IngameUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class HitboxRenderer {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;
    private RenderManager renderManager;
    private Entity entity;
    private int renderGroup;
    private boolean storeDebugBoundingBox;
    public Entity primaryTarget;

    public HitboxRenderer() {
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.renderManager = this.mc.getRenderManager();
        this.resetPrimaryTarget();
    }

    public void assignPrimaryTarget(){
        Entity target = IngameUtils.getRTLock();
        setPrimaryTarget(target);
    }

    public void setPrimaryTarget(Entity entity){
        this.primaryTarget = entity;
    }

    public void resetPrimaryTarget(){
        this.primaryTarget = null;
    }

    public void checkForPrimaryTarget(){
        boolean exists = IngameUtils.isEntityExisting(primaryTarget);
        if (!exists)
            this.primaryTarget = null;
    }

    public void renderHitboxes(Entity entity, double x, double y, double z)
    {
        if (this.renderManager == null){
            this.renderManager = this.mc.getRenderManager();
        }
        if (entity == null)
            return;
        if (primaryTarget != null && !entity.isInvisible()){
            checkForPrimaryTarget();
            if (entity == primaryTarget){
                this.renderBoundingBox(entity, x, y, z, 255, 0, 0, 255, 5.0f);
            }
            return;
        }
        if (!entity.isInvisible() && this.renderManager != null){
            this.storeDebugBoundingBox = this.renderManager.isDebugBoundingBox();
            this.renderManager.setDebugBoundingBox(false);
            if (!this.config.hitboxSettings.advanced) {
                renderGroup = 0;
            } else
            if (entity instanceof EntityPlayer){
                renderGroup = 1;
            } else
            if (entity instanceof EntityAmbientCreature || entity instanceof EntityAgeable){
                renderGroup = 2;
            } else
            if (entity instanceof EntityMob){
                renderGroup = 3;
            } else
            if (entity instanceof EntityItem){
                renderGroup = 4;
            } else
            if (entity instanceof EntityThrowable || entity instanceof EntityFishHook || entity instanceof EntityFireball || entity instanceof EntityArrow || entity instanceof EntityEnderEye || entity instanceof EntityFireworkRocket){
                renderGroup = 5;
            } else
            if (!(entity instanceof EntityHanging)){
                renderGroup = 6;
            } else {
                this.renderManager.setDebugBoundingBox(storeDebugBoundingBox);
                return;
            }
            int i = renderGroup;
            if (this.config.hitboxSettings.activated[i]) {
                if (!this.config.hitboxSettings.chroma[i]) {
                    this.renderBoundingBox(entity, x, y, z, this.config.hitboxSettings.red[i], this.config.hitboxSettings.green[i], this.config.hitboxSettings.blue[i], this.config.hitboxSettings.alpha[i]);
                } else {
                    Color chroma = getChroma(i);
                    this.renderBoundingBox(entity, x, y, z, chroma.getRed(), chroma.getGreen(), chroma.getBlue(), chroma.getAlpha());
                }
            }

        }
    }

    private Color getChroma(int group) {
        float time = (float)((System.currentTimeMillis() * (this.config.hitboxSettings.speed[group])) % (5000L)) / 5000.0F;
        Color color =  Color.getHSBColor(time, 0.8F, 0.8F);
        return color;
    }

    private void renderBoundingBox(Entity entity, double x, double y, double z, int red, int green, int blue, int alpha){
        this.renderBoundingBox(entity, x, y, z, red, green, blue, alpha, 1.0f);
    }

    private void renderBoundingBox(Entity entity, double x, double y, double z, int red, int green, int blue, int alpha, float size) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        AxisAlignedBB boundingBox = entity.getEntityBoundingBox();
        double width = boundingBox.maxX - boundingBox.minX;
        double height = boundingBox.maxY - boundingBox.minY;
        double length = boundingBox.maxZ - boundingBox.minZ;
        this.drawOutlinedBoundingBox((new AxisAlignedBB(x, y, z, x + width, y + height, z + length)).offset(-width / 2.0, 0.0, -length / 2.0), red, green, blue, alpha, size);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }


    private void drawOutlinedBoundingBox(AxisAlignedBB axisalign, int red, int green, int blue, int alpha){
        this.drawOutlinedBoundingBox(axisalign, red, green, blue, alpha, 1.0f);
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB axisalign, int red, int green, int blue, int alpha, float width)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        GL11.glLineWidth(width);
        worldrenderer.pos(axisalign.minX, axisalign.minY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.minY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.minY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.minY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.minY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(axisalign.minX, axisalign.maxY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.maxY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.maxY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.maxY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.maxY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(axisalign.minX, axisalign.minY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.maxY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.minY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.maxY, axisalign.minZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.minY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.maxX, axisalign.maxY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.minY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        worldrenderer.pos(axisalign.minX, axisalign.maxY, axisalign.maxZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glLineWidth(1.0f);
    }

}
