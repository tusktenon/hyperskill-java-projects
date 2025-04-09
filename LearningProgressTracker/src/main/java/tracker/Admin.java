package tracker;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Admin {

    static final Pattern VALID_EMAIL = Pattern.compile("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+");
    static final Pattern VALID_NAME = Pattern.compile("([A-Za-z][-']?)+[A-Za-z]");

    // For efficiency, maintain a static copy of Course.values()
    private static final Course[] COURSES = Course.values();

    private final StudentRegistry registry;
    private final Statistics statistics;
    private final Scanner in;

    Admin(StudentRegistry registry, Scanner in) {
        this.registry = registry;
        this.statistics = new Statistics(registry);
        this.in = in;
    }

    void run() {
        System.out.println("Learning Progress Tracker");

        while (true) {
            switch (in.nextLine().trim().toLowerCase()) {
                case "add points" -> addPoints();
                case "add students" -> addStudents();
                case "find" -> findAndDisplayStudent();
                case "list" -> listStudents();
                case "statistics" -> displayStatistics();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> {
                    System.out.println("Bye!");
                    return;
                }
                case "" -> System.out.println("No input.");
                default -> System.out.println("Unknown command!");
            }
        }
    }

    private void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");

        while (true) {
            String input = in.nextLine();
            if ("back".equalsIgnoreCase(input.trim())) return;

            try {
                String[] tokens = input.split("\\s+");
                if (tokens.length != 1 + COURSES.length)
                    throw new IllegalArgumentException();
                String id = tokens[0];
                int[] update = Arrays.stream(tokens).skip(1).mapToInt(Integer::parseInt).toArray();
                registry.getStudentById(id).ifPresentOrElse(
                        student -> {
                            student.updateRecord(update);
                            System.out.println("Points updated.");
                        },
                        () -> System.out.printf("No student is found for id=%s.\n", id)
                );
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect points format.");
            }
        }
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String input = in.nextLine();
            if ("back".equalsIgnoreCase(input.trim())) {
                System.out.printf("Total %d students have been added.\n", registry.studentCount());
                return;
            }
            try {
                Student student = parseStudent(input);
                if (registry.addStudent(student))
                    System.out.println("The student has been added.");
                else
                    System.out.println("This email is already taken.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void findAndDisplayStudent() {
        System.out.println("Enter an id or 'back' to return:");

        while (true) {
            String input = in.nextLine();
            if ("back".equalsIgnoreCase(input.trim())) return;

            registry.getStudentById(input).ifPresentOrElse(
                    Admin::displayStudentInfo,
                    () -> System.out.printf("No student is found for id=%s.\n", input));
        }
    }

    private void listStudents() {
        if (registry.studentCount() > 0) {
            System.out.println("Students:");
            registry.students().map(Student::getId).forEachOrdered(System.out::println);
        } else {
            System.out.println("No students found");
        }
    }

    private void displayStatistics() {
        statistics.calculate();
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        System.out.println(statistics);

        while (true) {
            String input = in.nextLine().trim();
            if ("back".equalsIgnoreCase(input)) return;
            IntStream.range(0, COURSES.length)
                    .filter(i -> COURSES[i].toString().equalsIgnoreCase(input))
                    .findFirst()
                    .ifPresentOrElse(
                            this::displayDetailedStatistics,
                            () -> System.out.println("Unknown course"));
        }
    }

    private void displayDetailedStatistics(int courseIndex) {
        System.out.println(COURSES[courseIndex]);
        System.out.println("id\tpoints\tcompleted");
        registry.students()
                .filter(student -> student.getSubmissions(courseIndex) > 0)
                .sorted((a, b) -> Double.compare(
                        b.percentCompleted(courseIndex), a.percentCompleted(courseIndex)))
                .forEachOrdered(
                        student -> System.out.printf("%d\t%d\t%.1f%%\n",
                                student.getId(),
                                student.getPoints(courseIndex),
                                student.percentCompleted(courseIndex)));
    }

    private static Student parseStudent(String input) throws Exception {
        String[] tokens = input.split("\\s+");
        if (tokens.length < 3)
            throw new Exception("Incorrect credentials.");
        if (!VALID_NAME.matcher(tokens[0]).matches())
            throw new Exception("Incorrect first name.");
        for (int i = 1; i < tokens.length - 1; i++) {
            if (!VALID_NAME.matcher(tokens[i]).matches())
                throw new Exception("Incorrect last name.");
        }
        String email = tokens[tokens.length - 1];
        if (!VALID_EMAIL.matcher(email).matches())
            throw new Exception("Incorrect email.");
        return new Student(0, email);
    }

    private static void displayStudentInfo(Student student) {
        int[] points = student.getPoints();
        System.out.print(student.getId() + " points: ");
        System.out.println(
                IntStream.range(0, COURSES.length)
                        .mapToObj(i -> COURSES[i].toString() + '=' + points[i])
                        .collect(Collectors.joining("; ")));
    }
}
