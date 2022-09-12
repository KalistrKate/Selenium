import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DebetCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void ChromeDriverOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void sendingCorrectForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79162888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void sendingCorrectFormDeficeSurname() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов-Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79162888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    void sendingFormInglishName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79162888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    void sendingFormWrongNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("89162888888");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__text")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}
