package banking;

import java.util.Random;

public class Account {

    private static final long BIN = 4000_00;
    private static final byte ACCOUNT_NUMBER_DIGITS = 9;
    private static final byte PIN_DIGITS = 4;
    private static final Random RNG = new Random();
    private static long nextAccountNumber = 0;

    private static final long CARD_NUMBER_BASE =
            BIN * Long.parseLong('1' + "0".repeat(ACCOUNT_NUMBER_DIGITS + 1));
    private static final String PIN_FORMAT_STRING = "%0" + PIN_DIGITS + 'd';
    private static final int PIN_BOUND = Integer.parseInt('1' + "0".repeat(PIN_DIGITS));

    private final long cardNumber;
    private final int pin;

    Account() {
        cardNumber = CARD_NUMBER_BASE + nextAccountNumber * 10 + checkDigit(nextAccountNumber);
        pin = RNG.nextInt(PIN_BOUND);
        nextAccountNumber++;
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

    private static long checkDigit(long accountNumber) {
        // In a future stage, this method will calculate the check digit
        // for the given account number using the Luhn algorithm
        return 0;
    }
}
