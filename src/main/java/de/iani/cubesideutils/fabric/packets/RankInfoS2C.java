package de.iani.cubesideutils.fabric.packets;

import java.nio.charset.StandardCharsets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RankInfoS2C(String rank) implements CustomPacketPayload {
    public static final Type<RankInfoS2C> PACKET_ID = new Type<>(Identifier.fromNamespaceAndPath("cubesideutilsfabricclient", "rank"));
    public static final StreamCodec<FriendlyByteBuf, RankInfoS2C> PACKET_CODEC = StreamCodec.ofMember(RankInfoS2C::write, RankInfoS2C::new);

    public RankInfoS2C(FriendlyByteBuf buf) {
        this(parse(buf));
    }

    private static String parse(FriendlyByteBuf buf) {
        int size = buf.readableBytes();
        byte[] data = new byte[size];
        buf.readBytes(data);
        return new String(data, StandardCharsets.UTF_8);
    }

    public void write(FriendlyByteBuf buf) {
        byte[] bytes = rank.getBytes(StandardCharsets.UTF_8);
        // buf.writeByte(bytes.length);
        buf.writeBytes(bytes);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
