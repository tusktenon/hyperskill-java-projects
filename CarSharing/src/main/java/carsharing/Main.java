package carsharing;

import carsharing.business.ManagementService;
import carsharing.persistence.*;
import carsharing.presentation.MainMenu;
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

        try (Connection connection = dataSource.getConnection();
             Scanner scanner = new Scanner(System.in)) {
            connection.setAutoCommit(true);
            DbUtils.createTables(connection);
            var carDao = new CarDao(connection);
            var companyDao = new CompanyDao(connection);
            new MainMenu(scanner, new ManagementService(carDao, companyDao)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
