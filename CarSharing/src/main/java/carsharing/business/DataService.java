package carsharing.business;

import carsharing.persistence.*;

import java.util.List;
import java.util.Optional;

public class DataService {

    private final CarDao carDao;
    private final CompanyDao companyDao;
    private final CustomerDao customerDao;

    public DataService(CarDao carDao, CompanyDao companyDao, CustomerDao customerDao) {
        this.carDao = carDao;
        this.companyDao = companyDao;
        this.customerDao = customerDao;
    }

    public void addCarToFleet(String carName, Company company) {
        carDao.add(carName, company.id());
    }

    public void addCompany(String name) {
        companyDao.add(name);
    }

    public void addCustomer(String name) {
        customerDao.add(name);
    }

    public Optional<Car> getCarById(int id) {
        return carDao.findById(id);
    }

    public Optional<Company> getCompanyById(int id) {
        return companyDao.findById(id);
    }

    public List<Car> listAvailableCars(Company company) {
        return carDao.findAvailableByCompanyOrderById(company.id());
    }

    public List<Company> listCompanies() {
        return companyDao.findAllOrderById();
    }

    public List<Customer> listCustomers() {
        return customerDao.findAllOrderById();
    }

    public List<Car> listFleet(Company company) {
        return carDao.findByCompanyOrderById(company.id());
    }

    public void rentCar(Customer customer, Car car) {
        customerDao.updateByIdSetRentedCarId(customer.getId(), car.id());
    }

    public void returnCar(Customer customer) {
        customerDao.updateByIdResetRentedCarId(customer.getId());
    }
}
