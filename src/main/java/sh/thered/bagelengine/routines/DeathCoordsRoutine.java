package sh.thered.bagelengine.routines;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class DeathCoordsRoutine {
    public static void sendPlayerDeathCoordinates(PlayerEntity entity) {
        int x = (int) entity.getX();
        int y = (int) entity.getY();
        int z = (int) entity.getZ();
        String dimension = entity.getWorld().getRegistryKey().getValue().toString();
        String deathMessage = String.format("§cYou died at §6X: %d, Y: %d, Z: %d §cin §6%s", x, y, z, dimension);
        entity.sendMessage(Text.literal(deathMessage), false);
    }
    public static void onDeath(LivingEntity entity, DamageSource damageSource, float damageAmount) {
        if(entity.isPlayer()) {
            PlayerEntity player = (PlayerEntity) entity;
            sendPlayerDeathCoordinates(player);
        }
    }
}
