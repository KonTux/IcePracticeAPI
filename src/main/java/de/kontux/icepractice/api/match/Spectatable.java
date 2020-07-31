package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface Spectatable {

    ItemStack LEAVE_ITEM = ItemBuilder.create(Material.REDSTONE, "§cBack To Spawn", null);

    List<Player> spectators = new ArrayList<>();

    void addSpectator(Player player, boolean announce);

    /**
     * Removes a spectator from a match.
     * @param player The player to leave spectator mode
     * @param announce If all other players in the match should see the leave message
     */
    void removeSpectator(Player player, boolean announce);

    default void handleSpectator(Player player) {
        spectators.add(player);
        IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.SPECTATING);

        for (int i = 0; i < 40; i++) {
            player.getInventory().clear(i);
        }

        player.getInventory().setItem(8, LEAVE_ITEM);

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    default List<Player> getSpectators() {
        return spectators;
    }
}
