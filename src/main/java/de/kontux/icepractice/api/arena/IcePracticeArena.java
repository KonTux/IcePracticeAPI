package de.kontux.icepractice.api.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IcePracticeArena {

    void create(Player player);

    boolean loadFromConfig();

    void saveToConfig();

    void delete();

    String getName();

    Location getPos1();

    void setPos1(Location pos1);

    Location getPos2();

    void setPos2(Location pos2);

    Location getCenter();

    void setCenter(Location center);

    boolean isSumo();

    void setSumo(boolean sumo);

    void setSpleef(boolean spleef);

    void setHcf(boolean hcf);

    boolean isSpleef();

    boolean isHcf();

    boolean isBuild();

    void setBuild();
}
