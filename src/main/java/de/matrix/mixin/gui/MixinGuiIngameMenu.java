package de.matrix.mixin.gui;

import de.matrix.pvnikamod.gui.GuiCustomIngameMenu;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class MixinGuiIngameMenu extends GuiScreen {

    /**
     * @author Matrix
     * @reason Replace Ingame GUI
     */
    @Inject( method = "initGui()V", at = @At( "HEAD" ), cancellable = true )
    public void initGui(CallbackInfo ci) {
        if (PvnikaMod.getInstance().getConfig().generalSettings.customMenu){
            this.mc.displayGuiScreen(new GuiCustomIngameMenu(null));
            ci.cancel();
        }
    }

}
