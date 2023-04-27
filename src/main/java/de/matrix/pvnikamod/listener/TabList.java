package de.matrix.pvnikamod.listener;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import de.matrix.pvnikamod.config.Config;
import de.matrix.pvnikamod.main.PvnikaMod;
import de.matrix.pvnikamod.utils.ColorUtil;
import de.matrix.pvnikamod.utils.PlayerComparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class TabList extends GuiPlayerTabOverlay {

    private final PvnikaMod mod;
    private final Config config;
    private final Minecraft mc;

    private IChatComponent footer;
    private IChatComponent header;
    private long lastTimeOpened;
    private boolean isBeingRendered;
    private final GuiIngame guiIngame;
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new PlayerComparator());

    public TabList(Minecraft minecraft, GuiIngame guiIngame){
        super(minecraft, guiIngame);
        this.mod = PvnikaMod.getInstance();
        this.config = this.mod.getConfig();
        this.mc = Minecraft.getMinecraft();
        this.guiIngame = this.mc.ingameGUI;
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Pre event){
        if (event.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST){
            event.setCanceled(false);
            this.renderPlayerlist();
        }
    }

    public void renderPlayerlist() {
        int width = (new ScaledResolution(mc)).getScaledWidth();
        Scoreboard scoreboardIn = mc.theWorld.getScoreboard();
        ScoreObjective scoreObjectiveIn = scoreboardIn.getObjectiveInDisplaySlot(0);
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
            if (PvnikaMod.getInstance().getConfig().generalSettings.pingOnTab){
                pingSize = this.mc.fontRendererObj.getStringWidth(String.valueOf(networkPlayerInfo.getResponseTime()));
                maxPing = Math.max(maxPing, pingSize);
            }
            i = Math.max(i, columns);
            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                columns = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkPlayerInfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, columns);
            }
        }
        i += maxPing + 1;


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
                NetworkPlayerInfo networkPlayerInfo2 = list.get(t);
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
                        this.drawScoreboardValues(scoreObjectiveIn, yPos, gameProfile.getName(), ab, ac, networkPlayerInfo2, maxPing);
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

    }

    public void drawPing(int i, int j, int k, NetworkPlayerInfo networkPlayerInfoIn) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(icons);
        int l = 0;
        byte m;
        int pingColor;
        int ping = networkPlayerInfoIn.getResponseTime();
        String pingStr = ping + "ms";
        int strWidth = this.mc.fontRendererObj.getStringWidth(pingStr);
        if (ping < 0) {
            m = 5;
        } else if (ping < 150) {
            m = 0;
        } else if (ping < 300) {
            m = 1;
        } else if (ping < 600) {
            m = 2;
        } else if (ping < 1000) {
            m = 3;
        } else {
            m = 4;
        }

        if (ping < 50){
            // 0-49
            pingColor = ColorUtil.colorToDec(new Color(0, 255, 0));
        } else if (ping < 100) {
            // 50-99
            pingColor = ColorUtil.colorToDec(new Color(85, 255, 0));
        } else if (ping < 150) {
            // 100-149
            pingColor = ColorUtil.colorToDec(new Color(170, 255, 0));
        } else if (ping < 200) {
            // 150-199
            pingColor = ColorUtil.colorToDec(new Color(255, 255, 0));
        } else if (ping < 250) {
            // 200-249
            pingColor = ColorUtil.colorToDec(new Color(255, 170, 0));
        } else if (ping < 300) {
            // 250-299
            pingColor = ColorUtil.colorToDec(new Color(255, 85, 0));
        } else {
            // 300+
            pingColor = ColorUtil.colorToDec(new Color(255, 0, 0));
        }

        this.zLevel += 100.0F;
        if(PvnikaMod.getInstance().getConfig().generalSettings.pingOnTab){
            this.mc.fontRendererObj.drawStringWithShadow(pingStr, j + i - strWidth, k, pingColor);
        } else {
            this.drawTexturedModalRect(j + i - 11, k, l * 10, 176 + m * 8, 10, 8);
        }
        this.zLevel -= 100.0F;
    }

    private void drawScoreboardValues(ScoreObjective scoreObjective, int i, String string, int j, int k, NetworkPlayerInfo networkPlayerInfo, int pingLen) {
        int l = scoreObjective.getScoreboard().getValueFromObjective(string, scoreObjective).getScorePoints();
        if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(icons);
            if (this.lastTimeOpened == networkPlayerInfo.func_178855_p()) {
                if (l < networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 20);
                } else if (l > networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 10);
                }
            }

            if (Minecraft.getSystemTime() - networkPlayerInfo.func_178847_n() > 1000L || this.lastTimeOpened != networkPlayerInfo.func_178855_p()) {
                networkPlayerInfo.func_178836_b(l);
                networkPlayerInfo.func_178857_c(l);
                networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
            }

            networkPlayerInfo.func_178843_c(this.lastTimeOpened);
            networkPlayerInfo.func_178836_b(l);
            int m = MathHelper.ceiling_float_int((float)Math.max(l, networkPlayerInfo.func_178860_m()) / 2.0F);
            int n = Math.max(MathHelper.ceiling_float_int((float)(l / 2)), Math.max(MathHelper.ceiling_float_int((float)(networkPlayerInfo.func_178860_m() / 2)), 10));
            boolean bl = networkPlayerInfo.func_178858_o() > (long)this.guiIngame.getUpdateCounter() && (networkPlayerInfo.func_178858_o() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
            if (m > 0) {
                float f = Math.min((float)(k - j - 4) / (float)n, 9.0F);
                if (f > 3.0F) {
                    int o;
                    for(o = m; o < n; ++o) {
                        this.drawTexturedModalRect((float)j + (float)o * f, (float)i, bl ? 25 : 16, 0, 9, 9);
                    }

                    for(o = 0; o < m; ++o) {
                        this.drawTexturedModalRect((float)j + (float)o * f, (float)i, bl ? 25 : 16, 0, 9, 9);
                        if (bl) {
                            if (o * 2 + 1 < networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect((float)j + (float)o * f, (float)i, 70, 0, 9, 9);
                            }

                            if (o * 2 + 1 == networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect((float)j + (float)o * f, (float)i, 79, 0, 9, 9);
                            }
                        }

                        if (o * 2 + 1 < l) {
                            this.drawTexturedModalRect((float)j + (float)o * f, (float)i, o >= 10 ? 160 : 52, 0, 9, 9);
                        }

                        if (o * 2 + 1 == l) {
                            this.drawTexturedModalRect((float)j + (float)o * f, (float)i, o >= 10 ? 169 : 61, 0, 9, 9);
                        }
                    }
                } else {
                    float g = MathHelper.clamp_float((float)l / 20.0F, 0.0F, 1.0F);
                    int p = (int)((1.0F - g) * 255.0F) << 16 | (int)(g * 255.0F) << 8;
                    String string2 = "" + (float)l / 2.0F;
                    if (k - this.mc.fontRendererObj.getStringWidth(string2 + "hp") >= j) {
                        string2 = string2 + "hp";
                    }

                    this.mc.fontRendererObj.drawStringWithShadow(string2, (float)((k + j) / 2 - this.mc.fontRendererObj.getStringWidth(string2) / 2 - pingLen - 2), (float)i, p);
                }
            }
        } else {
            String string3 = EnumChatFormatting.YELLOW + "" + l;
            this.mc.fontRendererObj.drawStringWithShadow(string3, (float)(k - this.mc.fontRendererObj.getStringWidth(string3)) - pingLen - 2, (float)i, 16777215);
        }

    }

    public void setFooter(IChatComponent footerIn) {
        super.setFooter(footerIn);
        this.footer = footerIn;
    }

    public void setHeader(IChatComponent headerIn) {
        super.setHeader(headerIn);
        this.header = headerIn;
    }

    public void resetFooterHeader() {
        super.resetFooterHeader();
        this.header = null;
        this.footer = null;
    }

}
