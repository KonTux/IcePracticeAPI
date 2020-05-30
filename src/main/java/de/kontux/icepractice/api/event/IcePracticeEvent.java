package de.kontux.icepractice.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.util.Objects;

public abstract class IcePracticeEvent extends Event implements Cancellable {

    private final String name;
    private boolean cancel = false;

    public IcePracticeEvent(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public final void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public final String getEventName() {
        return name;
    }
}
