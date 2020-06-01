package de.kontux.icepractice.api.gui;

import de.kontux.icepractice.api.IcePracticeAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryGui {

    protected final Inventory inventory;
    protected final Player player;

    public InventoryGui(Player player, String title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.player = player;
    }

    public final void openMenu() {
        setItems();
        player.openInventory(inventory);
        IcePracticeAPI.openInventoryMenu(player, this);
    }

    protected abstract void setItems();
    public abstract void runAction(ItemStack item);
}
