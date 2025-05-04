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
        long uncheckedCardNumber = BIN * ACCOUNT_NUMBER_BOUND + accountNumber;
        return 10 * uncheckedCardNumber + checkDigit(uncheckedCardNumber);
    }

    static int generatePin() {
        return RNG.nextInt(PIN_BOUND);
    }

    private static int checkDigit(long unchecked) {
        byte[] ascii = Long.toString(unchecked).getBytes();
        int uncheckedSum = 0;
        for (int i = 0; i < ascii.length; i++) {
            int digit = ascii[i] - '0';
            if (i % 2 == 0)
                digit = digit < 5 ? 2 * digit : 2 * digit - 9;
            uncheckedSum += digit;
        }
        int uncheckedSumLastDigit = uncheckedSum % 10;
        return uncheckedSumLastDigit == 0 ? 0 : 10 - uncheckedSumLastDigit;
    }
}
