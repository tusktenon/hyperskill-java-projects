package collections;

import java.util.List;

public final class ImmutableCollection<E> {

    private final List<E> contents;

    // No public constructors
    @SafeVarargs
    private ImmutableCollection(E... data) {
        // The unmodifiable lists created by `List.of` disallow  null elements.
        // Attempts to create them with null elements result in `NullPointerException`:
        // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html#unmodifiable
        contents = List.of(data);
    }

    // Only required for Hyperskill tests
    public static <E> ImmutableCollection<E> of() {
        return new ImmutableCollection<>();
    }

    @SafeVarargs
    public static <E> ImmutableCollection<E> of(E... data) {
        return new ImmutableCollection<>(data);
    }

    public boolean contains(E element) {
        return contents.contains(element);
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public int size() {
        return contents.size();
    }
}
