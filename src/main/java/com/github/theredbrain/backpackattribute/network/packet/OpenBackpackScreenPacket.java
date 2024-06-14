package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record OpenBackpackScreenPacket() implements CustomPayload {
    public static final CustomPayload.Id<OpenBackpackScreenPacket> PACKET_ID = new CustomPayload.Id<>(BackpackAttribute.identifier("open_backpack_screen"));
    public static final PacketCodec<RegistryByteBuf, OpenBackpackScreenPacket> PACKET_CODEC = PacketCodec.of(OpenBackpackScreenPacket::write, OpenBackpackScreenPacket::new);

    public OpenBackpackScreenPacket(PacketByteBuf buf) {
        this();
    }
    private void write(RegistryByteBuf registryByteBuf) {}

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
