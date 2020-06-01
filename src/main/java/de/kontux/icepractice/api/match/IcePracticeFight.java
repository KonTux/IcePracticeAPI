package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import org.bukkit.entity.Player;

import java.util.List;

public interface IcePracticeFight {

    void runMatch();

    void killPlayer(Player dead, Player killer);

    void endFight(Player lastDead);

    /**
     * Called when cooldown expired and the actual match starts
     */
    void expireCooldown();

    void addSpectator(Player player, boolean announce);

    /**
     * Removes a spectator from a match.
     * @param player The player to leave spectator mode
     * @param announce If all other players in the match should see the leave message
     */
    void removeSpectator(Player player, boolean announce);

    /**
     * Determines the winner team based on the last person who died
     * @param lastDead The person who died to trigger the match to end
     * @return A list of players which are in the winner team.
     */
    List<Player> getWinnerTeam(Player lastDead);

    /**
     * Determines the loser team based on the last person who died
     * @param lastDead The person who died to trigger the match to end
     * @return A list of players which are in the loser team.
     */
    List<Player> getLoserTeam(Player lastDead);

    /**
     * Sends the inventory and elo change (if ranked) messages to all players and spectators at the end of the match.
     * @param winners The player(s) who won the match
     * @param losers The player(s) who lost the match
     */
    void sendEndMessages(List<Player> winners, List<Player> losers);

    /**
     * Returns the next player or the opponent in a duel. This is only for players who are still alive
     * @param player The player whose opposite you want
     * @return The opposite or next player
     */
    Player getNext(Player player);

    /**
     * This is like a circle that goes through all players that have participated in the fight. It will return the next index or the first item
     * from the {@link Player} collection.
     * @param player The player who is in front of the player you are looking for.
     * @return The next player
     */
    Player getNextTotal(Player player);

    /**
     * Returns all players currently playing in this match
     * @return All players in the match
     */
    List<Player> getPlayers();

    List<Player> getSpectators();

    /**
     * Returns the kit that is used in this fight.
     * @return the kit.
     */
    IcePracticeKit getKit();

    /**
     * Returns the arena that is used in this fight.
     * @return the arena.
     */
    IcePracticeArena getArena();

    /**
     * Tells you if this is a ranked fight and if it will affect the players' elo when the match has finished.
     * @return if the match is ranked
     */
    boolean isRanked();

    FightStatistics getMatchStatistics();
}
