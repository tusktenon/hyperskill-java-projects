package carsharing.presentation;

import carsharing.business.DataService;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class AbstractMenu {

    final Scanner scanner;
    final DataService service;

    public AbstractMenu(Scanner scanner, DataService service) {
        this.scanner = scanner;
        this.service = service;
    }

    abstract void run();

    <E> void displayList(List<E> list, String emptyMessage, String header,
                         Function<E, String> displayFunction) {
        if (list.isEmpty()) {
            System.out.printf("%n%s%n", emptyMessage);
        } else {
            System.out.printf("%n%s%n", header);
            IntStream.range(0, list.size()).forEachOrdered(
                    i -> System.out.printf("%d. %s%n", i + 1, displayFunction.apply(list.get(i))));
        }
    }

    <E> void displayAndSelectFromList(List<E> list, String emptyMessage, String header,
                                      Function<E, String> displayFunction,
                                      Consumer<E> onSelection) {
        while (true) {
            displayList(list, emptyMessage, header, displayFunction);
            if (list.isEmpty()) return;
            System.out.println("0. Back");
            try {
                int selection = Integer.parseInt(scanner.nextLine());
                if (selection == 0) {
                    return;
                } else if (1 <= selection && selection <= list.size()) {
                    onSelection.accept(list.get(selection - 1));
                    return;
                } else {
                    System.out.println("Invalid selection");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection");
            }
        }
    }
}
