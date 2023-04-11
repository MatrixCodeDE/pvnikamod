package de.matrix.mixin.renderer;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.renderer.HitboxRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow
    private Minecraft mc;
    @Shadow
    protected abstract void orientCamera(float partialTicks);
    protected RenderManager renderManager;


    @Redirect(at = @At( value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;fovSetting:F", opcode = 180, ordinal = 0 ), method = {"getFOVModifier(FZ)F"})
    private float getFOV(GameSettings gameSettings){
        return PvnikaMod.getInstance().zoomUtils.changeFOV(gameSettings.fovSetting);
    }



}
