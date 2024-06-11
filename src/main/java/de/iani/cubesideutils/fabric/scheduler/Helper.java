package de.iani.cubesideutils.fabric.scheduler;

import de.iani.cubesideutils.fabric.CubesideUtilsFabricClientMod;
import net.minecraft.client.MinecraftClient;

public class Helper {
    public static void initialize(MinecraftClient client) {
        Scheduler.INSTANCE.initialize(client.getThread());
    }

    public static void processOnTick(CubesideUtilsFabricClientMod mod) {
        Scheduler.INSTANCE.processOnTick();
    }
}
