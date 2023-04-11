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

    /**
     * @author
     * @reason
     */
    /*@Overwrite
    protected void drawPing(int i, int j, int k, NetworkPlayerInfo networkPlayerInfoIn) {
        this.mc = PvnikaMod.getInstance().mc;
        NetHandlerPlayClient netHandlerPlayClient = this.mc.getNetHandler();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(icons);
        int l = 0;
        byte m;
        int pingColor;
        String ping = networkPlayerInfoIn.getResponseTime() + "ms";
        int strWidth = this.mc.fontRendererObj.getStringWidth(ping);
        if (networkPlayerInfoIn.getResponseTime() < 0) {
            pingColor = 65280;
            m = 5;
        } else if (networkPlayerInfoIn.getResponseTime() < 150) {
            pingColor = 65280;
            m = 0;
        } else if (networkPlayerInfoIn.getResponseTime() < 300) {
            pingColor = 8453888;
            m = 1;
        } else if (networkPlayerInfoIn.getResponseTime() < 600) {
            pingColor = 16776960;
            m = 2;
        } else if (networkPlayerInfoIn.getResponseTime() < 1000) {
            pingColor = 16744448;
            m = 3;
        } else {
            pingColor = 16711680;
            m = 4;
        }

        this.zLevel += 100.0F;
        if(PvnikaMod.getInstance().getConfig().pingOnTab){
            this.mc.fontRendererObj.drawStringWithShadow(ping, j + i - strWidth, k, pingColor);
        } else {
            this.drawTexturedModalRect(j + i - 11, k, 0 + l * 10, 176 + m * 8, 10, 8);
        }
        this.zLevel -= 100.0F;
    }*/

    /**
     * @author
     * @reason
     */
    /*@Overwrite
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
        NetHandlerPlayClient netHandlerPlayClient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(netHandlerPlayClient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        Iterator iterator = list.iterator();

        int columns;
        int pingSize = 0;
        int maxPing = 0;
        while(iterator.hasNext()) {
            NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)iterator.next();
            columns = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkPlayerInfo));
            if (PvnikaMod.getInstance().getConfig().pingOnTab){
                pingSize = this.mc.fontRendererObj.getStringWidth(String.valueOf(networkPlayerInfo.getResponseTime()));
                maxPing = Math.max(maxPing, pingSize);
            }
            i = Math.max(i, columns);
            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                columns = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkPlayerInfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, columns);
            }
        }
        i += maxPing;


        list = list.subList(0, Math.min(list.size(), 80));
        int maxPlayers = list.size();
        int rows = maxPlayers;

        for(columns = 1; rows > 20; rows = (maxPlayers + columns - 1) / columns) {
            ++columns;
        }

        boolean bl = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int n;
        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                n = 90;
            } else {
                n = j;
            }
        } else {
            n = 0;
        }

        int columnWidth = Math.min(columns * ((bl ? 9 : 0) + i + n + 13), width - 50) / columns;
        int left = width / 2 - (columnWidth * columns + (columns - 1) * 5) / 2;
        int border = 10;
        int residual = columnWidth * columns + (columns - 1) * 5;
        List<String> list2 = null;
        List<String> list3 = null;
        Iterator iterator2;
        String string;
        if (this.header != null) {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);

            for(iterator2 = list2.iterator(); iterator2.hasNext(); residual = Math.max(residual, this.mc.fontRendererObj.getStringWidth(string))) {
                string = (String)iterator2.next();
            }
        }

        if (this.footer != null) {
            list3 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);

            for(iterator2 = list3.iterator(); iterator2.hasNext(); residual = Math.max(residual, this.mc.fontRendererObj.getStringWidth(string))) {
                string = (String)iterator2.next();
            }
        }

        int resColumns;
        if (list2 != null) {
            drawRect(width / 2 - residual / 2 - 1, border - 1, width / 2 + residual / 2 + 1, border + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for(iterator2 = list2.iterator(); iterator2.hasNext(); border += this.mc.fontRendererObj.FONT_HEIGHT) {
                string = (String)iterator2.next();
                resColumns = this.mc.fontRendererObj.getStringWidth(string);
                this.mc.fontRendererObj.drawStringWithShadow(string, (float)(width / 2 - resColumns / 2), (float)border, -1);
            }

            ++border;
        }

        drawRect(width / 2 - residual / 2 - 1, border - 1, width / 2 + residual / 2 + 1, border + rows * 9, Integer.MIN_VALUE);

        for(int t = 0; t < maxPlayers; ++t) {
            int columns2 = t / rows;
            resColumns = t % rows;
            int xPos = left + columns2 * columnWidth + columns2 * 5;
            int yPos = border + resColumns * 9;
            drawRect(xPos, yPos, xPos + columnWidth, yPos + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (t < list.size()) {
                NetworkPlayerInfo networkPlayerInfo2 = (NetworkPlayerInfo)list.get(t);
                String string2 = this.getPlayerName(networkPlayerInfo2);
                GameProfile gameProfile = networkPlayerInfo2.getGameProfile();
                if (bl) {
                    EntityPlayer entityPlayer = this.mc.theWorld.getPlayerEntityByUUID(gameProfile.getId());
                    boolean bl2 = entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.CAPE) && (gameProfile.getName().equals("Dinnerbone") || gameProfile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkPlayerInfo2.getLocationSkin());
                    int x = 8 + (bl2 ? 8 : 0);
                    int y = 8 * (bl2 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(xPos, yPos, 8.0F, (float)x, 8, y, 8, 8, 64.0F, 64.0F);
                    if (entityPlayer != null && entityPlayer.isWearing(EnumPlayerModelParts.HAT)) {
                        int z = 8 + (bl2 ? 8 : 0);
                        int aa = 8 * (bl2 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(xPos, yPos, 40.0F, (float)z, 8, aa, 8, 8, 64.0F, 64.0F);
                    }

                    xPos += 9;
                }

                if (networkPlayerInfo2.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    string2 = EnumChatFormatting.ITALIC + string2;
                    this.mc.fontRendererObj.drawStringWithShadow(string2, (float)xPos, (float)yPos, -1862270977);
                } else {
                    this.mc.fontRendererObj.drawStringWithShadow(string2, (float)xPos, (float)yPos, -1);
                }

                if (scoreObjectiveIn != null && networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    int ab = xPos + i + 1;
                    int ac = ab + n;
                    if (ac - ab > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, yPos, gameProfile.getName(), ab, ac, networkPlayerInfo2);
                    }
                }

                this.drawPing(columnWidth, xPos - (bl ? 9 : 0), yPos, networkPlayerInfo2);
            }
        }

        if (list3 != null) {
            border += rows * 9 + 1;
            drawRect(width / 2 - residual / 2 - 1, border - 1, width / 2 + residual / 2 + 1, border + list3.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for(iterator2 = list3.iterator(); iterator2.hasNext(); border += this.mc.fontRendererObj.FONT_HEIGHT) {
                string = (String)iterator2.next();
                resColumns = this.mc.fontRendererObj.getStringWidth(string);
                this.mc.fontRendererObj.drawStringWithShadow(string, (float)(width / 2 - resColumns / 2), (float)border, -1);
            }
        }

    }*/
    @Inject(method = "renderPlayerlist", at = @At("HEAD"), cancellable = true)
    private void correctHeaders(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn, CallbackInfo ci){
        PvnikaMod.getInstance().tabList.setHeader(header);
        PvnikaMod.getInstance().tabList.setFooter(footer);
        ci.cancel();
    }

}
