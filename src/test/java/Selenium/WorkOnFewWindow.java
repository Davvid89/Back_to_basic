package Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WorkOnFewWindow {
    WebDriver driver;
    WebDriverWait wait;
    By cookieAccept = By.cssSelector("#onetrust-accept-btn-handler");

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://kfc.pl/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(cookieAccept).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieAccept));
    }

    @AfterEach
    public void driverQuit() {
        driver.close();
        driver.quit();
    }

    @Test
    public void windowHandlesTest() {
        driver.findElement(By.cssSelector("span.icon-youtube")).click();
        Set<String> windows = driver.getWindowHandles();
        String parentWindow = driver.getWindowHandle();
        ((Set<?>) windows).remove(parentWindow);
        String secondWindow = windows.iterator().next();
        driver.switchTo().window(secondWindow);
        String activeWindow = driver.getWindowHandle();
        driver.findElement(By.cssSelector("button[jsname='tWT92d'] span.VfPpkd-vQzf8d")).click();
        driver.findElement(By.cssSelector("yt-icon#logo-icon")).click();
        driver.switchTo().window(parentWindow);
    }
}