package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BookingHistorySection extends BasePage {

    private final By BySectionContainer = By.cssSelector("//main/div[contains(@class,'MuiGrid-container')");
    private final By ByBookingCards     = By.xpath("//main/div/div[contains(@class,' MuiGrid-grid-md-6')]");

    //field inside booking card
    private final By ByBookingDateTime  = By.cssSelector(".booking-datetime");
    private final By ByMovieTitle       = By.cssSelector(".movie-title");
    private final By ByCinema    = By.cssSelector(".cinema-branch");
    private final By ByLocation     = By.cssSelector(".booking-info");

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }
    public void waitForBookingHistorySectionVisible() {
        LOG.info("waitForBookingHistorySectionVisible");
        waitForVisibilityOfElementLocated(driver(), BySectionContainer);
    }
    public int getBookingCountOnCurrentPage() {
        waitForBookingHistorySectionVisible();
        List<WebElement> items = DriverFactory.getDriver().findElements(ByBookingCards);
        return items.size();
    }

    // check pagination visible when booking more than 6
    public boolean isPaginationVisible() {
        By pagination = By.id("#pagination");

        List<WebElement> paginations = driver().findElements(pagination);

        return !paginations.isEmpty() && paginations.get(0).isDisplayed();
    }











}
