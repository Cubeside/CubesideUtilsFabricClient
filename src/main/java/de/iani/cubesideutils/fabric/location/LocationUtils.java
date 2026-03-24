package de.iani.cubesideutils.fabric.location;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

public class LocationUtils {
    public static Location getPlayerLocation(Player player) {
        return new Location((ClientLevel) player.level(), player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
    }
}
