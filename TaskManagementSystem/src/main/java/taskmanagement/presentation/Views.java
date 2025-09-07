package taskmanagement.presentation;

public interface Views {
    public static interface TaskViews {
        public static interface WithoutCommentCount {}
        public static interface WithCommentCount extends WithoutCommentCount {}
    }
}
