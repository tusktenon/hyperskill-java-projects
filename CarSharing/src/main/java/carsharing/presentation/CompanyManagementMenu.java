package carsharing.presentation;

import carsharing.business.Company;
import carsharing.business.DataService;

import java.util.Scanner;

public class CompanyManagementMenu extends AbstractMenu {

    private static final String MENU_TEXT = """
            
            1. Company list
            2. Create a company
            0. Back
            """;

    public CompanyManagementMenu(Scanner scanner, DataService service) {
        super(scanner, service);
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> displayCompanies();
                case "2" -> addCompany();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void displayCompanies() {
        displayAndSelectFromList(
                service.listCompanies(),
                "The company list is empty!",
                "Choose a company:",
                Company::name,
                company -> new FleetManagementMenu(scanner, service, company).run()
        );
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
        service.addCompany(scanner.nextLine());
        System.out.println("The company was created!");
    }
}
