package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @Test(enabled = true)
    public void testCase01() throws InterruptedException {

        System.out.println("Beginning of Test Case 01");

        double starRating = 4.0;
        driver.get("http://www.flipkart.com/");
        Wrappers.enterTextWrapper(driver, By.xpath("//input[@type='text']"), "Washing Machine");
        Thread.sleep(3000);
        Wrappers.clickonelementWrapper(driver, By.xpath("//div[text()='Popularity']"));
        Thread.sleep(3000);
        Boolean status = Wrappers.SearchStarRatingAndPrintCount(driver,
                By.xpath("//div[@class='_5OesEi']/span/div"), starRating);
        Assert.assertTrue(status);
        System.out.println("Ending of Test Case 01");
    }

    @Test(enabled = true)
    public void testCase02() throws InterruptedException {

        System.out.println("Beginning of Test Case 02");
        int discount = 17;

        driver.get("http://www.flipkart.com/");
        Wrappers.enterTextWrapper(driver, By.xpath("//input[@type='text']"), "iPhone");
        Thread.sleep(8000);
        Boolean status = Wrappers.PrintTitleAndDiscountIphone(driver, By.xpath("//div[contains(@class,'yKfJKb')]"),
                discount);
        Assert.assertTrue(status);

        System.out.println("Ending of Test Case 02");
    }

    @Test(enabled = true)
    public void testCase03() throws InterruptedException {

        System.out.println("Beginning of Test Case 03");
        driver.get("http://www.flipkart.com/");

        Wrappers.enterTextWrapper(driver, By.xpath("//input[@type='text']"), "Coffee Mug");
        Thread.sleep(3000);
        Wrappers.clickonelementWrapper(driver, By.xpath("(//label[@class='tJjCVx _3DvUAf']/div)[1]"));
        Thread.sleep(3000);
        Boolean status = Wrappers.PrintTitleandImageUrlOfCoffeeMug(driver,
                By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
        Assert.assertTrue(status);

        System.out.println("Ending of Test Case 03");
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        // driver.close();
        // driver.quit();

    }
}