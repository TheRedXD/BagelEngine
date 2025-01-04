package sh.thered.bagelengine.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static sh.thered.bagelengine.common.CommandUtils.makeCommand;
import static sh.thered.bagelengine.common.PermUtils.permCheck;
import static sh.thered.bagelengine.common.SSUtils.distributeBarrelSignalStrengthItemsPerStack;

public class SSCommand {
    public static void register() {
        makeCommand((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ss")
            .then(argument("strength", IntegerArgumentType.integer()).requires(source -> permCheck(source, "bagelengine.ss"))
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    int strength = IntegerArgumentType.getInteger(context, "strength");

                    if(strength < 0 || strength > 15) {
                        source.sendError(Text.literal("§cSignal strength must be between 0 and 15."));
                        return 0;
                    }

                    List<Integer> items = distributeBarrelSignalStrengthItemsPerStack(strength);

                    // Create the barrel item with correct name
                    net.minecraft.item.ItemStack barrel = new net.minecraft.item.ItemStack(net.minecraft.item.Items.BARREL);
                    barrel.set(DataComponentTypes.CUSTOM_NAME, Text.literal(""+strength));
                    List<ItemStack> stacks = new ArrayList<>();
                    for (int count : items) {
                        ItemStack item = new ItemStack(Items.REDSTONE, count);
                        stacks.add(item);
                    }
                    ContainerComponent data_comp = ContainerComponent.fromStacks(stacks);
                    barrel.set(DataComponentTypes.CONTAINER, data_comp);

                    String extraMsg = "";
                    if (source.isExecutedByPlayer()) {
                        ServerPlayerEntity player = source.getPlayer();
                        assert player != null;
                        if (player.getInventory().getEmptySlot() >= 0) {
                            player.getInventory().insertStack(barrel);
                        } else {
                            net.minecraft.entity.ItemEntity item = new net.minecraft.entity.ItemEntity(player.getWorld(), player.getX(), player.getY(), player.getZ(), barrel);
                            player.getWorld().spawnEntity(item);
                            extraMsg = " Your inventory was full, so the barrel was dropped.";
                        }
                    } else {
                        source.sendFeedback(() -> Text.literal("§6[BagelEngine] Whoops! Looks like you're not a player. This command is for players only!"), false);
                        return 0;
                    }

                    String onestring = "§6[BagelEngine] §eCreated barrel with signal strength " + strength + ".";

                    final String message = String.format("%s%s", onestring, extraMsg);
                    source.sendFeedback(() -> Text.literal(message), false);

                    return 1;
                })
        )));
    }
}
