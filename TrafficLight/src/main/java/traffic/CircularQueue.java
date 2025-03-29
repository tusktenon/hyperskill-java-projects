package traffic;

import java.util.Optional;

class CircularQueue {

    private final String[] elements;
    private int front = 0;
    private int rear = 0;
    private int length = 0;

    CircularQueue(int capacity) {
        elements = new String[capacity];
    }

    boolean isEmpty() {
        return length == 0;
    }

    boolean isFull() {
        return length == elements.length;
    }

    boolean enqueue(String e) {
        if (isFull()) return false;
        elements[rear] = e;
        rear = (rear + 1) % elements.length;
        length++;
        return true;
    }

    Optional<String> dequeue() {
        if (isEmpty()) return Optional.empty();
        String dequeued = elements[front];
        elements[front] = null; // prevent loitering
        front = (front + 1) % elements.length;
        length--;
        return Optional.of(dequeued);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(elements[(front + i) % elements.length]).append('\n');
        return sb.toString();
    }
}
