package taskmanagement.presentation;

public interface Views {
    interface TaskViews {
        interface WithoutCommentCount {}
        interface WithCommentCount extends WithoutCommentCount {}
    }
}
