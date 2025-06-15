package collections;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SizeLimitedQueue<E> {

    final E[] data;
    final int capacity;
    int head = 0;
    int size = 0;

    @SuppressWarnings("unchecked")
    public SizeLimitedQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Size limit must be positive");
        this.capacity = capacity;
        data = (E[]) new Object[capacity];
    }

    public void add(E element) {
        if (element == null) throw new NullPointerException("Attempt to add null");
        data[(head + size) % capacity] = element;
        if (size == capacity) head = (head + 1) % capacity;
        else size++;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        head = 0;
        size = 0;
    }

    public boolean isAtFullCapacity() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int maxSize() {
        return capacity;
    }

    public E peek() {
        return isEmpty() ? null : data[head];
    }

    public E remove() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        E headElement = data[head];
        data[head] = null;
        head = (head + 1) % capacity;
        size--;
        return headElement;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray(E[] e) {
        if (e.length < size) {
            e = (E[]) Array.newInstance(e.getClass().getComponentType(), size);
        }
        for (int i = 0; i < size; i++) {
            e[i] = data[(head + i) % capacity];
        }
        for (int i = size; i < e.length; i++) {
            e[i] = null;
        }
        return e;
    }

    public Object[] toArray() {
        Object[] elements = new Object[size];
        for (int i = 0; i < size; i++) {
            elements[i] = data[(head + i) % capacity];
        }
        return elements;
    }

    @Override
    public String toString() {
        return IntStream.range(0, size)
                .mapToObj(i -> data[(head + i) % capacity].toString())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
