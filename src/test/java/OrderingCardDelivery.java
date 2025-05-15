import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OrderingCardDelivery {
    public String generateData(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter
                .ofPattern(pattern));
    }

    @Test
    public void orderCardDelivery() {
        String deliveryDate = generateData(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE))
                .should(Condition.visible, Duration.ofSeconds(15)).setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Иван Иванов");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[data-test-id=agreement]").click();
        $$(".button").find(Condition.text("Забронировать")).should(Condition.visible, Duration.ofSeconds(15)).click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на "+ deliveryDate));
    }
}
