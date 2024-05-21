package de.iani.cubesideutils.fabric.packets;

import de.iani.cubesideutils.fabric.permission.PermissionHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class RankDataChannelHandler implements ClientPlayNetworking.PlayPayloadHandler<RankInfoS2C>, ClientConfigurationNetworking.ConfigurationPayloadHandler<RankInfoS2C>  {

    public RankDataChannelHandler() {
        PayloadTypeRegistry.playS2C().register(RankInfoS2C.PACKET_ID, RankInfoS2C.PACKET_CODEC);
        PayloadTypeRegistry.configurationS2C().register(RankInfoS2C.PACKET_ID, RankInfoS2C.PACKET_CODEC);

        ClientPlayNetworking.registerGlobalReceiver(RankInfoS2C.PACKET_ID, this);
        ClientConfigurationNetworking.registerGlobalReceiver(RankInfoS2C.PACKET_ID, this);
    }

    @Override
    public void receive(RankInfoS2C payload, ClientConfigurationNetworking.Context context) {
        PermissionHandler.setRank(payload.rank());
    }

    @Override
    public void receive(RankInfoS2C payload, ClientPlayNetworking.Context context) {
        PermissionHandler.setRank(payload.rank());
    }
}
