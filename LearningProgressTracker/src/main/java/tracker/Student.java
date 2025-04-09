package tracker;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

class Student {

    // For efficiency, maintain a static copy of Course.values()
    private static final Course[] COURSES = Course.values();

    private int id;
    private final String email;
    private final int[] points = new int[COURSES.length];
    private final int[] submissions = new int[COURSES.length];

    Student(int id, String email) {
        this.id = id;
        this.email = email;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    int[] getPoints() {
        return points.clone();
    }

    int getPoints(int courseIndex) {
        return points[courseIndex];
    }

    int getSubmissions(int courseIndex) {
        return submissions[courseIndex];
    }

    double percentCompleted(int courseIndex) {
        return ((double) (100 * points[courseIndex])) / COURSES[courseIndex].completionPoints();
    }

    void updateRecord(int[] update) {
        if (update.length != COURSES.length || Arrays.stream(update).anyMatch(i -> i < 0))
            throw new IllegalArgumentException();
        IntStream.range(0, COURSES.length)
                .filter(i -> update[i] > 0)
                .forEach(i -> {
                    points[i] += update[i];
                    submissions[i]++;
                });
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        Student student = (Student) other;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
