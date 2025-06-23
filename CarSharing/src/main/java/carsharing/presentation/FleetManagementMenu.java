package carsharing.presentation;

import carsharing.business.*;

import java.util.Scanner;

public class FleetManagementMenu extends AbstractMenu {

    private static final String MENU_TEXT = """
            1. Car list
            2. Create a car
            0. Back
            """;

    private final Company company;

    public FleetManagementMenu(Scanner scanner, DataService service, Company company) {
        super(scanner, service);
        this.company = company;
    }

    @Override
    public void run() {
        System.out.printf("%n'%s' company:%n", company.name());
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> displayFleet();
                case "2" -> addCar();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
            System.out.println();
        }
    }

    private void displayFleet() {
        displayList(service.listFleet(company), "The car list is empty!", "Car list:", Car::name);
    }

    private void addCar() {
        System.out.println("\nEnter the car name:");
        service.addCarToFleet(scanner.nextLine(), company);
        System.out.println("The car was added!");
    }
}
