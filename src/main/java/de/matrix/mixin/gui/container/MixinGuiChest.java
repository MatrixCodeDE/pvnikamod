package de.matrix.mixin.gui.container;

import de.matrix.pvnikamod.renderer.CustomRenderManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiChest.class)
public class MixinGuiChest extends GuiScreen {

    @Shadow
    protected int inventoryRows;

    @Inject(method = "drawGuiContainerBackgroundLayer", at = @At("HEAD"))
    private void drawPvnikaLogo(float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        CustomRenderManager.renderPvnikaLogo(this.inventoryRows);
    }

}
