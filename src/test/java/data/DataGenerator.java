package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    public DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationByCardInfo generateByCard(String locale) {
            Faker faker = new Faker(new Locale("ru"));
            return new RegistrationByCardInfo(
                    faker.address().cityName(),
                    faker.name().firstName() + " " + faker.name().lastName(),
                    faker.phoneNumber().phoneNumber());
        }

        public static String generateDate(int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
    }
}