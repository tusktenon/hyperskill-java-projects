package tracker;

enum Course {
    Java(600), DSA(400), Databases(480), Spring(550);

    private final int completionPoints;

    Course (int completionPoints) {
        this.completionPoints = completionPoints;
    }

    int completionPoints() {
        return completionPoints;
    }
}
