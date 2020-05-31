package de.kontux.icepractice.api.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Helps you to create items without much code. You can use one instance for multiple items. This can for example let you create  custom item in just one line
 * while still modifying its {@link ItemMeta}
 */

public class ItemBuilder {

    public ItemBuilder() {
    }

    /**
     * Returns you an ItemStack with all given properties.
     * @param material May not be null, the item's material.
     * @param name The item's custom name. Use null for default name.
     * @param lore The item's lore. Use null for an empty lore.
     * @return The {@link ItemStack} with all the given properties.
     */

    public static ItemStack create(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }
    public static ItemStack create(Material material, String name, short durability,  List<String> lore) {
        ItemStack item = new ItemStack(material);
        item.setDurability(durability);
        ItemMeta itemMeta = item.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }


    public static ItemStack create(Material material, String name, List<String> lore,int amount, short damage) {
        ItemStack item = new ItemStack(material, amount, damage);
        ItemMeta itemMeta = item.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);

        return item;
    }
}
