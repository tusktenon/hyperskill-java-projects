package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao {

    private final Connection connection;

    public CarDao(Connection connection) {
        this.connection = connection;
        String sql = """
                CREATE TABLE IF NOT EXISTS car (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR NOT NULL UNIQUE,
                    company_id INT NOT NULL,
                    CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(id)
                )""";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String name, int companyId) {
        String sql = "INSERT INTO car (name, company_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, companyId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> findByCompanyOrderById(int companyId) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car WHERE company_id = ? ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, companyId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                cars.add(new Car(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
