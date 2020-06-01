package de.kontux.icepractice.api.kit;

import org.bukkit.entity.Player;

import java.util.List;

public interface KitHandler {

    void createKit(Player player, String name);

    void setKitInventory(Player player, String name);

    void equipKit(Player player, IcePracticeKit kit);

    void viewKit(Player player, String name);

    void addCooldown(Player player, String name, int cooldown);

    void setIcon(Player player, String name);

    void deleteKit(Player player, String name);

    void setSumo(Player player, String name, boolean sumo);

    boolean isKit(String name);

    IcePracticeKit getKit(String name);

    List<IcePracticeKit> getKits();

    void reload();

    IcePracticeKit getSumoEventKit();
}
