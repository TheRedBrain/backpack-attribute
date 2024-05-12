package com.github.theredbrain.backpackattribute.network.packet;

import com.github.theredbrain.backpackattribute.BackpackAttribute;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;

public record OpenBackpackScreenPacket() implements FabricPacket {

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
