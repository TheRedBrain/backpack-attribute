package com.github.theredbrain.backpackattribute.mixin.server.network;

import com.github.theredbrain.backpackattribute.entity.player.DuckPlayerMixin;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "restoreFrom", at = @At("TAIL"))
    public void backpackattribute$restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        ((DuckPlayerMixin) this).backpackattribute$setBackpackInventory(((DuckPlayerMixin) oldPlayer).backpackattribute$getBackpackInventory());
    }
}
