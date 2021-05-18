package Task3;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static WebElementsConstants.WebElements.*;

public class Warehouse {
    WebDriver driver;
    JavascriptExecutor js;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/Users/ashu/chromedriver");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;

        driver.manage().window().maximize();
        driver.get(BASEURL);
    }

    @Test(priority = 1)
    public void login_test() {
        driver.findElement(By.id(EMAIL_ELEMENT)).sendKeys(EMAIL_INPUT);
        driver.findElement(By.id(PASSWORD_ELEMENT)).sendKeys(PASSWORD_INPUT);
        driver.findElement(By.xpath(LOGIN_BUTTON)).click();
        Assert.assertEquals(driver.getTitle(), LOGIN_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void validate_warehouse_name_input_field() {
        driver.findElement(By.xpath(INVENTORY_BUTTON)).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(MANAGE_WAREHOUSE_BUTTON)));
        element.click();
        driver.findElement(By.xpath(NEW_WAREHOUSE_BUTTON)).click();
        driver.findElement(By.xpath(ADDRESS_LINE1_ELEMENT)).sendKeys(ADDRESS_LINE1_INPUT);
        driver.findElement(By.xpath(ADDRESS_LINE2_ELEMENT)).sendKeys(ADDRESS_LINE2_INPUT);
        driver.findElement(By.xpath(CITY_ELEMENT)).sendKeys(CITY_INPUT);
        driver.findElement(By.xpath(POSTAL_CODE_ELEMENT)).sendKeys(POSTAL_CODE_INPUT);

        driver.findElement(By.xpath(CREATE_WAREHOUSE_BUTTON)).click();
        Assert.assertEquals(driver.findElement(By.xpath(ERROR_ELEMENT)).getText(),ERROR_VALUE);
    }

    @Test(priority = 3)
    public void validate_warehouse_created() throws InterruptedException {
        driver.findElement(By.xpath(NAME_INPUT_ELEMENT)).sendKeys(NAME_INPUT);
        js.executeScript("window.scrollBy(0,1000)");
        Thread.sleep(3000);
        driver.findElement(By.xpath(CREATE_WAREHOUSE_BUTTON2)).click();
        Thread.sleep(3000);
        String value = driver.findElement(By.xpath(SUCCESS_ELEMENT)).getText();
        Assert.assertEquals(value, SUCCESS_VALUE);
    }

    @AfterTest
    public void clear() {
        driver.quit();
    }
}
