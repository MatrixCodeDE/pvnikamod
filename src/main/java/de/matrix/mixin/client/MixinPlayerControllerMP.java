package de.matrix.mixin.client;

import de.matrix.pvnikamod.listener.Implementation;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Shadow
    protected float curBlockDamageMP;

    @Inject(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)F", shift = At.Shift.AFTER))
    public void setCurBlockDamageMP(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> cir){
        Implementation implementation = PvnikaMod.getInstance().implementation;
        implementation.brokenBlock = null;
        implementation.setCurrentBlockDamage(this.curBlockDamageMP);
    }

    @Inject(method = "onPlayerDestroyBlock", at = @At("HEAD"))
    public void destroyCurBlockDamageMP(BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir){
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        Implementation implementation = PvnikaMod.getInstance().implementation;
        implementation.brokenBlock = block;
        implementation.setCurrentBlockDamage(0.0f);
        implementation.setBroken(true);
    }

    @Inject(method = "resetBlockRemoving()V", at = @At("HEAD"))
    public void resetCurBlockDamageMP(CallbackInfo ci){
        Implementation implementation = PvnikaMod.getInstance().implementation;
        implementation.brokenBlock = null;
        implementation.setCurrentBlockDamage(0.0f);
    }

}
