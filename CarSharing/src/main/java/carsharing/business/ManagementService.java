package carsharing.business;

import carsharing.persistence.CarDao;
import carsharing.persistence.CompanyDao;

import java.util.List;

public class ManagementService {

    private final CarDao carDao;
    private final CompanyDao companyDao;

    public ManagementService(CarDao carDao, CompanyDao companyDao) {
        this.carDao = carDao;
        this.companyDao = companyDao;
    }

    public void addCompany(String name) {
        companyDao.add(name);
    }

    public List<Company> listCompanies() {
        return companyDao.findAllOrderById();
    }

    public List<Car> listFleet(Company company) {
        return carDao.findByCompanyOrderById(company.id());
    }

    public void addToFleet(String carName, Company company) {
        carDao.add(carName, company.id());
    }
}
