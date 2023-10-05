package de.iani.cubesideutils.fabric;

import de.iani.cubesideutils.fabric.scheduler.Helper;
import java.sql.SQLException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubesideUtilsFabricMod implements ModInitializer {
    public static final String MODID = "cubesideutilsfabric";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public CubesideUtilsFabricMod() {
        LOGGER.info("Registering MySQL driver");
        try {
            new com.mysql.cj.jdbc.Driver();
        } catch (SQLException e) {
            LOGGER.warn("Could not register MySql driver", e);
            e.printStackTrace();
        }
        // ModLoadingContext.get().registerConfig(Type.SERVER, CubesideUtilsForgeConfig.GENERAL_SPEC);
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);
    }

    public void onServerStarting(MinecraftServer server) {
        Helper.initialize(this, server);
    }

    public void onServerTick(MinecraftServer server) {
        Helper.processOnTick(this);
    }
}
