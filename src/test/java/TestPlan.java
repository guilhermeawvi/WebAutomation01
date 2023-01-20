import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class TestPlan {
    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(testName = "Search 'automatización' in google, open wikipedia and confirm first automated process in history (year 270)")
    public void testCase1() throws InterruptedException {

        driver.get(Utils.BASE_URL);
        //Create object of webPages Class
        WebPages webPages= new WebPages(driver);
        //Accept cookies
        webPages.acceptCookies();
        //Check if page is opened
        Assert.assertTrue("Google page not loaded",webPages.isGooglePageOpened());
        //send search words
        webPages.enterSearch();
        //Search and open wikipedia website
        webPages.readSearchResults();
        //Check if page is opened
        Assert.assertTrue("Wikipedia page not loaded",webPages.isWikiPageOpened());
        //Check if year "270" is found
        Assert.assertTrue("Failed to find year of '270'",webPages.checkYear());
        //Scrow to element before screenshot
        webPages.scrollView();
        Thread.sleep(500);
        webPages.takeScreenShot("screenShot1");
    }
    @AfterMethod
    public void cleanUp(ITestResult result){
        WebPages webPages= new WebPages(driver);
        if(result.FAILURE==result.getStatus()){
            webPages.takeScreenShot(result.getName());
        }
        driver.manage().deleteAllCookies();
        driver.close();
    }
}
