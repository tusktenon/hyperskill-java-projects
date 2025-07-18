package mealplanner;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:postgresql:meals_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1111";

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Scanner in = new Scanner(System.in)) {
            conn.setAutoCommit(true);
            new Menu(in, new DbMealDao(conn)).run();
        }
    }
}
