package de.iani.cubesideutils.fabric.location;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;

public class LocationUtils {
    public static Location getPlayerLocation(PlayerEntity player) {
        return new Location((ClientWorld) player.getWorld(), player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch());
    }
}
