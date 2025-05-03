package banking;

import java.util.*;

public class AccountsRegistry {

    List<Account> accounts = new ArrayList<>();
    Set<Long> accountNumbers = new HashSet<>();

    Account add() {
        long accountNumber;
        do {
            accountNumber = Account.generateAccountNumber();
        } while (!accountNumbers.add(accountNumber));
        Account account = new Account(Account.toCardNumber(accountNumber), Account.generatePin());
        accounts.add(account);
        return account;
    }

    Optional<Account> lookup(long cardNumber, int pin) {
        return accounts.stream()
                .filter(a -> a.getCardNumber() == cardNumber && a.getPin() == pin)
                .findAny();
    }

    Optional<Account> lookup(String cardNumber, String pin) {
        return lookup(Long.parseLong(cardNumber), Integer.parseInt(pin));
    }
}
