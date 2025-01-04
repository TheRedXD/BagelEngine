package sh.thered.bagelengine.commands;

import com.mojang.brigadier.Command;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.WorldSavePath;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.BagelEngine.*;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;

public class ResetWorldCommand {
    public static void resetWorld(net.minecraft.server.MinecraftServer server) {
        shouldNotSave = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (var world : server.getWorlds()) {
            world.savingDisabled = true;
        }
        net.minecraft.util.WorldSavePath[] paths = {
                WorldSavePath.ROOT
        };
        for (var path : paths) {
            Path folderPath = server.getSavePath(path);
            try {
                System.out.println("BagelEngine - Walking path "+folderPath.toString());
                Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public @NotNull FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public @NotNull FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.err.println("BagelEngine - Error deleting folder: " + e.getMessage());
            }
        }
    }
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("resetworld")
            .executes(context -> {
                ServerCommandSource source = context.getSource();
                if(!source.equals(source.getServer().getCommandSource())) {
                    source.sendError(Text.literal("§cThis command can only be used by the console!"));
                    return 0;
                }

                source.getServer().getPlayerManager().broadcast(Text.literal("§6[BagelEngine] §eServer world reset requested!"), false);
                resetWorld(source.getServer());
                source.getServer().waitForTasks();
                source.getServer().stop(false);
                return Command.SINGLE_SUCCESS;
            })
        ));
    }
}
