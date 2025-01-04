package sh.thered.bagelengine.common;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PermUtils {
    public static boolean permCheck(ServerCommandSource source, String name) {
        return !source.isExecutedByPlayer() || Permissions.check(source, name) || source.hasPermissionLevel(4);
    }
    public static int permFail(ServerCommandSource source) {
        source.sendError(Text.literal("§cYou don't have permission to use this command."));
        return 0;
    }
}
