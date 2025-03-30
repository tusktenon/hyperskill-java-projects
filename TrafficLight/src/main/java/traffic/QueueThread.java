package traffic;

import java.util.Optional;

public class QueueThread extends Thread {

    private static final String displayTemplate = """
            ! %ds. have passed since system startup !
            ! Number of roads: %d !
            ! Interval: %d !
            """;

    // Maximum number of roads
    private final int roads;

    // Time between road state (open/closed) changes
    private final int interval;

    // Queue containing the roads
    private final CircularQueue queue;

    // Time since system startup
    private long seconds = 0;

    // Time remaining until next road state (open/closed) change
    private int countdown;

    // The road queue as an array
    private String[] list;

    // The index of the currently open road within the roads array
    private int openIndex = 0;

    // Flag to display system state (as opposed to main menu)
    private boolean display = false;

    // Flag to shut down the system
    private boolean shutdown = false;

    QueueThread(int roads, int interval) {
        this.roads = roads;
        this.interval = interval;
        this.countdown = interval;
        queue = new CircularQueue(roads);
        list = queue.toArray();
    }

    @Override
    public void run() {
        while (!shutdown) {
            if (display) {
                ConsoleUtils.clearConsole();
                System.out.printf(displayTemplate, seconds, roads, interval);
                displayRoadStates();
                System.out.println("\n! Press \"Enter\" to open menu !");
            }
            try {
                sleep(1000);
                seconds++;
                // In order to pass Hyperskill's tests, road states cannot be updated for the first
                // second after system startup
                if (seconds > 1) {
                    countdown = (countdown == 1) ? interval : countdown - 1;
                    if (countdown == interval)
                        openIndex = (openIndex == list.length - 1) ? 0 : openIndex + 1;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException("Queue Thread Interrupted");
            }
        }
    }

    public void displaySystemState(boolean display) {
        this.display = display;
    }

    public boolean addRoad(String name) {
        boolean result = queue.enqueue(name);
        if (result) list = queue.toArray();
        return result;
    }

    public Optional<String> deleteRoad() {
        Optional<String> result = queue.dequeue();
        if (result.isPresent()) {
            openIndex--;
            list = queue.toArray();
        }
        return result;
    }

    public void shutdown() {
        shutdown = true;
    }

    private void displayRoadStates() {
        for (int i = 0; i < list.length; i++) {
            if (i == openIndex) {
                System.out.printf("%s%s will be open for %ds.%s\n",
                        "\u001B[32m", list[i], countdown, "\u001B[0m");
            } else {
                int distanceFromOpen = i > openIndex ? i - openIndex : list.length - openIndex + i;
                int secondsUntilOpen = (distanceFromOpen - 1) * interval + countdown;
                System.out.printf("%s%s will be closed for %ds.%s\n",
                        "\u001B[31m", list[i], secondsUntilOpen, "\u001B[0m");
            }
        }
    }
}
