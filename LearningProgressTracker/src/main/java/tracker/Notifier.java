package tracker;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

public class Notifier {

    // For efficiency, maintain a static copy of Course.values()
    private static final Course[] COURSES = Course.values();

    private static final String TEMPLATE = """
            To: %s
            Re: Your Learning Progress
            Hello, %s! You have accomplished our %s course!
            """;

    private record Pair(Student student, Course course) {}

    private final Queue<Pair> pending = new ArrayDeque<>();

    void wrapStudentUpdate(Student student, int[] update) {
        int[] before = student.getPoints();
        student.updateRecord(update);
        int[] after = student.getPoints();
        IntStream.range(0, COURSES.length)
                .filter(i -> {
                    int required = COURSES[i].completionPoints();
                    return before[i] < required && required <= after[i];
                })
                .forEach(i -> pending.add(new Pair(student, COURSES[i])));
    }

    int sendAll() {
        Set<Student> studentsNotified = new HashSet<>();
        while (!pending.isEmpty()) {
            Pair p = pending.poll();
            studentsNotified.add(p.student);
            System.out.printf(TEMPLATE, p.student.getEmail(), p.student.getFullName(), p.course);
        }
        return studentsNotified.size();
    }
}
