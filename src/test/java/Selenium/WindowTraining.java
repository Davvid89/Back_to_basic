package Selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class WindowTraining {
    WebDriver driver;
    WebDriverWait wait;
    By demoStoreNoticeDismiss = By.cssSelector("a[class*='dismiss-link']");
    By pilatesGroup = By.cssSelector("a[href*='pilates']");
    By product = By.cssSelector("li.post-61");
    private Object ExpectedConditions;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.navigate().to("https://fakestore.testelka.pl");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(demoStoreNoticeDismiss).click();
        driver.findElement(pilatesGroup).click();
        driver.findElement(product).click();
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void removeWishListTest() {
        By addToWishlist = By.cssSelector("a.add_to_wishlist");
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(addToWishlist)).click();
        driver.findElement(addToWishlist).click();
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(addToWishlist));
        By wishlistLink = By.cssSelector("#menu-item-248");
        driver.findElement(wishlistLink).click();
        String parentWindow = driver.getWindowHandle();
        
        Set<String> windows = driver.getWindowHandles();
        windows.remove(parentWindow);
        String wishlistWindow = windows.iterator().next();
        driver.switchTo().window(wishlistWindow);
        By removeWishlist = By.cssSelector(".remove_from_wishlist");
        driver.findElement(removeWishlist).click();
        By emptyWishlist = By.cssSelector("td.wishlist-empty");

        Assertions.assertDoesNotThrow(() -> wait.until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(emptyWishlist)),
                "Wishlist is not empty.");
    }
}

