package de.kontux.icepractice.api.playerstates;

import org.bukkit.entity.Player;

public interface PlayerStateManager {

    PlayerState getState(Player player);

    void setState(Player player, PlayerState state);
}
