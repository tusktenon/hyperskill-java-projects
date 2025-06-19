package carsharing.presentation;

import carsharing.business.ManagementService;

import java.util.Scanner;

public class MainMenu implements Menu {

    private static final String MENU_TEXT = """
            
            1. Log in as a manager
            0. Exit
            """;

    private final Scanner scanner;
    private final ManagementService service;

    public MainMenu(Scanner scanner, ManagementService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(MENU_TEXT);
            switch (scanner.nextLine()) {
                case "1" -> new CompanyManagementMenu(scanner, service).run();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }
}
