package traffic;

import java.util.Optional;

public class QueueThread extends Thread {

    private static final String displayTemplate = """
            ! %ds. have passed since system startup !
            ! Number of roads: %d !
            ! Interval: %d !
            """;

    private final int roads;
    private final int interval;
    private final CircularQueue queue;
    private long seconds = 0;
    private boolean display = false;
    private boolean shutdown = false;

    QueueThread(int roads, int interval) {
        this.roads = roads;
        this.interval = interval;
        queue = new CircularQueue(roads);
    }

    @Override
    public void run() {
        while (!shutdown) {
            if (display) {
                ConsoleUtils.clearConsole();
                System.out.printf(displayTemplate, seconds, roads, interval);
                if (!queue.isEmpty()) System.out.println("\n" + queue);
                System.out.println("! Press \"Enter\" to open menu !");
            }
            try {
                sleep(1000);
                seconds++;
            } catch (InterruptedException e) {
                throw new RuntimeException("Queue Thread Interrupted");
            }
        }
    }

    public void displaySystemState(boolean display) {
        this.display = display;
    }

    public boolean addRoad(String name) {
        return queue.enqueue(name);
    }

    public Optional<String> deleteRoad() {
        return queue.dequeue();
    }

    public void shutdown() {
        shutdown = true;
    }
}
