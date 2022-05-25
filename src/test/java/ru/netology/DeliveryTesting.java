package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryTesting {

    @Test
    void shouldSubmitForm () {
        open ("http://localhost:9999/");
        DataGenerator.UserInfo userInfo = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id=city] input").setValue(userInfo.getCity());
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(DataGenerator.generateDate(3));
        $("[data-test-id=name] input").setValue(userInfo.getName());
        $("[name=phone]").setValue(userInfo.getPhone());
        $(".checkbox__box").click();
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(3)), Duration.ofSeconds(5));
        $("[placeholder=\"Дата встречи\"]").sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(DataGenerator.generateDate(4));
        $(".button").click();
        $(".notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + DataGenerator.generateDate(4)), Duration.ofSeconds(5));
    }
}
