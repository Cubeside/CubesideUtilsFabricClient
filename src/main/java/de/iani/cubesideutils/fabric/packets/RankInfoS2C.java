package de.iani.cubesideutils.fabric.packets;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

public record RankInfoS2C(String rank) implements CustomPayload {
    public static final Id<RankInfoS2C> PACKET_ID = new Id<>(new Identifier("cubesideutilsfabricclient", "rank"));
    public static final PacketCodec<PacketByteBuf, RankInfoS2C> PACKET_CODEC = PacketCodec.of(RankInfoS2C::write, RankInfoS2C::new);

    public RankInfoS2C(PacketByteBuf buf) {
        this(parse(buf));
    }

    private static String parse(PacketByteBuf buf) {
        int size = buf.readableBytes();
        byte[] data = new byte[size];
        buf.readBytes(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public void write(PacketByteBuf buf) {
        byte[] bytes = rank.getBytes(StandardCharsets.UTF_8);
        // buf.writeByte(bytes.length);
        buf.writeBytes(bytes);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
