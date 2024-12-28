package de.matrix.mixin.renderer;

import com.google.common.collect.Lists;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;
    @Shadow
    protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
    private static final float NAME_TAG_RANGE = 64.0F;
    private static final float NAME_TAG_RANGE_SNEAK = 32.0F;

    public MixinRendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn);
        this.mainModel = modelBaseIn;
        this.shadowSize = shadowSizeIn;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    protected boolean canRenderName(T entity) {

        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        if(entity == entityplayersp && PvnikaMod.getInstance().getConfig().generalSettings.ownNameTag && !(Minecraft.getMinecraft().currentScreen instanceof GuiInventory)){
            return true;
        }
        if (entity instanceof EntityPlayer && entity != entityplayersp) {
            Team team = entity.getTeam();
            Team team1 = entityplayersp.getTeam();
            if (team != null) {
                Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
                switch (team$enumvisible) {
                    case ALWAYS:
                        return true;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return team1 == null || team.isSameTeam(team1);
                    case HIDE_FOR_OWN_TEAM:
                        return team1 == null || !team.isSameTeam(team1);
                    default:
                        return true;
                }
            }
        }

        return Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !entity.isInvisibleToPlayer(entityplayersp) && entity.riddenByEntity == null;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void renderName(T entity, double x, double y, double z) {
        if (!MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Pre(entity, null, x, y, z))) {
            if (this.canRenderName(entity)) {
                double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
                float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
                if (d0 < (double)(f * f)) {
                    String s = entity.getDisplayName().getFormattedText();
                    float f1 = 0.02666667F;
                    GlStateManager.alphaFunc(516, 0.1F);
                    if (entity.isSneaking()) {
                        FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
                        GlStateManager.pushMatrix();
                        GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float)z);
                        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                        float pVX = this.renderManager.playerViewX * (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 || Minecraft.getMinecraft().gameSettings.thirdPersonView == 3 ? -1 : 1);
                        if (entity != Minecraft.getMinecraft().thePlayer){
                            pVX = this.renderManager.playerViewX;
                        }
                        GlStateManager.rotate(pVX, 1.0F, 0.0F, 0.0F);
                        GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                        GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                        GlStateManager.disableLighting();
                        GlStateManager.depthMask(false);
                        GlStateManager.enableBlend();
                        GlStateManager.disableTexture2D();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                        int i = fontrenderer.getStringWidth(s) / 2;
                        Tessellator tessellator = Tessellator.getInstance();
                        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                        worldrenderer.pos(-i - 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(-i - 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(i + 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        worldrenderer.pos(i + 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                        tessellator.draw();
                        GlStateManager.enableTexture2D();
                        GlStateManager.depthMask(true);
                        fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
                        GlStateManager.enableLighting();
                        GlStateManager.disableBlend();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                        GlStateManager.popMatrix();
                    } else
                    if (entity == Minecraft.getMinecraft().thePlayer){
                        renderOwnLabel(entity, s, x, y, z);
                    } else {
                        this.renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (double)(entity.height / 2.0F) : 0.0), z, s, 0.02666667F, d0);
                    }
                }
            }

            //MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Specials.Post(entity, this, x, y, z));
        }
    }

    protected void renderOwnLabel(T entityIn, String str, double x, double y, double z) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        FontRenderer fontRenderer = this.getFontRendererFromRenderManager();
        float f = 1.6F;
        float g = 0.016666668F * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0F, (float)y + entityIn.height + 0.5F, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        float pVX = renderManager.playerViewX * (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 || Minecraft.getMinecraft().gameSettings.thirdPersonView == 3 ? -1 : 1);
        GlStateManager.rotate(pVX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-g, -g, g);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        int i = 0;

        int j = fontRenderer.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(-j - 1, -1 + i, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(-j - 1, 8 + i, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(j + 1, 8 + i, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldRenderer.pos(j + 1, -1 + i, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(str, -fontRenderer.getStringWidth(str) / 2, i, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(str, -fontRenderer.getStringWidth(str) / 2, i, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }


}

