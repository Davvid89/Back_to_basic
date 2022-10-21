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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitTraining {
    WebDriver driver;
    WebDriverWait wait;
    String errorMessageText;
    String expectedMessage;

    @BeforeEach
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://fakestore.testelka.pl/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void correctAlertInBasket() throws InterruptedException {
        driver.findElement(By.cssSelector("li.post-389 [data-product_id='389']")).click();
        WebElement basketButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.post-389 [title='Zobacz koszyk']")));
        basketButton.click();
        driver.findElement(By.id("coupon_code")).sendKeys("10procent");
        acceptCoupon();
        Thread.sleep(2000);
        errorMessageText = getCorrectMSG();
        expectedMessage = "Kupon został pomyślnie użyty.";
        Assertions.assertEquals(expectedMessage, errorMessageText, "Error message is not correct.");
    }

    @Test
    public void noCodeInField() throws InterruptedException {
        driver.findElement(By.cssSelector("li.post-389 [data-product_id='389']")).click();
        WebElement basketButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.post-389 [title='Zobacz koszyk']")));
        basketButton.click();
        acceptCoupon();
        Thread.sleep(2000);
        errorMessageText = getErrorMSG();
        expectedMessage = "Proszę wpisać kod kuponu.";
        Assertions.assertEquals(expectedMessage, errorMessageText, "Error message is not correct");
    }

    @Test
    public void deleteCoupons() throws InterruptedException {
        driver.findElement(By.cssSelector("li.post-389 [data-product_id='389']")).click();
        WebElement basketButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li.post-389 [title='Zobacz koszyk']")));
        basketButton.click();
        driver.findElement(By.id("coupon_code")).sendKeys("10procent");
        acceptCoupon();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("a.woocommerce-remove-coupon")).click();
        ;
        Thread.sleep(2000);
        errorMessageText = getCorrectMSG();
        expectedMessage = "Kupon został usunięty.";
        Assertions.assertEquals(expectedMessage, errorMessageText, "Error message is not correct.");
    }

    private void acceptCoupon() {
        driver.findElement(By.cssSelector("div.coupon [name='apply_coupon']")).click();
    }

    private String getCorrectMSG() {
        return driver.findElement(By.cssSelector("div.woocommerce-message")).getText();
    }

    private String getErrorMSG() {
        return driver.findElement(By.cssSelector("ul.woocommerce-error")).getText();
    }
}
