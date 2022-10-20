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

public class AlertHandling {
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://jsfiddle.net/nm134se7/");
        driver.manage().window().maximize();
        driver.switchTo().frame("result");

    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void acceptConfirmWindowTest(){
        driver.findElement(By.cssSelector("button[onclick='confirmFunction()']")).click();
        driver.switchTo().alert().accept();
        String message = driver.findElement(By.cssSelector("p#demo")).getText();
        Assertions.assertEquals("Wybrana opcja to OK!", message, "Wrong message");
    }

    @Test
    public void dismissConfirmWindowTest(){
        driver.findElement(By.cssSelector("button[onclick='confirmFunction()']")).click();
        driver.switchTo().alert().dismiss();
        String message = driver.findElement(By.cssSelector("p#demo")).getText();
        Assertions.assertEquals("Wybrana opcja to Cancel!", message, "Message is not correct.");
    }

    @Test
    public void acceptPromptWindowTest(){
        driver.findElement(By.cssSelector("button[onclick='promptFunction()']")).click();
        driver.switchTo().alert().accept();
        String message = driver.findElement(By.cssSelector("p#prompt-demo")).getText();
        Assertions.assertEquals("Cześć Harry Potter! Jak leci?", message, "Message is not correct.");
    }

    @Test
    public void dismissPromptWindowTest(){
        driver.findElement(By.cssSelector("button[onclick='promptFunction()']")).click();
        driver.switchTo().alert().dismiss();
        String message = driver.findElement(By.cssSelector("p#prompt-demo")).getText();
        Assertions.assertEquals("Użytkownik anulował akcję.", message, "Message is not correct.");
    }

    @Test
    public void acceptWithTextWindowTest(){
        driver.findElement(By.cssSelector("button[onclick='promptFunction()']")).click();
        String textToSend = "Joe Black";
        driver.switchTo().alert().sendKeys(textToSend);
        driver.switchTo().alert().accept();
        String message = driver.findElement(By.cssSelector("p#prompt-demo")).getText();
        Assertions.assertEquals("Cześć " + textToSend + "! Jak leci?", message, "Message is not correct.");
    }
}
