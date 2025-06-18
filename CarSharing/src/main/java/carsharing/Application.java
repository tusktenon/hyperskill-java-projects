package carsharing;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static final String MAIN_MENU_TEXT = """
            
            1. Log in as a manager
            0. Exit
            """;

    private static final String MANAGER_MENU_TEXT = """
            
            1. Company list
            2. Create a company
            0. Back
            """;

    private final Scanner scanner;
    private final CompanyDao companyDao;

    public Application(Scanner scanner, CompanyDao companyDao) {
        this.scanner = scanner;
        this.companyDao = companyDao;
    }

    public void mainMenu() {
        while (true) {
            System.out.print(MAIN_MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> managerMenu();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.print(MANAGER_MENU_TEXT);
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
        List<Company> companies = companyDao.findAllOrderById();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
        } else {
            System.out.println("\nCompany list:");
            companies.forEach(
                    company -> System.out.printf("%d. %s%n", company.id(), company.name()));
        }
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
        companyDao.add(scanner.nextLine());
        System.out.println("The company was created!");
    }
}
