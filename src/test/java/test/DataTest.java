package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import data.DataGenerator;
import data.RegistrationByCardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DataTest {
    RegistrationByCardInfo user = DataGenerator.Registration.generateByCard("ru");
    String afterThreeDays = DataGenerator.Registration.generateDate(4);
    String inFiveDays = DataGenerator.Registration.generateDate(6);

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void submitCorrectForm() {
        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(afterThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + afterThreeDays));
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(inFiveDays);
        $$("button").find(exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + inFiveDays));
    }
    @Test
    void requestWithoutCity() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(afterThreeDays);
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[data-test-id='phone'] input").setValue(user.getNumber());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
