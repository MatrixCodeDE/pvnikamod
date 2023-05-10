package de.matrix.mixin.gui;

import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.modutils.DrawUtils;
import de.matrix.pvnikamod.renderer.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.ParagraphView;

@Mixin(GuiInventory.class)
public class MixinGuiInventory extends GuiScreen {

    @Inject(method = "drawGuiContainerBackgroundLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiInventory;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
    private void drawPvnikaLogo(float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(PvnikaMod.pvnikaHeader);
        int tySize = 166;
        int[] values = RenderManager.translateInvLogoPosition(tySize);
        new DrawUtils().drawTexture(values[0], values[1], 1, 1, 120, 40, 1.0f);
    }

}
