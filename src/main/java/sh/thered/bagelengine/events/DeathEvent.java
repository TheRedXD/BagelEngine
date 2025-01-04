package sh.thered.bagelengine.events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import sh.thered.bagelengine.routines.DeathCoordsRoutine;

public class DeathEvent {
    public static boolean onDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        // Call necessary routines
        DeathCoordsRoutine.onDeath(entity, damageSource, damageAmount);
        return true;
    }
}
