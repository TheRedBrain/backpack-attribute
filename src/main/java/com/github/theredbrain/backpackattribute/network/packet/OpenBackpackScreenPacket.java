package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.RegistryByteBuf;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.network.packet.CustomPayload;

public record OpenBackpackScreenPacket() implements FabricPacket/*CustomPayload*/ {

    // TODO 1.20.6
//    public static final CustomPayload.Id<OpenBackpackScreenPacket> PACKET_ID = new CustomPayload.Id<>(BackpackAttribute.identifier("open_backpack_screen"));
//    public static final PacketCodec<RegistryByteBuf, OpenBackpackScreenPacket> PACKET_CODEC = PacketCodec.of(OpenBackpackScreenPacket::write, OpenBackpackScreenPacket::new);
//
//    public OpenBackpackScreenPacket(RegistryByteBuf registryByteBuf) {
//        this();
//    }
//
//    public void write(RegistryByteBuf buf) {
//    }
//
//    @Override
//    public Id<? extends CustomPayload> getId() {
//        return PACKET_ID;
//    }

    public static final PacketType<OpenBackpackScreenPacket> TYPE = PacketType.create(
            BackpackAttribute.identifier("open_backpack_screen"),
            OpenBackpackScreenPacket::new
    );

    public OpenBackpackScreenPacket(PacketByteBuf buf) {
        this();
    }
    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
    @Override
    public void write(PacketByteBuf buf) {
    }
}
