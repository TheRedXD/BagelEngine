package sh.thered.bagelengine.routines;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

public class WelcomeRoutine {
    public static void onJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        String username = handler.player.getName().getString();
        String welcomeMessage = "§2========================\n§aWelcome to Bagel, §2" + username + "§a!\n" +
                "It is very much good kebab clone\n\n" +
                "This runs on fabric yes\n" +
                "I'm too lazy to come up with anything good to put here\n§2========================";
        handler.player.sendMessage(Text.literal(welcomeMessage).styled(style -> style.withClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, "https://sus.red/")
        )), false);
    }
}
