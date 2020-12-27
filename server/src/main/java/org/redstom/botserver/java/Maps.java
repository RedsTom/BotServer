package org.redstom.botserver.java;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class Maps {

    public static <T, V> void getIfPresent(Map<T, V> map, T key, Consumer<V> consumer) {
        Optional.ofNullable(map.get(key)).ifPresent(consumer::accept);
    }

}