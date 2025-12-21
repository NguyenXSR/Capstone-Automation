package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class HomeMovieSearch extends BasePage {

    private final By searchBarRoot = By.id("homeTool");
    private final By movieDropdown = By.cssSelector("select[name='film']");
    private final By movieOptions = By.cssSelector("select[name='film'] option");

    private final By locationDropdown = By.cssSelector("select[name='cinema']");
    private final By locationOptions = By.cssSelector("select[name='cinema'] option");

    private final By showtimeDropdown = By.cssSelector("select[name='date']");
    private final By showtimeOptions = By.cssSelector("select[name='date'] option");

    private final By buyTicketBtn = By.xpath("//span[normalize-space()='MUA VÉ NGAY']");

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }


//---------Check visibility-----

    public boolean isVisible() {
        return waitForVisibilityOfElementLocated(driver(), searchBarRoot).isDisplayed();
    }

    public boolean isMovieVisible() {
        return waitForVisibilityOfElementLocated(driver(), movieDropdown).isDisplayed();
    }

    public boolean isLocationVisible() {
        return waitForVisibilityOfElementLocated(driver(), locationDropdown).isDisplayed();
    }

    public boolean isShowtimeVisible() {
        return waitForVisibilityOfElementLocated(driver(), showtimeDropdown).isDisplayed();
    }

    //---------Check booking flow

    public void openMovieDropdown() {
        click(driver(), movieDropdown);
        waitForVisibilityOfElementLocated(driver(), movieOptions);
    }

    public List<String> getMovieOptionTexts() {
        openMovieDropdown();
        List<WebElement> options = driver().findElements(movieOptions);
        return options.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectMovie(String movieName) {
        openMovieDropdown();
        By option = By.xpath("//select[@name='film']/option[normalize-space()='" + movieName + "']");
        click(driver(), option);
        // chờ chọn movie location sẽ update >  chờ location enabled, hiện options
        waitForElementToBeClickable(driver(), locationDropdown);
    }

    public void openLocationDropdown() {
        click(driver(), locationDropdown);
        waitForVisibilityOfElementLocated(driver(), locationOptions);
    }

    public List<String> getLocationOptionTexts() {
        openLocationDropdown();
        return driver().findElements(locationOptions).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectLocation(String locationName) {
        openLocationDropdown();
        By option = By.xpath("//select[@name='cinema']/option[normalize-space()='" + locationName + "']");
        click(driver(), option);
        waitForElementToBeClickable(driver(), showtimeDropdown);
    }

    public void openShowtimeDropdown() {
        click(driver(), showtimeDropdown);
        waitForVisibilityOfElementLocated(driver(), showtimeOptions);
    }

    public List<String> getShowtimeOptionTexts() {
        openShowtimeDropdown();
        return driver().findElements(showtimeOptions).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void selectShowtime(String showtimeText) {
        openShowtimeDropdown();
        By option = By.xpath("//select[@name='date']/option[normalize-space()='" + showtimeText + "']");
        click(driver(), option);
        waitForElementToBeClickable(driver(), buyTicketBtn);
    }

    public void clickBuyTicketNow() {
        click(driver(), buyTicketBtn);
    }




}


