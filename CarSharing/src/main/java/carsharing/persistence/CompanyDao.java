package carsharing.persistence;

import carsharing.business.Company;

import java.util.List;
import java.util.Optional;

public class CompanyDao {

    private final DbClient dbClient;

    public CompanyDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public void add(String name) {
        dbClient.update("INSERT INTO company (name) VALUES ('%s')".formatted(name));
    }

    public Optional<Company> findById(int id) {
        return dbClient.uniqueCompanyQuery("SELECT * FROM company WHERE id = %d".formatted(id));
    }

    public List<Company> findAllOrderById() {
        return dbClient.companyQuery("SELECT * FROM company ORDER BY id");
    }
}
