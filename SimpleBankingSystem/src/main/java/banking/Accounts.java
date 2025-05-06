package banking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

class Accounts {

    private final Connection connection;

    Accounts(Connection connection) {
        this.connection = connection;
    }

    record NewCard(String number, String pin) {}

    NewCard add() throws SQLException {
        String number;
        boolean taken;
        do {
            number = CardUtils.generateCardNumber();
            String query = "SELECT id FROM card WHERE number = '%s'".formatted(number);
            try (ResultSet existing = connection.createStatement().executeQuery(query)) {
                taken = existing.next();
            }
        } while (taken);
        String pin = CardUtils.generatePin();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO card (number, pin) VALUES ('%s', '%s')".formatted(number, pin));
        }
        return new NewCard(number, pin);
    }

    Optional<Integer> lookup(String number, String pin)
            throws SQLException {
        String query =
                "SELECT id FROM card WHERE number = '%s' AND pin = '%s'".formatted(number, pin);
        try (ResultSet result = connection.createStatement().executeQuery(query)) {
            return result.next() ? Optional.of(result.getInt("id")) : Optional.empty();
        }
    }

    int getBalance(int id)
            throws SQLException {
        String query =
                "SELECT balance FROM card WHERE id = %d".formatted(id);
        try (ResultSet result = connection.createStatement().executeQuery(query)) {
            result.next();
            return result.getInt("balance");
        }
    }
}
