package carsharing.persistence;

import carsharing.business.Customer;

import java.util.List;

public class CustomerDao {

    private final DbClient dbClient;

    public CustomerDao(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public void add(String name) {
        dbClient.update("INSERT INTO customer (name) VALUES ('%s')".formatted(name));
    }

    public List<Customer> findAllOrderById() {
        return dbClient.customerQuery("SELECT * FROM customer ORDER BY id");
    }

    public void updateByIdSetRentedCarId(int id, int rentedCarId) {
        dbClient.update("UPDATE customer SET rented_car_id = %d WHERE id = %d"
                .formatted(rentedCarId, id));
    }

    public void updateByIdResetRentedCarId(int id) {
        dbClient.update("UPDATE customer SET rented_car_id = NULL WHERE id = %d".formatted(id));
    }
}
