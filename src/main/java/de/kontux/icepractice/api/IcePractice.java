package de.kontux.icepractice.api;

import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.locations.IcePracticeSpawnpoint;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.match.misc.MatchInventory;
import de.kontux.icepractice.api.nms.NmsApi;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.playerstates.PlayerStateManager;
import de.kontux.icepractice.api.user.UserData;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;

public interface IcePractice {
    String getVersion();

    IcePracticeKit getKit(String name);

    IcePracticeArena getArena(String name);

    NmsApi getNmsApi();

    PlayerStateManager getPlayerStateManager();

    IcePracticeFightRegistry getFightRegistry();

    void broadcast(List<Player> players, String message);

    void broadcast(List<Player> players, BaseComponent message);

    MatchInventory generateMatchInventory(Player player, IcePracticeFight fight);

    MatchMessages getMatchMessages();

    IcePracticeSpawnpoint getSpawnpointManager();

    UserData getUserData(Player player);
}
