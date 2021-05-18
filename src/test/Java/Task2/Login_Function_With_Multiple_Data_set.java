package Task2;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.util.concurrent.TimeUnit;

import static WebElementsConstants.WebElements.*;


public class Login_Function_With_Multiple_Data_set {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/users/ashu/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(BASEURL);
    }

    @Test
    public void reading_and_writing_login_results() throws IOException, InterruptedException {
        File file = new File("/Users/ashu/Documents/Java workspace/Cerebra_Test/src/test/Java/Task2/QA Assignment-V2 - Multiple Data sheet.xlsx");
        FileInputStream inputStream= new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet("Sheet1");

        int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

        for(int i = 1; i <= rowCount; i++) {
            WebElement email_input_field = driver.findElement(By.id(EMAIL_ELEMENT));
            WebElement password_input_field = driver.findElement(By.id(PASSWORD_ELEMENT));
            WebElement login_button = driver.findElement(By.xpath(LOGIN_BUTTON));
            email_input_field.clear();
            password_input_field.clear();
            email_input_field.sendKeys(sheet.getRow(i).getCell(0).getStringCellValue());
            password_input_field.sendKeys(sheet.getRow(i).getCell(1).getStringCellValue());
            login_button.click();


            XSSFCell cell = sheet.getRow(i).createCell(2);
            Boolean bool = driver.getTitle().equals(LOGIN_PAGE_TITLE);

            if(!bool) {
                cell.setCellValue("FAIL");
            } else {
                cell.setCellValue("PASS");
                FileOutputStream outputStream = new FileOutputStream(file);
                wb.write(outputStream);

                driver.findElement(By.xpath(USER_PROFILE_BUTTON)).click();
                Thread.sleep(5000);
                driver.findElement(By.xpath(LOGOUT_BUTTON)).click();

                driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
            }
        }

        wb.close();
    }

    @AfterTest
    public void cleanUp() {
        driver.quit();
    }

}
