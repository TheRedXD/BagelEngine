package sh.thered.bagelengine.patches;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.thered.bagelengine.BagelEngine;

/// Patches the server trying to save itself while resetting the world.
public class ResetWorldPatch {
    /// Triggers on: `PlayerManager#saveAllPlayerData`
    public static void saveAllPlayerData(CallbackInfo ci) {
        if (BagelEngine.resettingWorld()) {
            System.out.println("BagelEngine - saveAllPlayerData is being cancelled");
            ci.cancel();
        }
    }
    /// Triggers on: `MinecraftServer#save`
    public static void save(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (BagelEngine.resettingWorld()) {
            System.out.println("BagelEngine - save is being cancelled");
            cir.setReturnValue(true);
        }
    }
    /// Triggers on: `MinecraftServer#saveAll`
    public static void saveAll(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if (BagelEngine.resettingWorld()) {
            System.out.println("BagelEngine - saveAll is being cancelled");
            cir.setReturnValue(true);
        }
    }
}
