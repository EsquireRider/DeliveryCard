import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DeliveryCardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999/");
    }


    @Test
    void shouldValidFields() {
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Нижний Новгород");
        form.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue("20.11.2025");
        form.$("[name='name']").setValue("Тятяев Антон");
        form.$("[name='phone']").setValue("+79521112233");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']")
                .should(Condition.text("Встреча успешно забронирована на 20.11.2025"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldValidFieldsPlanDate() {
        String planningDate = generateDate(7, "dd.MM.yyyy");
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Нижний Новгород");
        form.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);
        form.$("[name='name']").setValue("Тятяев Антон");
        form.$("[name='phone']").setValue("+79521112233");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']")
                .should(Condition.text("Встреча успешно забронирована на 16.11.2025"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
