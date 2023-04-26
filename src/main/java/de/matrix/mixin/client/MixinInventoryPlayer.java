package de.matrix.mixin.client;

import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryPlayer.class)
public abstract class MixinInventoryPlayer {

    @Inject( at = @At( "HEAD" ), method = "changeCurrentItem(I)V", cancellable = true )
    private void onChangeCurrentItem(int direction, CallbackInfo ci) {
        if(PvnikaMod.getInstance().zoomUtils.blockItemScrolling){
            ci.cancel();
        }
    }

}
