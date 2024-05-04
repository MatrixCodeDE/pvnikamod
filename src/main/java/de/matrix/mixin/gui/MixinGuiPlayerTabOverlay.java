package de.matrix.mixin.gui;

import com.google.common.collect.Ordering;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.PlayerComparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay extends Gui {

    private Minecraft mc;
    @Final
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator());
    @Shadow
    public abstract String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn);
    @Shadow
    protected IChatComponent header;
    @Shadow
    protected IChatComponent footer;
    @Shadow
    protected abstract void drawScoreboardValues(ScoreObjective scoreObjective, int i, String string, int j, int k, NetworkPlayerInfo networkPlayerInfo);


    @Inject(method = "renderPlayerlist", at = @At("HEAD"), cancellable = true)
    private void correctHeaders(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn, CallbackInfo ci){
        PvnikaMod.getInstance().tabList.setHeader(header);
        PvnikaMod.getInstance().tabList.setFooter(footer);
        ci.cancel();
    }

}
