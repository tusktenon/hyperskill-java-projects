package tracker;

import java.util.*;
import java.util.stream.Stream;

class Platform {

    static final String[] COURSES = {"Java", "DSA", "Databases", "Spring"};
    static final int[] COURSE_COMPLETION_POINTS = { 600, 400, 480, 550 };

    private final Set<Student> students = new LinkedHashSet<>();
    private final Map<Integer, Student> studentIdMap = new HashMap<>();
    private static int nextStudentId = 1;

    boolean addStudent(Student student) {
        boolean added = students.add(student);
        if (added) {
            student.setId(nextStudentId);
            studentIdMap.put(nextStudentId, student);
            nextStudentId++;
        }
        return added;
    }

    int studentCount() {
        return students.size();
    }

    Optional<Student> getStudentById(int id) {
        return Optional.ofNullable(studentIdMap.get(id));
    }

    Optional<Student> getStudentById(String id) {
        try {
            return getStudentById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    Stream<Student> students() {
        return students.stream();
    }
}
