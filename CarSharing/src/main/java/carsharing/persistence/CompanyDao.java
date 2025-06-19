package carsharing.persistence;

import carsharing.business.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {

    private final Connection connection;

    public CompanyDao(Connection connection) {
        this.connection = connection;
    }

    public void add(String name) {
        String sql = "INSERT INTO company (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Company> findAllOrderById() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM company ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                int id = results.getInt("id");
                String name = results.getString("name");
                companies.add(new Company(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }
}
