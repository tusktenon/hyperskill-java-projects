package mealplanner;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            new Menu(in, new ArrayList<>()).run();
        }
    }
}
