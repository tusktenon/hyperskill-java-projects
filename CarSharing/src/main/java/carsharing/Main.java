package carsharing;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String databaseFileName = (args.length == 2 && "-databaseFileName".equals(args[0]))
                ? args[1]
                : "carsharing";

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:./db/" + databaseFileName);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(true);
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("""
                        CREATE TABLE IF NOT EXISTS COMPANY (
                            ID INT,
                            NAME VARCHAR
                        )
                        """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
