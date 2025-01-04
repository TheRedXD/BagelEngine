package sh.thered.bagelengine.patches;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/// Patches players getting killed using /kill
public class PlayerKillPatch {
    /// Triggers on: `PlayerEntity#damage`
    public static void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (amount == Float.MAX_VALUE) {
            cir.setReturnValue(false);
        }
    }
}
