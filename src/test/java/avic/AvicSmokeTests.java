package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicSmokeTests {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/ua/");

    }

    @Test(priority = 1)
    public void checkThatAmountInCartIncreases(){
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("Marshall Major");
        driver.findElement(xpath("//button[contains(@class,'reset search-btn')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'Major IV ')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        driver.findElement(xpath("//span[contains(@class,'js_plus')]")).click();
        driver.findElement(xpath("//div[@class='btns-cart-holder']//a[contains(@class,'btn--orange')]")).click();
        String increasedProductsAmountInCart =
                driver.findElement(xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]"))
                        .getText();
        assertEquals(increasedProductsAmountInCart, "2");
    }

    @Test(priority = 2)
    public void checkThatFilterReturnsCorrectResult(){
        //driver.findElement(xpath("//input[@id='input_search']")).sendKeys("iPhone 11");
        driver.findElement(xpath("//a[contains(@href, 'back-to-school')]/i[contains(@class, 'icon icon-sale')]")).click();
        driver.findElement(xpath("//div[contains(@class,'brand__top')]//a[contains(@href, 'iphone/actions')]")).click();
        driver.findElement(xpath("//label[@for='fltr-seriya-iphone-11']")).click();
        driver.findElement(xpath("//label[@for='fltr-nakopitel-64gb']")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elementList = driver.findElements(xpath("//div[@class='prod-cart__descr']"));
        for (WebElement webElement : elementList){
            assertTrue(webElement.getText().contains("iPhone 11 64GB"));
        }

        //driver.findElement(xpath("//span[@id='select2-sort-lm-container']")).click();
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //List<WebElement> elementList = driver.findElements(xpath("//div[@class='prod-cart__descr']"));
        //assertEquals(elementList.size(), 12);
    }

    @Test(priority = 3)
    public void canSetPriceBorders(){
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("playstation");
        driver.findElement(xpath("//button[contains(@class,'reset search-btn')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(xpath("//input[contains(@class,'form-control-max')]")).clear();
        driver.findElement(xpath("//input[contains(@class,'form-control-max')]")).sendKeys("15000");
        driver.findElement(xpath("//input[contains(@class,'form-control-max')]")).submit();

    }

    @AfterMethod
    public void tearDown(){
        driver.close();
    }
}
