package collections;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Multiset<E> {

    private final Map<E, Integer> counts = new LinkedHashMap<>();

    public void add(E element) {
        add(element, 1);
    }

    public void add(E element, int occurrences) {
        if (occurrences > 0) {
            counts.merge(element, occurrences, Integer::sum);
        }
    }

    public boolean contains(E element) {
        return counts.containsKey(element);
    }

    public int count(E element) {
        return counts.getOrDefault(element, 0);
    }

    public Set<E> elementSet() {
        return counts.keySet();
    }

    public void remove(E element) {
        remove(element, 1);
    }

    public void remove(E element, int occurrences) {
        if (occurrences > 0) {
            counts.computeIfPresent(
                    element, (e, current) -> current > occurrences ? current - occurrences : null);
        }
    }

    public void setCount(E element, int count) {
        if (count == 0) {
            counts.remove(element);
        } else if (count > 0) {
            counts.replace(element, count);
        }
    }

    public void setCount(E element, int oldCount, int newCount) {
        if (newCount == 0) {
            counts.remove(element, oldCount);
        } else if (newCount > 0) {
            counts.replace(element, oldCount, newCount);
        }
    }

    @Override
    public String toString() {
        return counts.keySet().stream()
                .flatMap(key -> Stream.generate(key::toString).limit(counts.get(key)))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
