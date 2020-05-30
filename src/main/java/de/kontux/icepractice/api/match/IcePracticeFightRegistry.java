package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import org.bukkit.entity.Player;

import java.util.List;

public interface IcePracticeFightRegistry {

    void addMatch(IcePracticeFight fight);

    void removeMatch(IcePracticeFight fight);

    IcePracticeFight getFightByPlayer(Player player);

    int getPlayersPlaying(IcePracticeKit kit, boolean ranked);

    List<IcePracticeFight> getAllFights();

    List<Player> getAllPlayers();
}
