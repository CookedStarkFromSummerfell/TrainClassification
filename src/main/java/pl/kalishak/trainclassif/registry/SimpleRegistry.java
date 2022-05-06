package pl.kalishak.trainclassif.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class SimpleRegistry<T extends RegistryEntry<T>> {

    protected final Map<Integer, RegistryEntry<T>> ENTRIES;

    public SimpleRegistry() {
        ENTRIES = Maps.newLinkedHashMap();
    }
}
