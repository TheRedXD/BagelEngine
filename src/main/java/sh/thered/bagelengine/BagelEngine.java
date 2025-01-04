package sh.thered.bagelengine;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import sh.thered.bagelengine.commands.CommandRegistrator;
import sh.thered.bagelengine.events.DeathEvent;
import sh.thered.bagelengine.events.JoinEvent;
import sh.thered.bagelengine.events.TickEvent;

// TODO for the entire project:
// - Switch to using logger instead of System.out.println
// - Implement more QOL-related commands, like /home, /warp etc.
// - Add a proper config
// - Make welcome messages configurable
// - Make the lag fixer smarter and be able to handle more cases of lag
//   - In terms of lag fixing, make it automatically detect high lag areas (maybe caused by redstone?) and
//     stop redstone activity there. This should automatically stop lag machines, but keep most other redstone running.

public class BagelEngine implements ModInitializer {
    public static boolean shouldNotSave = false;
    private static net.minecraft.server.MinecraftServer server;

    public static boolean resettingWorld() {
        return shouldNotSave;
    }

    public static net.minecraft.server.MinecraftServer getServer() {
        return server;
    }

    @Override
    public void onInitialize() {
        // Setup server
        ServerLifecycleEvents.SERVER_STARTED.register(server -> BagelEngine.server = server);

        // Register events
        ServerPlayConnectionEvents.JOIN.register(JoinEvent::onJoin);
        ServerLivingEntityEvents.ALLOW_DEATH.register(DeathEvent::onDeath);
        ServerTickEvents.END_SERVER_TICK.register(TickEvent::onTick);

        // Register commands and aliases
        CommandRegistrator.register();

        System.out.println("BagelEngine - Initialized!");
    }
}
