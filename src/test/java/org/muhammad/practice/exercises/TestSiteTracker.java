package org.muhammad.practice.exercises;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test site tracker.
 * 
 * @author Muhammad
 *
 */
public class TestSiteTracker {
    private static WebDriver driver;
    private static final String BASE_URL = "https://developer.salesforce.com";

    @BeforeClass
    public static void classSetup() {
        System.out.println(System.getProperty("user.dir"));
        System.setProperty("webdriver.chrome.driver",
            System.getProperty("user.dir") + "\\src\\main\\java\\" + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        // login code will be here if credentials are provided
        driver.manage().window().maximize();
    }

    @Test
    public static void test() throws InterruptedException {
        boolean isSearchPageFound = isSearchPageFound();
        if (isSearchPageFound) {
            boolean isTestingApexSearchFound = isWritingTestPageFound();
            if (isTestingApexSearchFound) {
                boolean isTestingApexPageOpened = isTestingApexPageOpened();
                Assert.assertTrue(isTestingApexPageOpened);
            } else {
                Assert.fail("Testing Apex page was not opened");
            }
        } else {
            Assert.fail("Search was not found *Writing Tests*");
        }
    }

    private static boolean isSearchPageFound() throws InterruptedException {
        boolean isSearchPageFound = false;
        WebElement searchWebElement = driver.findElement(By.id("st-search-input"));
        searchWebElement.click();
        Thread.sleep(500);
        searchWebElement.sendKeys("Writing Test");
        searchWebElement.sendKeys(Keys.RETURN);
        driver.getCurrentUrl();
        if (driver.getTitle().equals("Search")) {
            isSearchPageFound = true;
        }
        return isSearchPageFound;
    }

    private static boolean isWritingTestPageFound() {
        boolean isWritingTestApexFound = false;
        List<WebElement> listOfSearch = driver.findElements(By.xpath("//*[@id='st-results-container']/div[1]/a"));
        for (WebElement eachSearch : listOfSearch) {
            if (eachSearch.getText().contains("Writing Tests | Apex")) {
                isWritingTestApexFound = true;
                eachSearch.click();
                driver.getCurrentUrl();
                System.out.println(driver.getTitle());
                break;
            }
        }
        return isWritingTestApexFound;
    }

    private static boolean isTestingApexPageOpened() throws InterruptedException {
        boolean isTestingApexPageOpened = false;
        driver.getCurrentUrl();
        List<WebElement> allLinks = driver.findElements(By.xpath("//*[@id='content-body-anchor']//a"));
        for (WebElement link : allLinks) {
            if (link.getText().equals("Testing Apex")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("scroll(0, 400)");

                driver.getCurrentUrl();
                System.out.println(driver.getTitle());
                isTestingApexPageOpened = true;
                break;
            }
        }
        return isTestingApexPageOpened;
    }

    @AfterClass
    public void teardown() {
        // log out code if already logged in with credentials
        driver.quit();
    }
}
