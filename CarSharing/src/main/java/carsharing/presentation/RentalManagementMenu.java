package carsharing.presentation;

import carsharing.business.*;

import java.util.Scanner;

public class RentalManagementMenu extends AbstractMenu {

    private static final String MENU_TEXT = """
            
            1. Rent a car
            2. Return a rented car
            3. My rented car
            0. Back
            """;

    private final Customer customer;

    public RentalManagementMenu(Scanner scanner, DataService service, Customer customer) {
        super(scanner, service);
        this.customer = customer;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> rentCar();
                case "2" -> returnCar();
                case "3" -> displayRentedCar();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void rentCar() {
        if (customer.getRentedCarId() == null) {
            selectCompany();
        } else {
            System.out.println("\nYou've already rented a car!");
        }
    }

    private void selectCompany() {
        displayAndSelectFromList(
                service.listCompanies(),
                "The company list is empty!",
                "Choose a company:",
                Company::name,
                this::selectCar
        );
    }

    private void selectCar(Company company) {
        displayAndSelectFromList(
                service.listAvailableCars(company),
                "No available cars in the '%s' company.".formatted(company.name()),
                "Choose a car:",
                Car::name,
                car -> {
                    service.rentCar(customer, car);
                    customer.setRentedCarId(car.id());
                    System.out.printf("%nYou rented '%s'%n".formatted(car.name()));
                }
        );
    }

    private void returnCar() {
        if (customer.getRentedCarId() != null) {
            service.returnCar(customer);
            customer.setRentedCarId(null);
            System.out.println("\nYou've returned a rented car!");
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }

    private void displayRentedCar() {
        Integer carId = customer.getRentedCarId();
        if (carId != null) {
            Car car = service.getCarById(carId).orElseThrow();
            Company company = service.getCompanyById(car.companyId()).orElseThrow();
            System.out.printf("""
                    
                    Your rented car:
                    %s
                    Company:
                    %s
                    """, car.name(), company.name());
        } else {
            System.out.println("\nYou didn't rent a car!");
        }
    }
}
