package sh.thered.bagelengine.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;
import static sh.thered.bagelengine.common.PermUtils.permCheck;
import static sh.thered.bagelengine.common.PermUtils.permFail;

public class NickCommand {
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("nick")
            .executes(context -> {
                ServerCommandSource source = context.getSource();
                if(!permCheck(source, "bagelengine.nick")) return permFail(source);
                if(!source.isExecutedByPlayer()) {
                    source.sendError(Text.literal("§cThis command can only be used by players!"));
                    return 0;
                }

                String username = source.getName();

                // Execute the styledchat command to clear nickname
                source.getServer().getCommandManager().executeWithPrefix(
                    source.getServer().getCommandSource(),
                    "styledchat clear " + username + " display_name"
                );

                source.sendFeedback(() -> Text.literal("§6[BagelEngine] §eNickname cleared"), false);
                return Command.SINGLE_SUCCESS;
            })
            .then(argument("nickname", StringArgumentType.greedyString())
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    if(!permCheck(source, "bagelengine.nick")) return permFail(source);
                    if(!source.isExecutedByPlayer()) {
                        source.sendError(Text.literal("§cThis command can only be used by players!"));
                        return 0;
                    }

                    String nickname = StringArgumentType.getString(context, "nickname");
                    String username = source.getName();

                    // Execute the styledchat command
                    source.getServer().getCommandManager().executeWithPrefix(
                        source.getServer().getCommandSource(),
                        "styledchat set " + username + " display_name " + nickname
                    );

                    source.sendFeedback(() -> Text.literal("§6[BagelEngine] §eNickname set to: " + nickname), false);
                    return Command.SINGLE_SUCCESS;
                }))
        ));
    }
}
