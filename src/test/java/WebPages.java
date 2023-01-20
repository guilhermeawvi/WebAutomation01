import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class WebPages extends PageObject {

    private final String SEARCH_WORD = "automatización";
    private final String YEAR = "270";
    private final String FILE_PATH = System.getProperty("user.dir")+"//Screenshot//";


    @FindBy(xpath ="//li[@id='footer-places-about']/a")
    private WebElement wikipediaText;
    @FindBy(id = "L2AGLb")
    private WebElement acceptCookies;
    @FindBy(className = "gLFyf")
    private WebElement searchbar;
    @FindBy(id = "SIvCob")
    private WebElement googleCheck;
    @FindBy(id = "res")
    private WebElement searchResultContainer;
    By searchResultHeader = By.tagName("a");
    @FindBy(xpath = "//*[@id='mw-content-text']/div[1]/p[28]")
    private WebElement textLocator;

    public WebPages(WebDriver driver) {
        super(driver);
    }

    //Accept cookies
    public void acceptCookies() {
        this.acceptCookies.click();
    }

    //Write search term and hit enter
    public void enterSearch() {
        this.searchbar.sendKeys(SEARCH_WORD);
        this.searchbar.sendKeys(Keys.ENTER);
    }

    //Read all hyperlinks and search for "wikipedia"
    public void readSearchResults() {
        List<WebElement> searchResults = this.searchResultContainer.findElements(this.searchResultHeader);
        int size = searchResults.size();
        for (int i = 0; i < size; i++) {
            if (searchResults.get(i).getAttribute("href").contains("wikipedia")) {
                searchResults.get(i).click();
                break;
            }
        }
    }

    //Check if year is present in page
    public boolean checkYear() {
        //return this.textLocator.getText().toString().contains(YEAR);
        return this.textLocator.getText().contains(YEAR);

    }
    //Chec kif google page is loaded
    public boolean isGooglePageOpened(){
        //Assertion
        return this.googleCheck.getText().contains("Google");
    }

    //check if wikipedia page is loaded
    public boolean isWikiPageOpened(){
        //Assertion
        return this.wikipediaText.getText().contains("Wikipedia");
    }

    //Scroll page to view year information
    public void scrollView(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.textLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.background='yellow';", this.textLocator);

    }

    //Take screenshot
    public void takeScreenShot(String fileName)   {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(FILE_PATH+fileName+".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot path: "+destFile);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}