package de.kontux.icepractice.api.match.misc;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.match.IcePracticeFight;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CoolDown {

    private final Plugin plugin;
    private final IcePracticeFight fight;
    private int taskId;

    private int countdown = 5;

    public CoolDown(Plugin plugin, IcePracticeFight fight) {
        this.plugin = plugin;
        this.fight = fight;
    }

    public void runCooldown() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (countdown <= 0) {

                for (Player current : fight.getPlayers()) {
                    current.playNote(current.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F));
                }

                fight.expireCooldown();
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            for (Player player : fight.getPlayers()) {
                player.sendMessage(IcePracticeAPI.getMatchMessages().getCooldownMessage(countdown));
                player.playNote(player.getLocation(), Instrument.STICKS, Note.sharp(0, Note.Tone.C));
            }

            for (Player player : fight.getPlayers()) {
                player.sendMessage(IcePracticeAPI.getMatchMessages().getCooldownMessage(countdown));
                player.playNote(player.getLocation(), Instrument.STICKS, Note.sharp(0, Note.Tone.C));
            }

            countdown--;

        }, 0, 20);
    }
}
