package dev.enderman.minecraft.mods.triangulator.utility;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static <K, V> @NotNull Map<V, K> inverMap(@NotNull Map<K, V> map) {
        HashMap<V, K> invertedMap = new HashMap<>();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            invertedMap.put(entry.getValue(), entry.getKey());
        }

        return invertedMap;
    }
}
