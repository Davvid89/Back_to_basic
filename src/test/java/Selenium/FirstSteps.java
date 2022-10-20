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

public class FirstSteps {
    WebDriver driver;

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
    }

    @AfterEach
    public void driverQuit() {
        driver.quit();
    }

    @Test
    public void firstSteps() {
        driver.findElement(By.id("menu-item-201")).click();
        WebElement username = driver.findElement(By.cssSelector("#username"));
        WebElement password = driver.findElement(By.cssSelector("#password"));
        WebElement registerEmail = driver.findElement(By.cssSelector("#reg_email"));
        WebElement registerpassword = driver.findElement(By.cssSelector("#reg_password"));
        WebElement checkbox = driver.findElement(By.cssSelector("#rememberme"));
        WebElement login = driver.findElement(By.name("login"));
        WebElement lostPassword = driver.findElement(By.cssSelector("p.lost_password"));
        WebElement register = driver.findElement(By.name("register"));
        WebElement categoryProduct = driver.findElement(By.cssSelector("li.cat-item-18"));
    }

    @Test
    public void cssTraining() throws InterruptedException {
        driver.findElement(By.cssSelector(".storefront-recent-products [data-product_id='393']")).click();
        driver.findElement(By.cssSelector("[class='product-category product first'] [class='woocommerce-loop-category__title']")).click();
        driver.findElement(By.cssSelector("header.woocommerce-products-header+div select"));
        driver.findElement(By.cssSelector("li.product-type-simple [data-product_id='4116']")).click();
        String priceOnMainPage = driver.findElement(By.cssSelector("li.post-4116 bdi")).getText();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("li.post-4116 [class='added_to_cart wc-forward']")).click();
        String priceOnBasket = driver.findElement(By.cssSelector(".current-menu-item .amount")).getText();
        Assertions.assertEquals(priceOnMainPage, priceOnBasket);

    }

    @Test
    public void xpathTrainging() throws InterruptedException {
        driver.findElement(By.id("menu-item-198")).click();
        driver.findElement(By.xpath(".//*[contains(text(), 'Yoga i pilates ')]")).click();
        driver.findElement(By.xpath(".//a[@data-product_id='61']")).click();
        driver.findElement(By.xpath(".//a[@data-product_id='62']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(".//a[@title='Zobacz koszyk']")).click();
        driver.findElement(By.xpath(".//a[@class='checkout-button button alt wc-forward']")).click();
        String producut = driver.findElement(By.xpath(".//td[contains(text(), 'Kwitnącej')]")).getText();
        driver.findElement(By.xpath(".//*[@id='createaccount']")).click();
        System.out.println(producut);
        driver.findElement(By.xpath(".//input[starts-with(@id, 'billing_address_')]\n"));
    }

    @Test
    public void xpathTraingRelation() throws InterruptedException {
        driver.findElement(By.id("menu-item-198")).click();
        driver.findElement(By.xpath(".//*[contains(text(), 'Yoga i pilates ')]")).click();
        driver.findElement(By.xpath(".//a[@data-product_id='61']")).click();
        driver.findElement(By.xpath(".//a[@data-product_id='62']")).click();
        driver.findElement(By.xpath(".//a[@data-product_id='64']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(".//a[@title='Zobacz koszyk']")).click();
        String priceProduct = driver.findElement(By.xpath(".//*[@data-product_id='61']/../../*[@class='product-price']/*")).getText();
        System.out.println(priceProduct);
        String price = driver.findElement(By.xpath(".//*[@class='cart-subtotal']//bdi")).getText();
        System.out.println(price);

    }
}
