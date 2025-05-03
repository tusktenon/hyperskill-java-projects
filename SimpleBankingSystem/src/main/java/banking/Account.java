package banking;

import java.util.Random;

public class Account {

    private static final long BIN = 4000_00;
    private static final byte ACCOUNT_NUMBER_DIGITS = 9;
    private static final byte PIN_DIGITS = 4;
    private static final Random RNG = new Random();

    private static final long ACCOUNT_NUMBER_BOUND =
            Long.parseLong('1' + "0".repeat(ACCOUNT_NUMBER_DIGITS));
    private static final int PIN_BOUND = Integer.parseInt('1' + "0".repeat(PIN_DIGITS));
    private static final String PIN_FORMAT_STRING = "%0" + PIN_DIGITS + 'd';

    private final long cardNumber;
    private final int pin;

    Account(long cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    long getCardNumber() {
        return cardNumber;
    }

    int getPin() {
        return pin;
    }

    String getPinString() {
        return PIN_FORMAT_STRING.formatted(pin);
    }

    int getBalance() {
        return 0;
    }

    static long generateAccountNumber() {
        return RNG.nextLong(ACCOUNT_NUMBER_BOUND);
    }

    static long toCardNumber(long accountNumber) {
        return BIN * ACCOUNT_NUMBER_BOUND * 10 + accountNumber * 10 + checkDigit(accountNumber);
    }

    static int generatePin() {
        return RNG.nextInt(PIN_BOUND);
    }

    private static long checkDigit(long accountNumber) {
        // In a future stage, this method will calculate the check digit
        // for the given account number using the Luhn algorithm
        return 0;
    }
}
