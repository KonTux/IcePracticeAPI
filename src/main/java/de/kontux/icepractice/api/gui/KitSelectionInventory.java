package de.kontux.icepractice.api.gui;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import org.bukkit.entity.Player;

public abstract class KitSelectionInventory extends InventoryGui {

    public KitSelectionInventory(Player player) {
        super(player, IcePracticeAPI.getPrimary() + "Select a kit", 2*9);
    }

    public KitSelectionInventory(Player player, String title) {
        super(player, title, 2*9);
    }

    @Override
    protected void setItems() {
        for (IcePracticeKit kit : IcePracticeAPI.getKitHandler().getKits()) {
            inventory.addItem(kit.getIcon());
        }
    }

    protected final IcePracticeKit getKitByItemName(String name) {
        name = name.replace(IcePracticeAPI.getSecondary().toString(), "");
        return IcePracticeAPI.getKit(name);
    }
}
