package de.matrix.mixin.client;

import com.mojang.authlib.GameProfile;
import de.matrix.pvnikamod.utils.NameChangeMap;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(NetworkPlayerInfo.class)
public abstract class MixinNetworkPlayerInfo {

    @Final
    @Shadow
    private GameProfile gameProfile;

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    public void modifier(CallbackInfoReturnable<IChatComponent> cir){
        UUID uuid = this.gameProfile.getId();
        if (NameChangeMap.containsUUID(uuid)){
            IChatComponent component = new ChatComponentText(NameChangeMap.getFromUUID(uuid));
            cir.setReturnValue(component);
        }
    }

}
