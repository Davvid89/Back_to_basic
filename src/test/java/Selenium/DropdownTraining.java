package Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class DropdownTraining {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://allegro.pl/");
        driver.manage().window().maximize();

    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void selectElement() throws InterruptedException {
        driver.findElement(By.cssSelector("div.myre_8v_x button[data-role='reject-rodo']")).click();
        Thread.sleep(2000);
        WebElement categoryOfProduct = driver.findElement(By.cssSelector("select[data-role='filters-dropdown-toggle']"));
        Select dropdownCategories = new Select(categoryOfProduct);
        // Kategoria wyszukiwana po nr indexu
        dropdownCategories.selectByIndex(10);
        // Kategoria wyszukiwana po nazwie
        dropdownCategories.selectByValue("/kategoria/moda");
        // Kategoria wyszukiwana po widocznym tekscie
        dropdownCategories.selectByVisibleText("Supermarket");
        Boolean multiplateChoice = dropdownCategories.isMultiple();
        dropdownCategories.deselectByIndex(10);
    }
}
