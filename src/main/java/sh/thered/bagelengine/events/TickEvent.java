package sh.thered.bagelengine.events;

import net.minecraft.server.MinecraftServer;
import sh.thered.bagelengine.routines.LagFixRoutine;

public class TickEvent {
    public static void onTick(MinecraftServer server) {
        // Call necessary routines
        LagFixRoutine.onTick(server);
    }
}
