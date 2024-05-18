package de.matrix.mixin.gui.container;

import de.matrix.pvnikamod.renderer.CustomRenderManager;
import net.minecraft.client.gui.inventory.GuiCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCrafting.class)
public class MixinGuiCrafting {

    @Inject(method = "drawGuiContainerBackgroundLayer", at = @At("TAIL"))
    private void drawPvnikaLogo(float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        CustomRenderManager.renderPvnikaLogo();
    }

}
