package Selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DropdownListTraining {

    WebDriver driver;
    WebDriverWait wait;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://fakestore.testelka.pl/");
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void OrderAscTest() {
        driver.findElement(By.cssSelector("img[alt=\"Windsurfing\"]")).click();
        WebElement firstOrderBy = driver.findElements(By.cssSelector("select[name='orderby']")).get(0);
        Select dropDownOrder = new Select(firstOrderBy);
        dropDownOrder.selectByIndex(4);
        WebElement firstPriceAfterOrder = driver.findElements(By.cssSelector("span.price")).get(0);
        Assertions.assertEquals("2 900,00 zł", firstPriceAfterOrder.getText(), "Te price of first element is not lowest");
    }

    @Test
    public void OrderDescTest(){
        driver.findElement(By.cssSelector("img[alt=\"Windsurfing\"]")).click();
        WebElement firstOrderBy = driver.findElements(By.cssSelector("select[name='orderby']")).get(0);
        Select dropDownOrder = new Select(firstOrderBy);
        dropDownOrder.selectByIndex(5);
        WebElement firstPriceAfterOrder = driver.findElements(By.cssSelector("span.price")).get(0);
        Assertions.assertEquals("5 399,00 zł", firstPriceAfterOrder.getText(), "Te price of first element is not highest");
    }
}