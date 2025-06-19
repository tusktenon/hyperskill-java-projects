package carsharing.presentation;

import carsharing.business.Company;
import carsharing.business.ManagementService;

import java.util.List;
import java.util.Scanner;

public class CompanyManagementMenu implements Menu {

    private static final String MENU_TEXT = """
            
            1. Company list
            2. Create a company
            0. Back
            """;

    private final Scanner scanner;
    private final ManagementService service;

    public CompanyManagementMenu(Scanner scanner, ManagementService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> listCompanies();
                case "2" -> addCompany();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void listCompanies() {
        List<Company> companies = service.listCompanies();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return;
        }
        while (true) {
            System.out.println("\nChoose a company:");
            companies.forEach(
                    company -> System.out.printf("%d. %s%n", company.id(), company.name()));
            System.out.println("0. Back");

            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) {
                    return;
                } else if (1 <= selection && selection <= companies.size()) {
                    new FleetManagementMenu(scanner, service, companies.get(selection - 1)).run();
                    return;
                } else {
                    System.out.println("Invalid selection");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
            }
        }
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
        service.addCompany(scanner.nextLine());
        System.out.println("The company was created!");
    }
}
