package de.kontux.icepractice.api.event;

import de.kontux.icepractice.api.match.IcePracticeFight;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Collections;
import java.util.List;

public class FightEndEvent extends IcePracticeEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final IcePracticeFight fight;
    private final List<Player> winners, losers;
    private int winnerElo, loserElo = 0;

    public FightEndEvent(IcePracticeFight fight, List<Player> winners, List<Player> losers) {
        super("FightEndEvent");
        this.fight = fight;
        this.winners = winners;
        this.losers = losers;
    }

    public FightEndEvent(IcePracticeFight fight, Player winner, Player loser, int winnerElo, int loserElo) {
        this(fight, Collections.singletonList(winner), Collections.singletonList(loser));
        this.winnerElo = winnerElo;
        this.loserElo = loserElo;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public IcePracticeFight getFight() {
        return fight;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
    }

    public boolean affectsElo() {
        return fight.isRanked();
    }

    public void setWinnerElo(int winnerElo) {
        this.winnerElo = winnerElo;
    }

    public void setLoserElo(int loserElo) {
        this.loserElo = loserElo;
    }

    public int getWinnerElo() {
        return winnerElo;
    }

    public int getLoserElo() {
        return loserElo;
    }
}
