package de.kontux.icepractice.api;

import de.kontux.icepractice.api.arena.ArenaHandler;
import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.config.PluginSettings;
import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.kit.KitHandler;
import de.kontux.icepractice.api.locations.IcePracticeSpawnpoint;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.match.misc.MatchInventory;
import de.kontux.icepractice.api.nms.NmsApi;
import de.kontux.icepractice.api.playerstates.PlayerStateManager;
import de.kontux.icepractice.api.protocol.IcePracticeEntityHider;
import de.kontux.icepractice.api.user.UserData;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;

public interface IcePractice {
    String getVersion();

    NmsApi getNmsApi();

    PlayerStateManager getPlayerStateManager();

    IcePracticeFightRegistry getFightRegistry();

    void broadcast(List<Player> players, String message);

    void broadcast(List<Player> players, BaseComponent message);

    MatchInventory generateMatchInventory(Player player, IcePracticeFight fight);

    MatchMessages getMatchMessages();

    IcePracticeSpawnpoint getSpawnpointManager();

    UserData getUserData(Player player);

    FightStatistics constructFightStatistics(IcePracticeFight fight);

    ArenaHandler getArenaHandler();

    IcePracticeEntityHider getEntityHider();

    IcePracticeFight getFightByPlayer(Player player);

    void registerInventoryMenu(Player player, InventoryGui inventory);

    KitHandler getKitHandler();

    PluginSettings getPluginSettings();
}
