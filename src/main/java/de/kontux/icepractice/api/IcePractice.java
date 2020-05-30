package de.kontux.icepractice.api;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.nms.NmsApi;
import de.kontux.icepractice.api.arena.IcePracticeArena;

public interface IcePractice {
    String getVersion();

    IcePracticeKit getKit(String name);

    IcePracticeArena getArena(String name);

    NmsApi getNmsApi();

    IcePracticeFightRegistry getFightRegistry();
}
