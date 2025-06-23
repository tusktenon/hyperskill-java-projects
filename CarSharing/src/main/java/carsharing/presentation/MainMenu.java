package carsharing.presentation;

import carsharing.business.Customer;
import carsharing.business.DataService;

import java.util.Scanner;

public class MainMenu extends AbstractMenu {

    private static final String MENU_TEXT = """
            
            1. Log in as a manager
            2. Log in as a customer
            3. Create a customer
            0. Exit
            """;

    public MainMenu(Scanner scanner, DataService service) {
        super(scanner, service);
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> new CompanyManagementMenu(scanner, service).run();
                case "2" -> displayCustomers();
                case "3" -> addCustomer();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void displayCustomers() {
        displayAndSelectFromList(
                service.listCustomers(),
                "The customer list is empty!",
                "Choose a customer:",
                Customer::getName,
                customer -> new RentalManagementMenu(scanner, service, customer).run()
        );
    }

    private void addCustomer() {
        System.out.println("\nEnter the customer name:");
        service.addCustomer(scanner.nextLine());
        System.out.println("The customer was added!");
    }
}
