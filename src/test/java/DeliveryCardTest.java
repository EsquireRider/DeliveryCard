import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DeliveryCardTest {

    public String generateDate(int days, String pettern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pettern));
    }

    @Test
    void shouldValidFields() {
        Selenide.open("http://localhost:9999/");
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Нижний Новгород");
        form.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue("20.11.2025");
        form.$("[name='name']").setValue("Тятяев Антон");
        form.$("[name='phone']").setValue("+79521112233");
        $$("[data-test-id='agreement']").filter(Condition.visible).find(Condition.text("Я соглашаюсь"))
                .click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована"))
                .should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldValidFieldsPlanDate() {
        String planningDate = generateDate(7, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Нижний Новгород");
        form.$("[placeholder='Дата встречи']").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);
        form.$("[name='name']").setValue("Тятяев Антон");
        form.$("[name='phone']").setValue("+79521112233");
        $$("[data-test-id='agreement']").filter(Condition.visible).find(Condition.text("Я соглашаюсь"))
                .click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована"))
                .should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldValidFieldsDropDownList() {
        Selenide.open("http://localhost:9999/");
        SelenideElement form = $("form");
        form.$("[placeholder='Город']").setValue("Ни");
        $(Selectors.withText("Нижний Новгород")).click();
        form.$("[placeholder='Дата встречи']").click();
        $("[data-step='1']").click();
        $("[data-day='1765141200000']").click();
        form.$("[name='name']").setValue("Тятяев Антон");
        form.$("[name='phone']").setValue("+79521112233");
        $$("[data-test-id='agreement']").filter(Condition.visible).find(Condition.text("Я соглашаюсь"))
                .click();
        $$("button").filter(Condition.visible).find(Condition.text("Забронировать")).click();
        $(Selectors.withText("Встреча успешно забронирована"))
                .should(Condition.visible, Duration.ofSeconds(15));
    }
}
