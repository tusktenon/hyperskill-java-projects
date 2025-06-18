package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Application {

    private static final String MAIN_MENU_TEXT = """
            
            1. Log in as a manager
            0. Exit
            """;

    private static final String COMPANY_MENU_TEXT = """
            
            1. Company list
            2. Create a company
            0. Back
            """;

    private static final String CAR_MENU_TEXT = """
            1. Car list
            2. Create a car
            0. Back
            """;

    private final Scanner scanner;
    private final CompanyDao companyDao;
    private final CarDao carDao;

    public Application(Scanner scanner, CompanyDao companyDao, CarDao carDao) {
        this.scanner = scanner;
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public void mainMenu() {
        while (true) {
            System.out.print(MAIN_MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> companyMenu();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private void companyMenu() {
        while (true) {
            System.out.print(COMPANY_MENU_TEXT);
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
                    carMenu(companies.get(selection - 1));
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
        companyDao.add(scanner.nextLine());
        System.out.println("The company was created!");
    }

    private void carMenu(Company company) {
        System.out.printf("%n'%s' company:%n", company.name());
        while (true) {
            System.out.print(CAR_MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> listCars(company.id());
                case "2" -> addCar(company.id());
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
            System.out.println();
        }
    }

    private void listCars(int companyId) {
        List<Car> cars = carDao.findByCompanyOrderById(companyId);
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            IntStream.range(0, cars.size())
                    .forEachOrdered(i -> System.out.printf("%d. %s%n", i + 1, cars.get(i).name()));
        }
    }

    private void addCar(int companyId) {
        System.out.println("\nEnter the car name:");
        carDao.add(scanner.nextLine(), companyId);
        System.out.println("The car was added!");
    }
}
