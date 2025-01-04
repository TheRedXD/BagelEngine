package sh.thered.bagelengine.commands;

import static sh.thered.bagelengine.common.CommandUtils.makeAlias;

public class AliasRegistrator {
    public static void register() {
        makeAlias("tps", "spark tps");
        makeAlias("lag", "spark tps");
        makeAlias("gc", "spark tps");
        makeAlias("pl", "mods");
        makeAlias("plugins", "mods");
        makeAlias("sc", "sharecoords");
    }
}
