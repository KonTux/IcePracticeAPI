package de.kontux.icepractice.api.nms;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface NmsApi {

    void sendEntityStatusPacket(Entity entity, List<Player> sendPacketTo, byte status);

    void sendWorldEventPacket(Player player, int id, double x, double y, double z, int data);

    void sendEntityVelocityPacket(Entity entity, double x, double y, double z, List<Player> sendPacketTo);

    void sendBlockChangePacket(Block block, Player player);

    void injectPacketListener(Player player);

    int getPing(Player player);
}
