package sh.thered.bagelengine.common;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class CommandUtils {
    public static void makeCommand(CommandRegistrationCallback listener) {
        CommandRegistrationCallback.EVENT.register(listener);
    }
    public static void makeAlias(String a, String b) {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal(a)
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    source.getServer().getCommandManager().executeWithPrefix(source, b);
                    return Command.SINGLE_SUCCESS;
                })
        ));
    }
}
