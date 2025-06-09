package dataserver;

import java.util.List;

public class TransactionService {

    private static final List<Transaction> DATA_1 = List.of(
            new Transaction("1", "server-04", "02248", "5120", "2023-12-24"),
            new Transaction("18", "server-02", "01055", "8347", "2025-02-15"),
            new Transaction("47", "server-11", "02248", "1205", "2024-01-07"));

    private static final List<Transaction> DATA_2 = List.of(
            new Transaction("5", "server-10", "01055", "0984", "2025-01-04"),
            new Transaction("26", "server-07", "01055", "2846", "2022-10-16"),
            new Transaction("34", "server-02", "02248", "4089", "2023-12-21"));

    private final List<Transaction> data;

    public TransactionService(int id) {
        data = id == 1 ? DATA_1 : DATA_2;
    }

    public List<Transaction> transactionsByAccount(String account) {
        return data.stream().filter(transaction -> transaction.account().equals(account)).toList();
    }
}
