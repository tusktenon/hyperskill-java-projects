package carsharing.persistence;

import carsharing.business.*;

import java.sql.*;
import java.util.*;

public class DbClient {

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

    private static final String CREATE_CUSTOMER_TABLE = """
            CREATE TABLE IF NOT EXISTS customer (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR NOT NULL UNIQUE,
                rented_car_id INT,
                CONSTRAINT fk_car FOREIGN KEY (rented_car_id) REFERENCES car(id)
            )""";

    private static final ResultSetMapper<Car> CAR_MAPPER =
            resultSet -> new Car(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("company_id")
            );

    private static final ResultSetMapper<Company> COMPANY_MAPPER =
            resultSet -> new Company(resultSet.getInt("id"), resultSet.getString("name"));

    private static final ResultSetMapper<Customer> CUSTOMER_MAPPER =
            resultSet -> {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer rentedCarId = resultSet.getInt("rented_car_id");
                if (rentedCarId == 0) rentedCarId = null;
                return new Customer(id, name, rentedCarId);
            };

    private final Connection connection;

    public DbClient(Connection connection) {
        this.connection = connection;
    }

    public void createTables() {
        update(CREATE_COMPANY_TABLE);
        update(CREATE_CAR_TABLE);
        update(CREATE_CUSTOMER_TABLE);
    }

    public void update(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <E> List<E> query(String sql, ResultSetMapper<E> mapper) {
        List<E> list = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)
        ) {
            while (results.next()) {
                list.add(mapper.map(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public <E> Optional<E> uniqueQuery(String sql, ResultSetMapper<E> mapper) {
        List<E> results = query(sql, mapper);
        return switch (results.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(results.getFirst());
            default -> throw new IllegalStateException("Query returned multiple results.");
        };
    }

    public List<Car> carQuery(String sql) {
        return query(sql, CAR_MAPPER);
    }

    public List<Company> companyQuery(String sql) {
        return query(sql, COMPANY_MAPPER);
    }

    public List<Customer> customerQuery(String sql) {
        return query(sql, CUSTOMER_MAPPER);
    }

    public Optional<Car> uniqueCarQuery(String sql) {
        return uniqueQuery(sql, CAR_MAPPER);
    }

    public Optional<Company> uniqueCompanyQuery(String sql) {
        return uniqueQuery(sql, COMPANY_MAPPER);
    }
}
