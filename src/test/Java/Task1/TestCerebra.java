package Task1;

//Task 1:
//        Test Cases:
//        1. Verify if the Home Page(Cerebra Icon) Icon is working.
//        2. Verify if a user will be able to login with a valid username and valid password.
//        3. Verify if a user can not login with a valid username and an invalid password.
//        4. Verify the login page for both, when the field is blank and Submit button is clicked.
//        5. Verify the ‘Forget Password’ functionality.
//        6. Verify the message in the invalid login.
//        7. Verify if the data in password field is either visible or asterisk.
//        8. Verify if the ‘Enter’ key of the keyboard is working correctly on the login page.
//        9. Verify if the Social Media Icons are working when clicked.
//        10. Verify all links in the footer are woking when clicked.

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static WebElementsConstants.WebElements.*;

public class TestCerebra {
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

    /*
    1. Login to the website https://cerebratest.com/en/users/sign_in.
    2. Validate Cerebar icon is displayed.
     */
    @Test(priority = 1)
    public void Test_login_page_home_icon(){
        WebElement home_icon_top = driver.findElement(By.xpath(HOME_ICON_TOP_ELEMENT));
        WebElement home_icon_bottom = driver.findElement(By.xpath(HOME_ICON_BOTTOM_ELEMENT));
        Assert.assertTrue(home_icon_top.isDisplayed());
    }

    /*
    1. Login to the website https://cerebratest.com/en/users/sign_in.
    2. Validate correct user and password.
     */
    @Test(priority = 2)
    public void Test_user_login_with_a_valid_username_and_valid_password() throws InterruptedException {
        driver.findElement(By.id(EMAIL_ELEMENT)).sendKeys(EMAIL_INPUT);
        driver.findElement(By.id(PASSWORD_ELEMENT)).sendKeys(PASSWORD_INPUT);
        driver.findElement(By.xpath(LOGIN_BUTTON)).click();

        Assert.assertEquals(driver.getTitle(), LOGIN_PAGE_TITLE);

        driver.findElement(By.xpath(USER_PROFILE_BUTTON)).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(LOGOUT_BUTTON)).click();

        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
    }

    /*
    1. Login to the website https://cerebratest.com/en/users/sign_in.
    2. Validate correct user and invalid password.
     */
    @Test(priority = 3)
    public void Test_user_login_with_a_valid_username_and_invalid_password(){
        driver.findElement(By.id(EMAIL_ELEMENT)).sendKeys(EMAIL_INPUT);
        driver.findElement(By.id(PASSWORD_ELEMENT)).sendKeys(INVALID_PASSWORD_INPUT);
        driver.findElement(By.xpath(LOGIN_BUTTON)).click();
        WebElement InvalidUser = driver.findElement(By.xpath("/html/body/div[2]/div[1]"));
        Assert.assertEquals(InvalidUser.getText(), "Invalid Email or password.");
    }

    /*
    1. Login to the website https://cerebratest.com/en/users/sign_in.
    2. Validate empty user and empty password.
     */
    @Test(priority = 4)
    public void Test_a_user_login_with_a_empty_username_and_empty_password() throws InterruptedException {
        driver.findElement(By.id(EMAIL_ELEMENT)).sendKeys("");
        driver.findElement(By.id(PASSWORD_ELEMENT)).sendKeys("");
        driver.findElement(By.xpath(LOGIN_BUTTON)).click();
        WebElement InvalidUser = driver.findElement(By.xpath(INVALID_USER_MESSAGE_ELEMENT));
        Assert.assertEquals(InvalidUser.getText(), INVALID_USER_MESSAGE);
    }

    /*
    1. Login to the website https://cerebratest.com/en/users/sign_in.
    2. Validate Forget Password Link working
     */
    @Test(priority = 5)
    public void Test_forget_Password_link() {
        //js.executeScript("window.scrollBy(0,100)");
        driver.findElement(By.xpath(FORGET_PASSWORD_BUTTON)).click();
        WebElement ForgetPassword = driver.findElement(By.xpath(FORGET_PASSWORD_ELEMENT));
        Assert.assertEquals(ForgetPassword.getText(), FORGET_PASSWORD_ELEMENT_TEXT);
    }

    @AfterTest
    public void Close(){
        driver.quit();
    }
}
