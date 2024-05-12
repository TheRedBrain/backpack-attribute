package com.github.theredbrain.backpackattribute.mixin.server.network;

import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerEntityMixin;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    public void backpackattribute$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((DuckPlayerEntityMixin) this).backpackattribute$setBackpackInventory(((DuckPlayerEntityMixin) oldPlayer).backpackattribute$getBackpackInventory());
    }
}
