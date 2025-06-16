package collections;

import java.util.ArrayDeque;
import java.util.Queue;

public class SizeLimitedQueueArrayDeque<E> {

    final Queue<E> data;
    final int capacity;

    public SizeLimitedQueueArrayDeque(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Size limit must be positive");
        this.capacity = capacity;
        data = new ArrayDeque<>(capacity);
    }

    public void add(E element) {
        // ArrayDeque prohibits null elements
        if (isAtFullCapacity()) data.remove();
        data.add(element);
    }

    public void clear() {
        data.clear();
    }

    public boolean isAtFullCapacity() {
        return data.size() == capacity;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int maxSize() {
        return capacity;
    }

    public E peek() {
        return data.peek();
    }

    public E remove() {
        return data.remove();
    }

    public int size() {
        return data.size();
    }

    public E[] toArray(E[] e) {
        return data.toArray(e);
    }

    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public String toString() {
        return data.toString();
    }
}

