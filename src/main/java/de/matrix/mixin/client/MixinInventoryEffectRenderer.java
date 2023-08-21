package de.matrix.mixin.client;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer{

    public MixinInventoryEffectRenderer(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Inject(method = "updateActivePotionEffects", at = @At("TAIL"))
    private void onUpdateActivePotionEffects(CallbackInfo ci){
        if (PvnikaMod.getInstance().getConfig().visualsSettings.disableShift)
            this.guiLeft = (this.width - this.xSize) / 2;
    }


}
