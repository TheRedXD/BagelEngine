package sh.thered.bagelengine.commands;

import com.mojang.brigadier.Command;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.BagelEngine.*;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;

public class SpawnCommand {
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("spawn")
            .executes(context -> {
                ServerCommandSource source = context.getSource();
                if(!source.isExecutedByPlayer()) {
                    source.sendError(Text.literal("§cThis command can only be used by players!"));
                    return 0;
                }

                MinecraftServer server = getServer();

                ServerPlayerEntity player = source.getPlayer();
                ServerWorld world = server.getOverworld();
                assert player != null;
                player.teleport(world, (double) world.getSpawnPos().getX() + 0.5d, (double) world.getSpawnPos().getY(), (double) world.getSpawnPos().getZ() + 0.5d, PositionFlag.getFlags(0), 0.0f, 0.0f, false);

                source.sendFeedback(() -> Text.literal("§6[BagelEngine] §eTeleported to spawn"), false);
                return Command.SINGLE_SUCCESS;
            })
        ));
    }
}
