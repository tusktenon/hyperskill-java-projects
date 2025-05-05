package banking;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class DatabaseUtils {

    static Card add(Connection connection) throws SQLException {
        String number;
        boolean taken;
        do {
            number = Card.generateCardNumber();
            String query = "SELECT id FROM card WHERE number = '%s'".formatted(number);
            try (ResultSet existing = connection.createStatement().executeQuery(query)) {
                taken = existing.next();
            }
        } while (taken);
        String pin = Card.generatePin();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    INSERT INTO card (number, pin)
                    VALUES ('%s', '%s')
                    """.formatted(number, pin));
        }
        return new Card(number, pin, 0);
    }

    static Optional<Card> lookup(String number, String pin, Connection connection)
            throws SQLException {
        String query = """
                SELECT number, pin, balance
                FROM card
                WHERE number = '%s' AND pin = '%s'
                """.formatted(number, pin);
        try (ResultSet result = connection.createStatement().executeQuery(query)) {
            return result.next()
                    ? Optional.of(new Card(number, pin, result.getInt("balance")))
                    : Optional.empty();
        }
    }
}
