package de.iani.cubesideutils.fabric.scheduler;

import de.iani.cubesideutils.fabric.CubesideUtilsFabricMod;
import net.minecraft.server.MinecraftServer;

public class Helper {
    public static void initialize(CubesideUtilsFabricMod mod, MinecraftServer server) {
        Scheduler.INSTANCE.initialize(server.getRunningThread());
    }

    public static void processOnTick(CubesideUtilsFabricMod mod) {
        Scheduler.INSTANCE.processOnTick();
    }
}
