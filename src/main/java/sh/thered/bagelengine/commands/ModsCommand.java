package sh.thered.bagelengine.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;
import static sh.thered.bagelengine.common.ModsUtils.compression;
import static sh.thered.bagelengine.common.ModsUtils.compressionsRequired;
import static sh.thered.bagelengine.common.PermUtils.permCheck;
import static sh.thered.bagelengine.common.PermUtils.permFail;

public class ModsCommand {
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("mods")
            .executes(context -> {
                ServerCommandSource source = context.getSource();
                if(!permCheck(source, "bagelengine.mods")) return permFail(source);
                int count = 0;
                StringBuilder modList = new StringBuilder();
                List<String> compressionsAlreadyPresent = new java.util.ArrayList<>(List.of());
                List<String> modNamesAlreadyPresent = new java.util.ArrayList<>(List.of());
                for (String modId : FabricLoader.getInstance().getAllMods().stream()
                        .map(mod -> mod.getMetadata().getId())
                        .sorted().toList()) {
                    if (!(modId.equals("minecraft") || modId.equals("java") || (modId.startsWith("fabric-") && !modId.equals("fabric-api")) || modId.equals("fabricloader") || modId.equals("mixinextras"))) {
                        if (compressionsRequired(modId)) {
                            String modName = compression(modId);
                            if(!compressionsAlreadyPresent.contains(modName)) {
                                if (!modNamesAlreadyPresent.contains(modName)) {
                                    compressionsAlreadyPresent.add(modName);
                                    modList.append("§a").append(modName).append("§6").append(", ");
                                    count++;
                                    modNamesAlreadyPresent.add(modName);
                                }
                            }
                        } else {
                            String modName;
                            if(FabricLoader.getInstance().getModContainer(modId).isPresent()) {
                                modName = FabricLoader.getInstance().getModContainer(modId).get().getMetadata().getName();
                            } else {
                                modName = modId;
                            }
                            if (!modNamesAlreadyPresent.contains(modName)) {
                                modList.append("§a").append(modName).append("§6").append(", ");
                                count++;
                                modNamesAlreadyPresent.add(modName);
                            }
                        }
                    }
                }
                if (modList.charAt(modList.length() - 1) == ' ') {
                    modList.setLength(modList.length() - 2);
                    modList.append(".");
                }
                String newModList = "§e§lInstalled mods ("+count+"):§6\n"+ modList;
                source.sendFeedback(() -> Text.literal(newModList), false);
                return 1;
            })
            .then(literal("all")
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    if(!permCheck(source, "bagelengine.mods")) return permFail(source);
                    int count = 0;
                    StringBuilder modList = new StringBuilder();
                    for (String modId : FabricLoader.getInstance().getAllMods().stream()
                            .map(mod -> mod.getMetadata().getId())
                            .sorted().toList()) {
                        String statusColor;
                        if (modId.equals("minecraft") || modId.equals("java") || (modId.startsWith("fabric-") && !modId.equals("fabric-api")) || modId.equals("fabricloader") || modId.equals("mixinextras") || FabricLoader.getInstance().getModContainer(modId).get().getContainingMod().isPresent()) {
                            statusColor = "§7"; // Gray color
                        } else {
                            statusColor = "§a"; // Lime green color
                        }
                        String modName;
                        if(FabricLoader.getInstance().getModContainer(modId).isPresent()) {
                            modName = FabricLoader.getInstance().getModContainer(modId).get().getMetadata().getName();
                        } else {
                            modName = modId;
                        }
                        modList.append(statusColor).append(modName).append("§6").append(", ");
                        count++;
                    }
                    if (modList.charAt(modList.length() - 1) == ' ') {
                        modList.setLength(modList.length() - 2);
                        modList.append(".");
                    }
                    String newModList = "§e§lInstalled mods (all, "+count+"):§6\n"+ modList;
                    source.sendFeedback(() -> Text.literal(newModList), false);
                    return 1;
                }))
            .then(literal("trace")
                .then(argument("modId", StringArgumentType.greedyString())
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        if(!permCheck(source, "bagelengine.mods")) return permFail(source);
                        String modId = StringArgumentType.getString(context, "modId");

                        var modContainer = FabricLoader.getInstance().getModContainer(modId);
                        if (modContainer.isEmpty()) {
                            source.sendError(Text.literal("§cMod ID '" + modId + "' does not exist."));
                            return 0;
                        }

                        if (modContainer.get().getContainingMod().isPresent()) {
                            String containerModId = modContainer.get().getContainingMod().get().getMetadata().getId();
                            source.sendFeedback(() -> Text.literal("§eMod '" + modId + "' is in a container. Mod ID of container is §6" + containerModId), false);
                            return 1;
                        }
                        var modLocation = modContainer.get().getOrigin().getPaths().getFirst();
                        source.sendFeedback(() -> Text.literal("§eMod '" + modId + "' is located at: §6" + modLocation), false);
                        return 1;
                    }))
            )
            .then(literal("ids")
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    if(!permCheck(source, "bagelengine.mods")) return permFail(source);
                    int count = 0;
                    StringBuilder modList = new StringBuilder();
                    for (String modId : FabricLoader.getInstance().getAllMods().stream()
                            .map(mod -> mod.getMetadata().getId())
                            .sorted().toList()) {
                        modList.append("§a").append(modId).append("§6").append(", ");
                        count++;
                    }
                    if (modList.charAt(modList.length() - 1) == ' ') {
                        modList.setLength(modList.length() - 2);
                        modList.append(".");
                    }
                    String newModList = "§e§lInstalled mods (Mod IDs, "+count+"):§6\n"+ modList;
                    source.sendFeedback(() -> Text.literal(newModList), false);
                    return 1;
                }))
        ));
    }
}
