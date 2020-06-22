package de.kontux.icepractice.api.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IcePracticeKit {

    void setInventory(ItemStack[] items, ItemStack[] armor);

    void save();

    void delete();

    void equipKit(Player player);

    int getPearlCooldown();

    void setPearlCooldown(int cooldown);

    String getName();

    ItemStack[] getItems();

    ItemStack[] getArmor();

    ItemStack getIcon();

    void setIcon(ItemStack icon);

    boolean isRanked();

    void setRanked(boolean ranked);

    boolean isSumo();

    void setSumo(boolean sumo);

    boolean isCombo();

    void setCombo(boolean combo);

    String toString();

    void setAllowBuild(boolean allowBuild);

    boolean isBuild();

    boolean isHcf();

    void setHcf(boolean hcf);

    boolean isSpleef();

    void setSpleef(boolean spleef);

    boolean isEditable();

    void setEditable(boolean editable);

    boolean allowRegen();

    void setAllowRegen(boolean allowRegen);

    boolean allowChestEditing();

    void setAllowChestEditing(boolean allowChestEditing);
}
