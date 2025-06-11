package collections;

import java.util.Set;

public final class ImmutableCollection<E> {

    private final Set<E> contents;

    // No public constructors
    @SafeVarargs
    private ImmutableCollection(E... data) {
        // The unmodifiable sets created by `Set.of` disallow  null elements.
        // Attempts to create them with null elements result in `NullPointerException`:
        // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Set.html#unmodifiable
        contents = Set.of(data);
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
