package sh.thered.bagelengine.common;

import java.util.ArrayList;
import java.util.List;

public class SSUtils {
    public static int calculateBarrelSignalStrengthItemCount(int ss) {
        float maxBarrelItemCount = 64f*9f*3f;
        float itemCountPerSS = maxBarrelItemCount / 15f; // 16 states - 1 (because first state means no items)
        float itemCount = itemCountPerSS * (float)ss;
        return (int)itemCount;
    }
    public static List<Integer> distributeBarrelSignalStrengthItemsPerStack(int ss) {
        int currentItemCount = calculateBarrelSignalStrengthItemCount(ss);
        // now we need to distribute this per stack
        List<Integer> items = new ArrayList<>();
        while(true) {
            if (currentItemCount >= 64) {
                currentItemCount -= 64;
                items.add(64);
            } else {
                if (currentItemCount != 0) {
                    items.add(currentItemCount);
                }
                break;
            }
        }
        return items;
    }
}
