package de.kontux.icepractice.api.event.gui;

import de.kontux.icepractice.api.event.IcePracticeEvent;
import de.kontux.icepractice.api.gui.InventoryGui;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class InventoryGuiOpenEvent extends IcePracticeEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private InventoryGui inventory;

    public InventoryGuiOpenEvent(InventoryGui inventory) {
        super("InventoryGuiOpenEvent");
        this.inventory = Objects.requireNonNull(inventory);
    }

    public InventoryGui getInventory() {
        return inventory;
    }

    public void setInventory(InventoryGui inventory) {
        this.inventory = inventory;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
