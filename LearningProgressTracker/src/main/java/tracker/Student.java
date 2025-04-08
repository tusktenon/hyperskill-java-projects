package tracker;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

class Student {
    private int id;
    private final String email;
    private final int[] points;

    Student (int id, String email) {
        this.id = id;
        this.email = email;
        points = new int[Platform.COURSES.length];
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

    void addPoints(int[] update) {
        if (update.length != points.length || Arrays.stream(update).anyMatch(i -> i < 0))
            throw new IllegalArgumentException("Invalid points update");
        IntStream.range(0, points.length).forEach(i -> points[i] += update[i]);
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
