package traffic;

public class QueueThread extends Thread {

    private static final String displayTemplate = """
            ! %ds. have passed since system startup !
            ! Number of roads: %d !
            ! Interval: %d !
            ! Press "Enter" to open menu !
            """;

    private final int roads;
    private final int interval;
    private long seconds = 0;
    private boolean display = false;
    private boolean shutdown = false;

    QueueThread(int roads, int interval) {
        this.roads = roads;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!shutdown) {
            if (display) {
                ConsoleUtils.clearConsole();
                System.out.printf(displayTemplate, seconds, roads, interval);
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

    public void shutdown() {
        shutdown = true;
    }
}
