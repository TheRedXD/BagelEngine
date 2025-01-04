package sh.thered.bagelengine.mixin;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(CommandManager.class)
public abstract class CommandManagerMixin {

    @Shadow @Final private CommandDispatcher<ServerCommandSource> dispatcher;

    // Commands to disable
    @Unique
    private static final Set<String> DISABLED_COMMANDS = Set.of(
            "styledchat",
            "setidletimeout",
            "return",
            "transfer",
            "perf",
            "jfr",
            "defaultgamemode",
            "datapack",
            "reload",
            "save-all",
            "save-on",
            "save-off",
            "tick",
            "spark",
            "whitelist",
            "stop",
            "kick",
            "ban",
            "ban-ip",
            "pardon",
            "pardon-ip",
            "async",
            "servercore",
            "c2me",
            "viaversion",
            "viaver",
            "forceload"
    );

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void onExecute(ParseResults<ServerCommandSource> parseResults, String command, CallbackInfo ci) {
        String commandName = command.split(" ")[0]; // Get the base command name

        if (DISABLED_COMMANDS.contains(commandName) && (parseResults.getContext().getSource().isExecutedByPlayer() || !parseResults.getContext().getSource().hasPermissionLevel(4))) { // Block players and command blocks
            if (commandName.equals("spark") && command.equals("spark tps")) {
                return;
            }
            parseResults.getContext().getSource().sendFeedback(() -> Text.literal("§cThe command " + commandName + " is disabled on this server."), false);
            ci.cancel();
        }
    }
}