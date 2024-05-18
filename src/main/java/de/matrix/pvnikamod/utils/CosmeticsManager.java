package de.matrix.pvnikamod.utils;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;

public class CosmeticsManager {
    private static final Set<String> playersWithCosmetics = new HashSet<>();

    public static void addPlayerWithCosmetic(String playerName) {
        playersWithCosmetics.add(playerName);
    }

    public static boolean hasCosmetic(EntityPlayer player) {
        return playersWithCosmetics.contains(player.getName());
    }
}
