package sh.thered.bagelengine.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import sh.thered.bagelengine.routines.WelcomeRoutine;

public class JoinEvent {
    public static void onJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        // Call necessary routines
        WelcomeRoutine.onJoin(handler, sender, server);
    }
}
