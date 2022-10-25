package Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;


public class ActionTraining {
    WebDriver driver;
    Actions actions;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        actions = new Actions(driver);
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void contextClickExample() {
        driver.navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        WebElement editOption = driver.findElement(By.cssSelector(".context-menu-icon-edit"));
        WebElement buttton = driver.findElement(By.cssSelector(".context-menu-one"));
        //actions.moveByOffset(461,195).contextClick().click(editOption).build().perform();
        actions.contextClick(buttton).click(editOption).build().perform();
    }

    @Test
    public void doubleClickExample() {
        driver.navigate().to("https://www.plus2net.com/javascript_tutorial/ondblclick-demo.php");
        actions.moveByOffset(330,173).doubleClick().build().perform();
        WebElement box = driver.findElement(By.cssSelector("#box"));
        actions.doubleClick(box).build().perform();
    }

    @Test
    public void clickExample() {
        driver.navigate().to("http://jqueryui.com/selectable/#default");
        actions.moveByOffset(488, 380).click().build().perform();
        driver.switchTo().frame(0);
        List<WebElement> listElements = driver.findElements(By.cssSelector("#selectable>li"));
        WebElement firstElement = listElements.get(0);
        actions.click(firstElement).build().perform();
    }

}

