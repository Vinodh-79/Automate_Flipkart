package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    // Parameters are driver and Dragging the locator and the input text from my
    // test case
    public static boolean enterTextWrapper(WebDriver driver, By Locator, String text) {

        System.out.println("Sending the Keys");
        Boolean success;

        try {
            // My wait statement instead of using Thread.sleep
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
            WebElement inputBox = driver.findElement(Locator);
            inputBox.clear();
            inputBox.sendKeys(text);
            inputBox.sendKeys(Keys.ENTER); // This is my Keyboard Action
            success = true;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception Occured !! " + e.getMessage());
            success = false;
        }
        return success;

    }

    public static boolean clickonelementWrapper(WebDriver driver, By Locator) {

        System.out.println("Click Action");
        Boolean success;
        try {
            // Wait Class for all the wrapper methods
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));
            // Define the WebELement
            WebElement clickableElement = driver.findElement(Locator);
            clickableElement.click();
            success = true;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception Occured !! " + e.getMessage());
            success = false;
        }
        return success;
    }

    public static Boolean SearchStarRatingAndPrintCount(WebDriver driver, By Locator, double starRating) {

        System.out.println("Searching for Star Rating AND Printing the Titles");
        int washingMachineCount = 0;
        Boolean success;

        try {

            List<WebElement> starRatingElements = driver.findElements(Locator);
            Thread.sleep(5000);
            for (WebElement starRatingElement : starRatingElements) {

                if (Double.parseDouble(starRatingElement.getText()) <= starRating) {
                    washingMachineCount++;
                }
            }
            System.out.println("Count of washing machine which has star rating less than or equal to " + starRating
                    + ": " + washingMachineCount);
            success = true;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception Occured !! ");
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static boolean PrintTitleAndDiscountIphone(WebDriver driver, By Locator, int discount) {
        boolean success = false;
        try {
            // Wait until the products are visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));

            List<WebElement> IphonesearchProductRows = driver.findElements(Locator);

            // Store iPhone titles and discounts
            HashMap<String, String> iPhoneTitleDiscountMap = new HashMap<>();

            for (WebElement IphonesearchProductIndv : IphonesearchProductRows) {
                // Get the discountpercent text

                List<WebElement> Percentdiscounts = IphonesearchProductIndv
                        .findElements(By.xpath(".//div[contains(@class,'UkUFwK')]/span"));

                if (Percentdiscounts.size() != 0) {

                    WebElement Percentdiscount = IphonesearchProductIndv
                            .findElement(By.xpath(".//div[contains(@class,'UkUFwK')]/span"));
                    String percentagediscount = Percentdiscount.getText();

                    // Thread.sleep(5000);

                    System.out.println("Discount: " + percentagediscount);

                    // Parse the discount value
                    int discountValue = Integer.parseInt(percentagediscount.replaceAll("[^\\d]", ""));
                    if (discountValue > discount) {
                        // Get the title of the iPhone
                        WebElement iphoneTitleElement = IphonesearchProductIndv
                                .findElement(By.xpath(".//div[@class='KzDlHZ']"));
                        String iphoneTitle = iphoneTitleElement.getText();
                        iPhoneTitleDiscountMap.put(percentagediscount, iphoneTitle);
                    }
                    Thread.sleep(2000);
                }
            }
            // Print out results if there are iPhones with a discount
            if (!iPhoneTitleDiscountMap.isEmpty()) {
                for (Map.Entry<String, String> entry : iPhoneTitleDiscountMap.entrySet()) {
                    System.out.println(
                            "iPhone discount percentage: " + entry.getKey() + " and its title: " + entry.getValue());
                }
            } else {
                System.out.println("No iPhones found with discount greater than " + discount + "%");
            }
            success = true;
        } catch (

        Exception e) {
            System.out.println("Exception Occurred!! ");
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public static Boolean PrintTitleandImageUrlOfCoffeeMug(WebDriver driver, By Locator) {
        Boolean success;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Locator));

            List<WebElement> userRevieWebElements = driver.findElements(Locator);
            Set<Integer> userReviewSet = new HashSet<>();

            for (WebElement userRevieWebElement : userRevieWebElements) {
                int userReview = Integer.parseInt(userRevieWebElement.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReview);
            }
            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList, Collections.reverseOrder());
            System.out.println(userReviewCountList);

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productdetailsMap = new LinkedHashMap<>();

            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "(" + numberFormat.format(userReviewCountList.get(i)) + ")";
                String productTitle = driver
                        .findElement(By.xpath(
                                "//div[@class='slAVV4']//span[contains(text() ,'" + formattedUserReviewCount
                                        + "')]/../..//a[@class='wjcEIp']"))
                        .getText();

                String productImgUrl = driver
                        .findElement(By.xpath(
                                "//div[@class='slAVV4']//span[contains(text() ,'" + formattedUserReviewCount
                                        + "')]/../..//img[@class='DByuf4']"))
                        .getAttribute("src");

                String highestReviewCountandProductTitle = String.valueOf(i + 1) + "highest review count : "
                        + formattedUserReviewCount + "Title: " + productTitle;
                productdetailsMap.put(highestReviewCountandProductTitle, productImgUrl);
            }

            for (Map.Entry<String, String> productDetails : productdetailsMap.entrySet()) {
                System.out.println(
                        "Title: " + productDetails.getKey() + " and Product Image URL : "
                                + productDetails.getValue());
            }
            success = true;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception Occured !!");
            e.printStackTrace();
            success = false;
        }
        return success;
    }
}
