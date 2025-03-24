package budget;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class FileOps {
    static final String DELIMITER = ",";

    static void load(Ledger ledger, String source) {
        try (Stream<String> balanceLine = Files.lines(Path.of(source)).limit(1);
             Stream<String> purchaseLines = Files.lines(Path.of(source)).skip(1)) {
            balanceLine.forEach(line -> ledger.setBalance(Double.parseDouble(line)));
            purchaseLines.forEach(line -> {
                String[] fields = line.split(DELIMITER);
                ledger.addPurchaseWithoutBalanceAdjustment(
                        Category.valueOf(fields[0]), fields[1], Double.parseDouble(fields[2]));
            });
        } catch (IOException e) {
            System.out.println("Error opening purchases source file");
        }
    }

    static void save(Ledger ledger, String target) {
        try (PrintWriter writer = new PrintWriter(target)) {
            writer.printf("%.2f\n", ledger.balance());
            ledger.purchases().forEach(purchase ->
                    writer.printf(
                            "%s%s%s%s%.2f\n",
                            purchase.category(),
                            DELIMITER,
                            purchase.description(),
                            DELIMITER,
                            purchase.amount())
            );
        } catch (FileNotFoundException e) {
            System.out.println("Error writing purchases to target file");
        }
    }
}
