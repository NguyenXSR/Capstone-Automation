package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomeMovieSelectorTable extends BasePage {



    // brand logos
    private final By cinemaBrandItems = By.cssSelector(".MuiContainer-maxWidthMd > .MuiTabs-vertical button");
    private By brandByName(String brandName) {
        return By.xpath("//div[contains(@class,'MuiContainer-maxWidthMd')]//img[@alt='"+brandName+"']");
    }

    //locations after choosing logo
    private final By LocationSelectorItems = By.xpath("//div[contains(@id,'vertical-tabpanel')]//button");
    private By locationByName(String locationName) {
        return By.xpath("//div[contains(@class,'MuiTabs-vertical')]//button//h4[normalize-space()='"+locationName+"']");
    }


    //private final By movieListItems = By.cssSelector(".movie-showtimes, .movie-list, [data-testid='movie-showtime-area']");

    // movie title by text
    private By movieTitleByName(String movieTitle) {
        return By.xpath("//h2[contains(@class,'MuiTypography-h2') and contains(.,'" + movieTitle + "')]");
    }
    private By movieCardByTitle(String movieTitle) {
        return By.xpath("//div[@class='App']//div[contains(@class,'MuiContainer-maxWidthMd')][2]//div[@role='tabpanel'][7]/div/div");
    }


    public boolean isLogoSelectorItemsVisible() {
        LOG.info("isLogoSelectorItemsVisible");

        return waitForVisibilityOfElementLocated(driver(), cinemaBrandItems).isDisplayed();
    }
    public boolean isCinemaSelectorItemsVisible() {
        LOG.info("isCinemaSelectorItemsVisible");

        return waitForVisibilityOfElementLocated(driver(), LocationSelectorItems).isDisplayed();
    }


    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    public void selectBrand(String brandName) {
        LOG.info("Select brand: " + brandName);
        click(driver(), brandByName(brandName));
    }
    public void selectLocation(String locationName) {
        LOG.info("Select location: " + locationName);
        click(driver(), locationByName(locationName));
    }

    //---------Choose brand and location-----
    public void chooseBrandAndLocation(String brandName, String locationName) {
        LOG.info("chooseBrandAndLocation: " + brandName + " - " + locationName);
        selectBrand(brandName);
        selectLocation(locationName);
    }

    //---------Wait for movie selector table loaded-----
    public void waitMovieSelectorTableLoaded() {
        LOG.info("waitMovieSelectorTableLoaded");
        waitForVisibilityOfElementLocated(driver(), cinemaBrandItems);
        waitForVisibilityOfElementLocated(driver(), LocationSelectorItems);
    }

    //---------Wait for movie list loaded-----
    public void waitMovieListLoaded() {
        LOG.info("waitMovieListLoaded");
        waitForVisibilityOfElementLocated(driver(), movieTitleByName("")); // wait for any movie title to be visible
    }

    //---------Check movie visibility-----
    public boolean isMovieVisible(String movieTitle) {
        LOG.info("isMovieVisible: " + movieTitle);
        return waitForVisibilityOfElementLocated(driver(), movieTitleByName(movieTitle)).isDisplayed();
    }



    //---------Choose movie by title-----
    public void chooseMovie(String movieTitle) {
        LOG.info("chooseMovie: " + movieTitle);
//        waitForVisibilityOfElementLocated(driver(), movieTitleByName(movieTitle));
//        JavascriptExecutor js = (JavascriptExecutor) driver();
//        WebElement el = driver().findElement(By.id(movieTitle));
//        js.executeScript("arguments[0].scrollIntoView(true);", el);
        WebElement titleEl = waitForVisibilityOfElementLocated(driver(), movieTitleByName(movieTitle));
        WebElement card = waitForVisibilityOfElementLocated(driver(), movieCardByTitle(movieTitle));

        // Scroll the card into view
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView({block:'center'});", card);


    }



    //---------Click first available showtime for a given movie title-----

       public String clickFirstAvailableShowtime(String movieTitle) {
        LOG.info("clickFirstAvailableShowtime");

        //identify movie container by movie title
        WebElement movieCard = waitForVisibilityOfElementLocated(driver(), movieCardByTitle(movieTitle));

        //locate showtime buttons within that movie container
        List <WebElement> showtimeButtons = movieCard.findElements
                (By.xpath("(//div[@role='tabpanel'])[7]/div/div[.//h2[contains(@class,'MuiTypography-h2') and contains(.,'"+ movieTitle+"')]]//a"));

        if (showtimeButtons.isEmpty()) {
            throw new RuntimeException("No available showtime found for movie: " + movieTitle);
        }

        // get href before clicking
        String showtimeLink = showtimeButtons.get(0).getAttribute("href");

        String showTimeId = showtimeLink.substring(showtimeLink.lastIndexOf("/") + 1);

        // click the first available showtime
        showtimeButtons.get(0).click();
        return showTimeId;
    };




}
