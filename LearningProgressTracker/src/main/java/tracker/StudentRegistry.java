package tracker;

import java.util.*;
import java.util.stream.Stream;

class StudentRegistry {

    private final Set<Student> students = new LinkedHashSet<>();
    private final Set<String> emails = new HashSet<>();
    private final Map<Integer, Student> studentIdMap = new HashMap<>();
    private static int nextStudentId = 1;

    boolean add(NewStudent newStudent) {
        boolean added = emails.add(newStudent.email());
        if (added) {
            Student student = new Student(nextStudentId, newStudent.email(), newStudent.fullName());
            students.add(student);
            studentIdMap.put(nextStudentId, student);
            nextStudentId++;
        }
        return added;
    }

    int size() {
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
