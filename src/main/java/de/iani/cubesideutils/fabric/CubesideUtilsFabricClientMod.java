package de.iani.cubesideutils.fabric;

import de.iani.cubesideutils.fabric.packets.RankDataChannelHandler;
import de.iani.cubesideutils.fabric.permission.PermissionHandler;
import de.iani.cubesideutils.fabric.scheduler.Helper;
import java.sql.SQLException;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubesideUtilsFabricClientMod implements ClientModInitializer {
    public static final String MODID = "cubesideutilsfabricclient";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

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
        ClientPlayConnectionEvents.DISCONNECT.register((_, _) -> PermissionHandler.setRank(null));
    }

    public void onClientStarting(Minecraft client) {
        Helper.initialize(client);
    }

    public void onClientTick(Minecraft client) {
        Helper.processOnTick(this);
    }
}
