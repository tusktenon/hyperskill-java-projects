package collections;

import java.util.*;

public class BiMap<K, V> {

    private final Map<K, V> forward;
    private final Map<V, K> reverse;

    public BiMap() {
        forward = new HashMap<>();
        reverse = new HashMap<>();
    }

    private BiMap(Map<K, V> forward, Map<V, K> reverse) {
        this.forward = forward;
        this.reverse = reverse;
    }

    public V put(K key, V value) {
        throwIfAlreadyPresent(key, value);
        forward.put(key, value);
        reverse.put(value, key);
        return value;
    }

    public void putAll(Map<K, V> map) {
        map.forEach(this::throwIfAlreadyPresent);
        map.forEach((key, value) -> {
            forward.put(key, value);
            reverse.put(value, key);
        });
    }

    public Set<V> values() {
        return reverse.keySet();
    }

    public V forcePut(K key, V value) {
        V oldValue = forward.put(key, value);
        K oldKey = reverse.put(value, key);
        if (!key.equals(oldKey)) forward.remove(oldKey);
        if (!value.equals(oldValue)) reverse.remove(oldValue);
        return oldValue;
    }

    public BiMap<V, K> inverse() {
        return new BiMap<>(reverse, forward);
    }

    @Override
    public String toString() {
        return forward.toString();
    }

    private void throwIfAlreadyPresent(K key, V value) {
        if (forward.containsKey(key)) {
            throw new IllegalArgumentException("BiMap already contains a mapping for key " + key);
        }
        if (reverse.containsKey(value)) {
            throw new IllegalArgumentException("BiMap already contains a mapping for value " + value);
        }
    }
}
