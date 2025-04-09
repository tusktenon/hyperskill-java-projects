package tracker;

public enum CourseProperty {
    MOST_POPULAR, LEAST_POPULAR, MOST_ACTIVE, LEAST_ACTIVE, EASIEST, HARDEST;

    @Override
    public String toString() {
        return switch (this) {
            case MOST_POPULAR -> "Most popular";
            case LEAST_POPULAR -> "Least popular";
            case MOST_ACTIVE -> "Highest activity";
            case LEAST_ACTIVE -> "Lowest activity";
            case EASIEST -> "Easiest course";
            case HARDEST -> "Hardest course";
        };
    }
}
