package Selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.Duration;

public class FirstTestMyAccount {

    WebDriver driver;
    String errorText = "Błąd: Nazwa użytkownika jest wymagana.";
    String errorNoPassword = "Błąd: Hasło jest puste.";
    String errorNoAccountName = "Błąd: Nazwa użytkownika jest wymagana.";
    String userName = "gefil20040";


    @BeforeEach
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://fakestore.testelka.pl/moje-konto/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void noDataOnLoginToMyAccount() {
        driver.findElement(By.name("login")).click();
        String alertText = driver.findElement(By.cssSelector("div.woocommerce [role='alert']")).getText();
        Assertions.assertEquals(errorText, alertText, "The text of the error is different from that expected: " + alertText);
    }

    @Test
    public void noDataAndSelectedCheckbox() {
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.name("login")).click();
        String alertText = driver.findElement(By.cssSelector("div.woocommerce [role='alert']")).getText();
        Assertions.assertEquals(errorText, alertText, "The text of the error is different from that expected: " + alertText);
    }

    @Test
    public void loginEnteredEmail() {
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.id("username")).sendKeys("gefil20040@ekbasia.com");
        driver.findElement(By.name("login")).click();
        String alertText = driver.findElement(By.cssSelector("div.woocommerce [role='alert']")).getText();
        Assertions.assertEquals(errorNoPassword, alertText, "The text of the error is different from that expected: " + alertText);
    }

    @Test
    public void loginEnteredPassword() {
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.id("password")).sendKeys("somePassword123");
        driver.findElement(By.name("login")).click();
        String alertText = driver.findElement(By.cssSelector("div.woocommerce [role='alert']")).getText();
        Assertions.assertEquals(errorNoAccountName, alertText, "The text of the error is different from that expected: " + alertText);
    }

    @Test
    public void correctAccountName() {
        driver.findElement(By.id("rememberme")).click();
        driver.findElement(By.id("username")).sendKeys("gefil20040@ekbasia.com");
        driver.findElement(By.id("password")).sendKeys("%keDcLf#fx!fxOcI6e3yJOqS3");
        driver.findElement(By.name("login")).click();
        String accountName = driver.findElement(By.cssSelector(".woocommerce-MyAccount-content p")).getText();
        String[] tableAccountName = accountName.split(" ");
        Assertions.assertEquals(tableAccountName[1], userName, "The account name is different than expected" + tableAccountName[1]);
    }
}
