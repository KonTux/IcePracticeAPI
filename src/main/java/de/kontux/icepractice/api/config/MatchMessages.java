package de.kontux.icepractice.api.config;

import org.bukkit.entity.Player;

public interface MatchMessages {

    String getCooldownMessage(int cooldown);

    String getFFAStartingMessage(String kit);

    String getStartMessage();

    String getStartingMessage(Player opponent, String kit);

    String getTeamStartingMessage(Player opponent, String kit);

    String getEndMessage();

    String getNoArenaMessage();

    String getComboMessage();

    String getNowSpectatingMessage(Player player);

    String getNoLongerSpectatingMessage(Player player);

    String getLeftSpectatingMessage();

    String getSoloSpectatorMessage(Player player1, Player player2, String kit);

    String getTeamSpectatorMessage(Player team1, Player team2, String kit);

    String getFFASpectatorMessage(String kit);

    String getTeamFightDeathMessage(Player dead, Player killer);

    String getTextWithColour(String path);
}