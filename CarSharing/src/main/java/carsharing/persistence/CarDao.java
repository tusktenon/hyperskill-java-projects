package carsharing.persistence;

import carsharing.business.Car;

import java.util.List;
import java.util.Optional;

public class CarDao {

    private final DbClient dbClient;

    public CarDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public void add(String name, int companyId) {
        dbClient.update(
                "INSERT INTO car (name, company_id) VALUES ('%s', %d)".formatted(name, companyId));
    }

    public Optional<Car> findById(int id) {
        return dbClient.uniqueCarQuery("SELECT * FROM car WHERE id = %d".formatted(id));
    }

    public List<Car> findByCompanyOrderById(int companyId) {
        return dbClient.carQuery(
                "SELECT * FROM car WHERE company_id = %d ORDER BY id".formatted(companyId));
    }

    public List<Car> findAvailableByCompanyOrderById(int companyId) {
        return dbClient.carQuery("""
                SELECT * FROM car
                WHERE company_id = %d
                AND id NOT IN (
                    SELECT rented_car_id FROM customer WHERE rented_car_id IS NOT NULL
                )
                ORDER BY id""".formatted(companyId));
    }
}
