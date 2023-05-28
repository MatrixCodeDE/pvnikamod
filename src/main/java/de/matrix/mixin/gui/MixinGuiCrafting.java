package de.matrix.mixin.gui;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.DrawUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCrafting.class)
public class MixinGuiCrafting {

    @Inject(method = "drawGuiContainerBackgroundLayer", at = @At("TAIL"))
    private void drawPvnikaLogo(float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(PvnikaMod.pvnikaHeader);
        int tySize = 166;
        int[] values = RenderManager.translateInvLogoPosition(tySize);
        new DrawUtils().drawTexture(values[0], values[1], 1, 1, 120, 40, 1.0f);
    }

}
