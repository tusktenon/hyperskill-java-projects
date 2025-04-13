package tracker;

import java.util.*;
import java.util.stream.IntStream;

class Statistics {

    // For efficiency, maintain a static copy of Course.values()
    private static final Course[] COURSES = Course.values();

    private record CourseListPair(List<Course> maxList, List<Course> minList) {}

    static void displayCourseProperties(StudentRegistry registry) {
        courseProperties(registry).forEach((key, value) ->
                System.out.println(key + ": " + courseListToString(value)));
    }

    static void displayCourseDetails(StudentRegistry registry, int courseIndex) {
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

    static Map<CourseProperty, List<Course>> courseProperties(StudentRegistry registry) {
        EnumMap<CourseProperty, List<Course>> properties = new EnumMap<>(CourseProperty.class);
        int[] enrollments = new int[COURSES.length];
        int[] submissions = new int[COURSES.length];
        long[] points = new long[COURSES.length];

        registry.students().forEach(student ->
                IntStream.range(0, COURSES.length)
                        .filter(i -> student.getSubmissions(i) > 0)
                        .forEach(i -> {
                            enrollments[i]++;
                            submissions[i] += student.getSubmissions(i);
                            points[i] += student.getPoints(i);
                        }));

        if (Arrays.stream(enrollments).allMatch(e -> e == 0)) {
            Arrays.stream(CourseProperty.values()).forEach(key -> properties.put(key, List.of()));
        } else {
            CourseListPair mostLeastPopular = calculateMostLeastPopular(enrollments);
            properties.put(CourseProperty.MOST_POPULAR, mostLeastPopular.maxList());
            properties.put(CourseProperty.LEAST_POPULAR, mostLeastPopular.minList());

            CourseListPair mostLeastActive = calculateMostLeastActive(submissions);
            properties.put(CourseProperty.MOST_ACTIVE, mostLeastActive.maxList());
            properties.put(CourseProperty.LEAST_ACTIVE, mostLeastActive.minList());

            CourseListPair easiestHardest = calculateEasiestHardest(points, submissions);
            properties.put(CourseProperty.EASIEST, easiestHardest.maxList());
            properties.put(CourseProperty.HARDEST, easiestHardest.minList());
        }
        return properties;
    }

    private static String courseListToString(List<Course> list) {
        if (list.isEmpty()) return "n/a";
        return String.join(", ", list.stream().map(Course::toString).toList());
    }

    private static CourseListPair calculateMostLeastPopular(int[] enrollments) {
        IntSummaryStatistics enrollmentSummary = Arrays.stream(enrollments).summaryStatistics();

        List<Course> mostPopular = IntStream.range(0, COURSES.length)
                .filter(i -> enrollments[i] == enrollmentSummary.getMax())
                .mapToObj(i -> COURSES[i])
                .toList();

        List<Course> leastPopular = enrollmentSummary.getMin() == enrollmentSummary.getMax()
                ? List.of()
                : IntStream.range(0, COURSES.length)
                .filter(i -> enrollments[i] == enrollmentSummary.getMin())
                .mapToObj(i -> COURSES[i])
                .toList();

        return new CourseListPair(mostPopular, leastPopular);
    }

    private static CourseListPair calculateMostLeastActive(int[] submissions) {
        IntSummaryStatistics submissionSummary = Arrays.stream(submissions).summaryStatistics();

        List<Course> mostActive = IntStream.range(0, COURSES.length)
                .filter(i -> submissions[i] == submissionSummary.getMax())
                .mapToObj(i -> COURSES[i])
                .toList();

        List<Course> leastActive = submissionSummary.getMin() == submissionSummary.getMax()
                ? List.of()
                : IntStream.range(0, COURSES.length)
                .filter(i -> submissions[i] == submissionSummary.getMin())
                .mapToObj(i -> COURSES[i])
                .toList();

        return new CourseListPair(mostActive, leastActive);
    }

    private static CourseListPair calculateEasiestHardest(long[] points, int[] submissions) {
        Double[] averages = IntStream.range(0, COURSES.length)
                .mapToObj(i -> submissions[i] == 0 ? null : ((double) points[i]) / submissions[i])
                .toArray(Double[]::new);

        DoubleSummaryStatistics averageSummary = Arrays.stream(averages)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        List<Course> easiest = IntStream.range(0, COURSES.length)
                .filter(i -> Double.valueOf(averageSummary.getMax()).equals(averages[i]))
                .mapToObj(i -> COURSES[i])
                .toList();

        List<Course> hardest = averageSummary.getMin() == averageSummary.getMax()
                ? List.of()
                : IntStream.range(0, COURSES.length)
                .filter(i -> Double.valueOf(averageSummary.getMin()).equals(averages[i]))
                .mapToObj(i -> COURSES[i])
                .toList();

        return new CourseListPair(easiest, hardest);
    }
}
