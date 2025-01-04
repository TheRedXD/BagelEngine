package sh.thered.bagelengine.routines;

import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LagFixRoutine {
    static boolean waitingForLagClearTime = false;
    static int lagClearTimeTicks = 0;
    public static void onTick(MinecraftServer server) {
        if (server.getTicks() % 20 == 0) {
            var sparkProvider = SparkProvider.get();
            var tps = sparkProvider.tps();
            if (tps != null) {
                if (waitingForLagClearTime) {
                    if (lagClearTimeTicks >= 20*10) {
                        lagClearTimeTicks = 0;
                        waitingForLagClearTime = false;
                    } else {
                        lagClearTimeTicks++;
                    }
                }
                if (tps.poll(StatisticWindow.TicksPerSecond.SECONDS_10) < 10d && !waitingForLagClearTime) {
                    server.getPlayerManager().broadcast(Text.literal("§6[BagelEngine] §eServer lag detected. Attempting to fix..."), false);
                    waitingForLagClearTime = true;
                    var entities = new HashMap<String, Integer>();
                    for (var world : server.getWorlds()) {
                        for (var entity : world.iterateEntities()) {
                            // get class name up until @ symbol only
                            String name = entity.getClass().getName().split("@")[0];
                            // only want last part
                            name = name.split("\\.")[name.split("\\.").length - 1];
                            entities.put(name, entities.getOrDefault(name, 0) + 1);
                        }
                    }
                    // get top 5 entities
                    List<Map.Entry<String, Integer>> sortedEntities = new ArrayList<>(entities.entrySet());
                    sortedEntities.sort((a, b) -> b.getValue().compareTo(a.getValue()));
                    boolean shouldKill = false;
                    for (int i = 0; i < Math.min(5, sortedEntities.size()); i++) {
                        if (sortedEntities.get(i).getValue() >= 80) {
                            shouldKill = true;
                            break;
                        }
                    }
                    int totalEntities = sortedEntities.stream().mapToInt(Map.Entry::getValue).sum();
                    if (totalEntities >= 800) {
                        shouldKill = true;
                    }
                    if (shouldKill) {
                        for (var world : server.getWorlds()) {
                            for (var entity : world.iterateEntities()) {
                                if (!entity.isPlayer()) {
                                    entity.discard();
                                }
                            }
                        }
                        server.getPlayerManager().broadcast(Text.literal("§6[BagelEngine] §eEntities cleared to reduce lag."), false);
                    }
                }
            }
        }
    }
}
