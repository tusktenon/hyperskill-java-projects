package tracker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            Admin admin = new Admin(new StudentRegistry(), in);
            admin.run();
        }
    }
}
