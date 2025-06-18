package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String databaseFileName = (args.length == 2 && "-databaseFileName".equals(args[0]))
                ? args[1]
                : "carsharing";

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:./db/" + databaseFileName);

        try (Connection conn = dataSource.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            conn.setAutoCommit(true);
            new Application(scanner, new CompanyDao(conn)).mainMenu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
