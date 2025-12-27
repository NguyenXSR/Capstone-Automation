package pages;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MovieDetailPage extends CommonPage {

    private final By movieTitle = By.cssSelector("h1.MuiTypography-root.MuiTypography-h1");

    private final By btnBuyTicket = By.xpath("//a[text()='Mua vé']");

    private final By showtimeSection = By.id("cinemaList");

    // brand item theo alt img
    private By brandByAlt(String brandName) {
        return By.xpath("//img[@class='MuiAvatar-img'][@alt='" + brandName + "']");
    }

    // Showtime clickable items in tabpanel
    private final By showtimeItems = By.xpath("//div[@role='tabpanel']/div/div/div[2]/div[1]");



    private WebDriver driver() {
        return DriverFactory.getDriver();
    }


    public void openDetailPage(String movieId) {
        driver().get("https://demo1.cybersoft.edu.vn/detail/" + movieId);
    }

    public void waitMovieDetailsLoaded() {
        // wait for movie title visible then page is loaded
        waitForVisibilityOfElementLocated(driver(), movieTitle);
    }


    public void clickBuyTicketButton() {
        click(driver(), btnBuyTicket);
    }

    public boolean isShowtimeSectionVisible() {
        return waitForVisibilityOfElementLocated(driver(), showtimeSection).isDisplayed();
    }

    public void selectBrand(String brandName) {
        click(driver(), brandByAlt(brandName));
    }




    public void clickFirstAvailableShowtime() {
        List<WebElement> items = DriverFactory.getDriver().findElements(showtimeItems);
        if (items.isEmpty()) {
            throw new RuntimeException("No available showtime found!");
        }
        items.get(0).click();
    }

}

