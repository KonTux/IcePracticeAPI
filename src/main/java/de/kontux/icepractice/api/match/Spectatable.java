package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Spectatable {

    ItemStack LEAVE_ITEM = ItemBuilder.create(Material.REDSTONE, "§cBack To Spawn", null);

    void addSpectator(Player player, boolean announce);

    /**
     * Removes a spectator from a match.
     *
     * @param player   The player to leave spectator mode
     * @param announce If all other players in the match should see the leave message
     */
    void removeSpectator(Player player, boolean announce);

    List<Player> getSpectators();
}
