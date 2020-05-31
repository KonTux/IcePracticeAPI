package de.kontux.icepractice.api.user;

import de.kontux.icepractice.api.IcePractice;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CustomUserKit {

    String getCustomName();

    void equip(Player player);

    ItemStack[] getInventory();

    void setInventory(ItemStack[] inventory);

    int getNumber();

    IcePractice getKit();

    void setCustomName();
}
