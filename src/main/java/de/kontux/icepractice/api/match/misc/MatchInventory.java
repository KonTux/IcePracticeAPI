package de.kontux.icepractice.api.match.misc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MatchInventory {

    /**
     * Initializes the inventory. Should be called before a player dies.
     */
    void initializeInventory();

    /**
     * Gets the player whose after-match-inventory will open when clicking the arrow
     * @return The next player
     */
    Player getNext();

    /**
     * Gets the Player this inventory is from
     * @return The player
     */
    Player getTarget();


    /**
     * Gets all items ready to be set into a 5*9 inventory
     * @return All contents
     */
    ItemStack[] getContents();

}
