package de.kontux.icepractice.api;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.nms.NmsApi;
import org.bukkit.Bukkit;

public final class IcePracticeAPI {

    private static IcePractice practice;

    public static void setIcePractice(IcePractice icePractice) {
        if (practice != null) {
            throw new IllegalStateException("Practice instance for IcePracticeAPI has already been set.");
        }

        practice = icePractice;
        Bukkit.getLogger().info("IcePracticeAPI is on version " + icePractice.getVersion());
    }

    /**
     * Returns an kit due to its name. Will return null if there is no kit with this name
     * @param name The kit's name
     * @return The kit, null if not found
     */
    public static IcePracticeKit getKit(String name) {
        return practice.getKit(name);
    }

    /**
     * Returns an arena due to its name. Will return null if there is no arena with this name
     * @param name The arena's name
     * @return The arena, null if not found
     */
    public static IcePracticeArena getArena(String name) {
        return practice.getArena(name);
    }

    /**
     * IcePractice has a built in api for several version specific CraftBukkit and Minecraft classes. You can use them cross version as long as
     * the version of bukkit is supported by IcePractice.
     * @return An NMS API instance which you can use.
     */
    public static NmsApi gNmsApi() {
        return practice.getNmsApi();
    }

    public static IcePracticeFightRegistry getFightRegistry() {
        return practice.getFightRegistry();
    }
}
