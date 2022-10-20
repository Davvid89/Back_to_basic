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

import java.time.Duration;

public class FrameTraining {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://fakestore.testelka.pl/cwiczenia-z-ramek/");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void mainPageButtonDisabledTest() {
        driver.switchTo().frame("main-frame");
        WebElement mainPageButton = driver.findElement(By.cssSelector("input[name='main-page']"));
        Assertions.assertFalse(mainPageButton.isEnabled(), "Main Page button is not disabled.");
    }

    @Test
    public void imageLinkTest() {
        driver.switchTo().frame("main-frame")
                .switchTo().frame("image");
        WebElement mainPageLink = driver.findElement(By.xpath(".//img[@alt='Wakacje']/.."));
        Assertions.assertEquals("https://fakestore.testelka.pl/", mainPageLink.getAttribute("href"),
                "There is no link to the main page on the image.");
    }

    @Test
    public void mainPageButtonEnabledTest() {
        driver.switchTo().frame("main-frame")
                .switchTo().frame("image")
                .switchTo().frame(0);
        WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
        Assertions.assertTrue(mainPageButton.isEnabled(), "Main Page button is not enabled.");
    }

    @Test
    public void logoDisplayedTest() {
        driver.switchTo().frame("main-frame")
                .switchTo().frame("image")
                .switchTo().frame(0);
        WebElement mainPageButton = driver.findElement(By.cssSelector("a.button"));
        mainPageButton.click();
        driver.switchTo().parentFrame()
                .switchTo().parentFrame();
        WebElement climbingButton = driver.findElement(By.cssSelector("a[name='climbing']"));
        climbingButton.click();

    }
}
