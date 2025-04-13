package tracker;

import java.util.*;
import java.util.stream.Stream;

class StudentRegistry {

    // Since student ID numbers are sequentially assigned, we could also use a TreeMap here
    private final Map<Integer, Student> students = new LinkedHashMap<>();

    // For efficient validation of new students
    private final Set<String> emails = new HashSet<>();

    private static int nextStudentId = 1;

    boolean add(NewStudent newStudent) {
        boolean added = emails.add(newStudent.email());
        if (added) {
            Student student = new Student(nextStudentId, newStudent.email(), newStudent.fullName());
            students.put(nextStudentId, student);
            nextStudentId++;
        }
        return added;
    }

    int size() {
        return students.size();
    }

    Optional<Student> getStudentById(int id) {
        return Optional.ofNullable(students.get(id));
    }

    Optional<Student> getStudentById(String id) {
        try {
            return getStudentById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    Stream<Student> students() {
        return students.values().stream();
    }
}
