package cinema;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.IntBinaryOperator;

@Configuration
public class CinemaConfig {

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private static final int STANDARD_SEAT_PRICE = 8;
    private static final int PREMIUM_SEAT_PRICE = 10;

    private static final IntBinaryOperator SEAT_PRICER =
            (row, col) -> row <= 4 ? PREMIUM_SEAT_PRICE : STANDARD_SEAT_PRICE;

    @Bean
    public SeatingService seatingService() {
        return new SeatingService(ROWS, COLUMNS, SEAT_PRICER);
    }

    @Bean
    public String managerPassword() {
        return "super_secret";
    }
}
