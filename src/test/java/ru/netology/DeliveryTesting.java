package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryTesting {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

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
