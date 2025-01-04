package sh.thered.bagelengine.commands;

public class CommandRegistrator {
    public static void register() {
        // Register aliases
        AliasRegistrator.register();

        // Register all commands
        SpawnCommand.register();
        ResetWorldCommand.register();
        ModsCommand.register();
        SSCommand.register();
        ShareCoordsCommand.register();
        NickCommand.register();
    }
}
