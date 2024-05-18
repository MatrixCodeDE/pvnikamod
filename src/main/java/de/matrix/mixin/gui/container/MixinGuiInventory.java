package de.matrix.mixin.gui.container;

import de.matrix.pvnikamod.renderer.CustomRenderManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiInventory.class)
public class MixinGuiInventory extends GuiScreen {

    //@Inject(method = "drawGuiContainerBackgroundLayer(FII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiInventory;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
    @Inject(method = "drawGuiContainerBackgroundLayer(FII)V", at = @At( "HEAD" ))
    private void drawPvnikaLogo(float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        CustomRenderManager.renderPvnikaLogo();
    }

}
