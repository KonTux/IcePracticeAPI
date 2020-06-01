package de.kontux.icepractice.api.arena;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import org.bukkit.entity.Player;

import java.util.List;

public interface ArenaHandler {

    void create(Player player, String name);

    void delete(Player player, String name);

    IcePracticeArena getArena(String name);

    IcePracticeArena getRandomFreeArena(IcePracticeKit kit);

    List<IcePracticeArena> getArenas();

    void reload();
}
