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
import java.util.List;

public class MethodsLearning {

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
        driver.navigate().to("https://fakestore.testelka.pl/metody-na-elementach");
        driver.manage().window().maximize();
        driver.findElement(By.cssSelector(".woocommerce-store-notice__dismiss-link")).click();
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void checkElementsStateTest() {
        WebElement mainPage = driver.findElement(By.cssSelector("input[name='main-page']"));
        WebElement hiddenButton = driver.findElement(By.cssSelector("a[name='sailing']"));
        List<WebElement> yellowButton = driver.findElements(By.cssSelector("[style='margin: 5px; background-color: #f5e965;']"));
        WebElement selectCheckbox = driver.findElement(By.cssSelector("input[name='selected-checkbox']"));
        WebElement notSelectCheckbox = driver.findElement(By.cssSelector("input[name='not-selected-checkbox']"));
        WebElement selectRadio = driver.findElement(By.cssSelector("input[name='selected-radio']"));
        WebElement notSelectRadio = driver.findElement(By.cssSelector("input[name='not-selected-radio']"));
        List<WebElement> elementsWithButtonClass = driver.findElements(By.cssSelector(".button"));

        Assertions.assertAll("Checking compliance with the guidelines",
                () -> Assertions.assertFalse(mainPage.isEnabled(), "Button 'Main page' is not disable"),
                () -> Assertions.assertFalse(hiddenButton.isDisplayed(), "Hidden button is  displayed."),
                () -> assertThatButtonsAreYellow(yellowButton),
                () -> Assertions.assertTrue(selectCheckbox.isSelected(), "Checkbox is not selected."),
                () -> Assertions.assertFalse(notSelectCheckbox.isSelected(), "Checkbox is selected."),
                () -> Assertions.assertTrue(selectRadio.isSelected(), "Radiobutton is not selected."),
                () -> Assertions.assertFalse(notSelectRadio.isSelected(), "Radiobutton is selected."),
                () -> assertElementsHaveCorrectTag(elementsWithButtonClass)
        );
    }

    public void assertThatButtonsAreYellow(List<WebElement> buttons) {
        for (WebElement button : buttons) {
            String color = button.getCssValue("background-color");
            Assertions.assertEquals("rgba(245, 233, 101, 1)", color, "Button color is not what expected.");
        }
    }

    public void assertElementsHaveCorrectTag(List<WebElement> elements) {
        for (WebElement element : elements) {
            Assertions.assertEquals("a", element.getTagName(), "Element's tag is not 'a'.");
        }
    }
}
