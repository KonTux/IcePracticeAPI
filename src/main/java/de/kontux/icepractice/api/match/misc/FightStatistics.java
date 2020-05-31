package de.kontux.icepractice.api.match.misc;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface FightStatistics {

    void recoverArena();

    void addHit(Player player);

    void addPotion(Player player, double intensity);

    void addBrokenBlock(Block block);

    int getHitCount(UUID player);

    int getThrownPots(UUID player);

    int getMissedPots(UUID player);
}
