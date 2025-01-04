package sh.thered.bagelengine.commands;

import com.mojang.brigadier.Command;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;

public class ShareCoordsCommand {
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("sharecoords")
            .executes(context -> {
                ServerCommandSource source = context.getSource();
                if(!source.isExecutedByPlayer()) {
                    source.sendError(Text.literal("§cThis command can only be used by players!"));
                    return 0;
                }

                ServerPlayerEntity player = source.getPlayer();
                assert player != null;
                int x = (int) player.getX();
                int y = (int) player.getY();
                int z = (int) player.getZ();
                String dimension = player.getWorld().getRegistryKey().getValue().toString();

                String message = String.format("§e%s §6wants to share their coordinates! Coords: §6X: §e%d §6§nY: §e§n%d§6 §6Z: §e%d §6Dimension: §e%s",
                        player.getName().getString(), x, y, z, dimension);

                source.getServer().getPlayerManager().broadcast(Text.literal(message), false);

                return Command.SINGLE_SUCCESS;
            })
        ));
    }
}
