package carsharing.presentation;

import carsharing.business.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FleetManagementMenu implements Menu {

    private static final String MENU_TEXT = """
            1. Car list
            2. Create a car
            0. Back
            """;

    private final Scanner scanner;
    private final ManagementService service;
    private final Company company;

    public FleetManagementMenu(Scanner scanner, ManagementService service, Company company) {
        this.scanner = scanner;
        this.service = service;
        this.company = company;
    }

    @Override
    public void run() {
        System.out.printf("%n'%s' company:%n", company.name());
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> listCars();
                case "2" -> addCar();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
            System.out.println();
        }
    }

    private void listCars() {
        List<Car> cars = service.listFleet(company);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            IntStream.range(0, cars.size())
                    .forEachOrdered(i -> System.out.printf("%d. %s%n", i + 1, cars.get(i).name()));
        }
    }

    private void addCar() {
        System.out.println("\nEnter the car name:");
        service.addToFleet(scanner.nextLine(), company);
        System.out.println("The car was added!");
    }
}
