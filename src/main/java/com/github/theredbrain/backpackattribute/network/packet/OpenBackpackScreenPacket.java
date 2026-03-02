package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record OpenBackpackScreenPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenBackpackScreenPacket> PACKET_ID = new CustomPacketPayload.Type<>(BackpackAttribute.identifier("open_backpack_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenBackpackScreenPacket> PACKET_CODEC = StreamCodec.ofMember(OpenBackpackScreenPacket::write, OpenBackpackScreenPacket::new);

    public OpenBackpackScreenPacket(FriendlyByteBuf buf) {
        this();
    }
    private void write(RegistryFriendlyByteBuf registryByteBuf) {}

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
