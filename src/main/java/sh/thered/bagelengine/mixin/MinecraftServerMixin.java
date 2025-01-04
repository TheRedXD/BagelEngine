package sh.thered.bagelengine.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.thered.bagelengine.patches.ResetWorldPatch;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "save", at = @At("HEAD"), cancellable = true)
    public void save(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        ResetWorldPatch.save(suppressLogs, flush, force, cir);
    }
    @Inject(method = "saveAll", at = @At("HEAD"), cancellable = true)
    public void saveAll(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        ResetWorldPatch.saveAll(suppressLogs, flush, force, cir);
    }
}
