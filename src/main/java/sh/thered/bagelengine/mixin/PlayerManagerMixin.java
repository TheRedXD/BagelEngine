package sh.thered.bagelengine.mixin;

import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.thered.bagelengine.patches.ResetWorldPatch;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(method = "saveAllPlayerData", at = @At("HEAD"), cancellable = true)
    public void saveAllPlayerData(CallbackInfo ci) {
        ResetWorldPatch.saveAllPlayerData(ci);
    }
}
