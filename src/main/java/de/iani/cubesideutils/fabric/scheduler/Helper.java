package de.iani.cubesideutils.fabric.scheduler;

import de.iani.cubesideutils.fabric.CubesideUtilsFabricClientMod;
import net.minecraft.client.Minecraft;

public class Helper {
    public static void initialize(Minecraft client) {
        Scheduler.INSTANCE.initialize(client.getRunningThread());
    }

    public static void processOnTick(CubesideUtilsFabricClientMod mod) {
        Scheduler.INSTANCE.processOnTick();
    }
}
