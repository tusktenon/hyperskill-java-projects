package tracker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Statistics {

    private static final int NUMBER_OF_COURSES = Platform.COURSES.length;

    private final Platform platform;
    private final EnumMap<CourseProperty, List<String>> statistics =
            new EnumMap<>(CourseProperty.class);

    Statistics(Platform platform) {
        this.platform = platform;
    }

    void calculate() {
        statistics.clear();
        int[] enrollments = new int[NUMBER_OF_COURSES];
        int[] submissions = new int[NUMBER_OF_COURSES];
        long[] points = new long[NUMBER_OF_COURSES];

        platform.students().forEach(student ->
                IntStream.range(0, NUMBER_OF_COURSES)
                        .filter(i -> student.getSubmissions(i) > 0)
                        .forEach(i -> {
                            enrollments[i]++;
                            submissions[i] += student.getSubmissions(i);
                            points[i] += student.getPoints(i);
                        }));

        if (Arrays.stream(enrollments).allMatch(e -> e == 0)) {
            Arrays.stream(CourseProperty.values()).forEach(key -> statistics.put(key, List.of()));
        } else {
            calculateMostLeastPopular(enrollments);
            calculateMostLeastActive(submissions);
            calculateEasiestHardest(points, submissions);
        }
    }

    private void calculateMostLeastPopular(int[] enrollments) {
        int maxEnrollments = Arrays.stream(enrollments).max().orElseThrow();
        int minEnrollments = Arrays.stream(enrollments).min().orElseThrow();

        List<String> mostPopular = IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> enrollments[i] == maxEnrollments)
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        List<String> leastPopular = (maxEnrollments == minEnrollments)
                ? List.of()
                : IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> enrollments[i] == minEnrollments)
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        statistics.put(CourseProperty.MOST_POPULAR, mostPopular);
        statistics.put(CourseProperty.LEAST_POPULAR, leastPopular);
    }

    private void calculateMostLeastActive(int[] submissions) {
        int maxSubmissions = Arrays.stream(submissions).max().orElseThrow();
        int minSubmissions = Arrays.stream(submissions).min().orElseThrow();

        List<String> mostActive = IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> submissions[i] == maxSubmissions)
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        List<String> leastActive = (maxSubmissions == minSubmissions)
                ? List.of()
                : IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> submissions[i] == minSubmissions)
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        statistics.put(CourseProperty.MOST_ACTIVE, mostActive);
        statistics.put(CourseProperty.LEAST_ACTIVE, leastActive);
    }

    private void calculateEasiestHardest(long[] points, int[] submissions) {
        Double[] averages = IntStream.range(0, NUMBER_OF_COURSES)
                .mapToObj(i -> submissions[i] == 0 ? null : ((double) points[i]) / submissions[i])
                .toArray(Double[]::new);

        Double maxAverage = Arrays.stream(averages)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElseThrow();

        Double minAverage = Arrays.stream(averages)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElseThrow();

        List<String> easiest = IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> maxAverage.equals(averages[i]))
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        List<String> hardest = maxAverage.equals(minAverage)
                ? List.of()
                : IntStream.range(0, NUMBER_OF_COURSES)
                .filter(i -> minAverage.equals(averages[i]))
                .mapToObj(i -> Platform.COURSES[i])
                .toList();

        statistics.put(CourseProperty.EASIEST, easiest);
        statistics.put(CourseProperty.HARDEST, hardest);
    }

    @Override
    public String toString() {
        return statistics.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + courseListToString(entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private static String courseListToString(List<String> list) {
        if (list.isEmpty()) return "n/a";
        return String.join(", ", list);
    }
}
