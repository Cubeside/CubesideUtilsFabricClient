package de.iani.cubesideutils.fabric;

import de.iani.cubesideutils.fabric.packets.RankDataChannelHandler;
import de.iani.cubesideutils.fabric.permission.PermissionHandler;
import de.iani.cubesideutils.fabric.scheduler.Helper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class CubesideUtilsFabricClientMod implements ClientModInitializer {
    public static final String MODID = "cubesideutilsfabricclient";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static String rank;

    public CubesideUtilsFabricClientMod() {
        LOGGER.info("Registering MySQL driver");
        try {
            new com.mysql.cj.jdbc.Driver();
        } catch (SQLException e) {
            LOGGER.warn("Could not register MySql driver", e);
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializeClient() {
        new RankDataChannelHandler();
        new PermissionHandler();

        ClientLifecycleEvents.CLIENT_STARTED.register(this::onClientStarting);
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> PermissionHandler.setRank(null));
    }
    public void onClientStarting(MinecraftClient client) {
        Helper.initialize(this, client);
    }

    public void onClientTick(MinecraftClient client) {
        Helper.processOnTick(this);
    }
}
