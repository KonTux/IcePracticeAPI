package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.misc.CoolDown;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.api.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class CustomFight implements IcePracticeFight {

    protected static final MatchMessages MESSAGES = IcePracticeAPI.getMatchMessages();

    protected final List<Player> players;
    protected final IcePracticeKit kit;
    protected IcePracticeArena arena;
    private final FightStatistics statistics = IcePracticeAPI.constructFightStatistics(this);

    protected final boolean ranked;
    protected final Plugin plugin;
    private final List<Player> all;
    protected final List<Player> spectators = new ArrayList<>();

    public CustomFight(Plugin plugin, List<Player> players, IcePracticeKit kit, boolean ranked) {
        this.plugin = Objects.requireNonNull(plugin);
        this.players = Objects.requireNonNull(players);
        this.all = Collections.unmodifiableList(players);
        this.kit = Objects.requireNonNull(kit);
        this.ranked = ranked;
    }

    protected final void startCooldown() {
        new CoolDown(plugin, this).runCooldown();
    }

    protected void spawnPlayer(Player player, Location location) {
        player.teleport(location);
        IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.STARTING_MATCH);
        handleEntityHider(player);
    }

    protected final void equipKit(Player player) {
        List<CustomUserKit> customKits = null;

        if (!player.hasMetadata("NPC")) {
            customKits = IcePracticeAPI.getUserData(player).getCustomKits(kit);
        }

        if (customKits == null || customKits.isEmpty()) {
            kit.equipKit(player);
        } else {
            for (CustomUserKit customKit : customKits) {
                player.getInventory().addItem(ItemBuilder.create(Material.BOOK, customKit.getCustomName(), null));
            }

            player.sendMessage(ChatColor.YELLOW + "Please select a custom kit.");
        }
    }

    @Override
    public final void killPlayer(Player dead, Player killer) {
        IcePracticeAPI.getPlayerStateManager().setState(dead, PlayerState.IDLE);
        IcePracticeAPI.generateMatchInventory(dead, this).initializeInventory();

        //Plays fake death animation. Must be delayed because it cannot be sent in the same tick as the EntityDamageEvent occurs
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> IcePracticeAPI.getNmsApi().sendEntityStatusPacket(dead, players, (byte) 3), 3);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> IcePracticeAPI.getNmsApi().sendEntityStatusPacket(dead, spectators, (byte) 3), 3);

        if (killer != null) {
            IcePracticeAPI.broadcast(players, MESSAGES.getTeamFightDeathMessage(dead, killer));
            IcePracticeAPI.broadcast(spectators, MESSAGES.getTeamFightDeathMessage(dead, killer));
        } else {
            IcePracticeAPI.broadcast(players, ChatColor.RED + dead.getDisplayName() + " died.");
            IcePracticeAPI.broadcast(spectators, ChatColor.RED + dead.getDisplayName() + " died.");
        }

        players.remove(dead);
        processDeath(dead, killer);

        if (isMatchOver()) {
            spectators.add(dead);
            endFight(dead);
        } else {
            addSpectator(dead, false);
        }
    }

    protected abstract void processDeath(Player dead, Player killer);

    protected abstract boolean isMatchOver();

    @Override
    public void removeSpectator(Player player, boolean announce) {
        spectators.remove(player);

        if (announce) {
            IcePracticeAPI.broadcast(players, MESSAGES.getNoLongerSpectatingMessage(player));
            IcePracticeAPI.broadcast(spectators, MESSAGES.getNoLongerSpectatingMessage(player));
        }

        IcePracticeAPI.getSpawnpointManager().teleportToSpawn(player);
        player.sendMessage(MESSAGES.getLeftSpectatingMessage());
    }

    @Override
    public final Player getNext(Player player) {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("This player was not part of this match");
        }

        int ownIndex = players.indexOf(player);
        if (ownIndex == players.size() - 1) {
            return players.get(0);
        } else {
            return players.get(ownIndex + 1);
        }
    }

    @Override
    public final Player getNextTotal(Player player) {
        int ownIndex = all.indexOf(player);

        if (ownIndex == players.size() - 1) {
            return all.get(0);
        } else {
            return all.get(ownIndex + 1);
        }
    }

    protected final void registerFight() {
        IcePracticeAPI.getFightRegistry().addMatch(this);
    }

    protected final void unregisterFight() {
        IcePracticeAPI.getFightRegistry().addMatch(this);
    }

    @Override
    public void expireCooldown() {
        for (Player player : players) {
            IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.MATCH);
        }

        IcePracticeAPI.broadcast(players, MESSAGES.getStartMessage());
        IcePracticeAPI.broadcast(spectators, MESSAGES.getStartMessage());
    }

    protected final void handleEntityHider(Player player) {
        for (Player current : player.getWorld().getPlayers()) {
            if (players.contains(current)) {
                IcePracticeAPI.getEntityHider().showEntity(current, player);
                IcePracticeAPI.getEntityHider().showEntity(player, current);
            } else if (spectators.contains(current)){
                IcePracticeAPI.getEntityHider().showEntity(player, current);
                IcePracticeAPI.getEntityHider().hideEntity(player, current);
            } else {
                IcePracticeAPI.getEntityHider().hideEntity(player, current);
                IcePracticeAPI.getEntityHider().hideEntity(current, player);
            }
        }
    }

    @Override
    public final FightStatistics getMatchStatistics() {
        return statistics;
    }

    @Override
    public final boolean isRanked() {
        return ranked;
    }

    @Override
    public final List<Player> getPlayers() {
        return players;
    }

    @Override
    public final List<Player> getSpectators() {
        return spectators;
    }

    @Override
    public final IcePracticeKit getKit() {
        return kit;
    }

    @Override
    public final IcePracticeArena getArena() {
        return arena;
    }
}
