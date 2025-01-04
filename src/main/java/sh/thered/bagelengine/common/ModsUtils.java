package sh.thered.bagelengine.common;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.HashMap;

public class ModsUtils {
    static HashMap<String, String> compressions = new HashMap<>();
    public static boolean compressionsRequired(String modId) {
        if (FabricLoader.getInstance().getModContainer(modId).isPresent()) {
            ModContainer container = FabricLoader.getInstance().getModContainer(modId).get();
            if (container.getContainingMod().isPresent()) {
                if (container.getContainingMod().get().getContainingMod().isPresent()) {
                    compressions.put(modId, container.getContainingMod().get().getContainingMod().get().getMetadata().getName());
                } else {
                    compressions.put(modId, container.getContainingMod().get().getMetadata().getName());
                }
                return true;
            }
        }
        return false;
    }
    public static String compression(String modId) {
        if (compressions.containsKey(modId)) {
            return compressions.get(modId);
        }
        return modId;
    }
}
