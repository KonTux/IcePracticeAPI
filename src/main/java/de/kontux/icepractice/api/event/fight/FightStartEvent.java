package de.kontux.icepractice.api.event.fight;

import de.kontux.icepractice.api.event.IcePracticeEvent;
import de.kontux.icepractice.api.match.IcePracticeFight;
import org.bukkit.event.HandlerList;

public class FightStartEvent extends IcePracticeEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final IcePracticeFight fight;

    public FightStartEvent(IcePracticeFight fight) {
        super("FightStartEvent");
        this.fight = fight;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public IcePracticeFight getFight() {
        return fight;
    }
}
