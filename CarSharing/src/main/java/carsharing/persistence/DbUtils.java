package carsharing.persistence;

import java.sql.*;

public class DbUtils {

    private static final String CREATE_COMPANY_TABLE = """
            CREATE TABLE IF NOT EXISTS company (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR NOT NULL UNIQUE
            )""";

    private static final String CREATE_CAR_TABLE = """
            CREATE TABLE IF NOT EXISTS car (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR NOT NULL UNIQUE,
                company_id INT NOT NULL,
                CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(id)
            )""";

    public static void createTables(Connection connection) {
        run(connection, CREATE_COMPANY_TABLE);
        run(connection, CREATE_CAR_TABLE);
    }

    private static void run(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
