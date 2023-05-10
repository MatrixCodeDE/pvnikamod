package de.matrix.mixin.gui;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.DrawUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(PvnikaMod.pvnikaHeader);
        int tySize = 166;
        int[] values = RenderManager.translateInvLogoPosition(166, this.inventoryRows);
        new DrawUtils().drawTexture(values[0], values[1], 1, 1, 120, 40, 1.0f);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

}
