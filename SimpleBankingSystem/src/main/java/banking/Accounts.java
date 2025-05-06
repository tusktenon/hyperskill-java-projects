package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Accounts {

    private final Connection connection;

    Accounts(Connection connection) {
        this.connection = connection;
    }

    record NewCard(String number, String pin) {}

    NewCard addAccount() throws SQLException {
        String number;
        boolean taken;
        do {
            number = CardUtils.generateCardNumber();
            String sql = "SELECT id FROM card WHERE number = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, number);
                try (ResultSet existing = statement.executeQuery()) {
                    taken = existing.next();
                }
            }
        } while (taken);
        String pin = CardUtils.generatePin();

        String sql = "INSERT INTO card (number, pin) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
            statement.setString(2, pin);
            statement.executeUpdate();
        }

        return new NewCard(number, pin);
    }

    int lookup(String number) throws SQLException {
        String sql = "SELECT id FROM card WHERE number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? result.getInt("id") : -1;
            }
        }
    }

    int lookup(String number, String pin) throws SQLException {
        String sql = "SELECT id FROM card WHERE number = ? AND pin = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, number);
            statement.setString(2, pin);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? result.getInt("id") : -1;
            }
        }
    }

    int getBalance(int id) throws SQLException {
        String sql = "SELECT balance FROM card WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return result.getInt("balance");
            }
        }
    }

    void deposit(int id, int amount) throws SQLException {
        String sql = "UPDATE card SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, amount);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    void transfer(int sourceId, int targetId, int amount) throws SQLException {
        String sql = "UPDATE card SET balance = balance + ? WHERE id = ?";
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, -amount);
                statement.setInt(2, sourceId);
                statement.executeUpdate();

                statement.setInt(1, amount);
                statement.setInt(2, targetId);
                statement.executeUpdate();

                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("Unable to complete transfer. Transaction is being rolled back");
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    void closeAccount(int id) throws SQLException {
        String sql = "DELETE FROM card WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
