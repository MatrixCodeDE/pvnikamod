package de.matrix.pvnikamod.utils;

import com.google.common.collect.ComparisonChain;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;

@SideOnly(Side.CLIENT)
public class PlayerComparator implements Comparator<NetworkPlayerInfo> {
    public PlayerComparator() {
    }

    public int compare(NetworkPlayerInfo networkPlayerInfo, NetworkPlayerInfo networkPlayerInfo2) {
        ScorePlayerTeam scorePlayerTeam = networkPlayerInfo.getPlayerTeam();
        ScorePlayerTeam scorePlayerTeam2 = networkPlayerInfo2.getPlayerTeam();
        return ComparisonChain.start().compareTrueFirst(networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR, networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scorePlayerTeam != null ? scorePlayerTeam.getRegisteredName() : "", scorePlayerTeam2 != null ? scorePlayerTeam2.getRegisteredName() : "").compare(networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo2.getGameProfile().getName()).result();
    }

}
