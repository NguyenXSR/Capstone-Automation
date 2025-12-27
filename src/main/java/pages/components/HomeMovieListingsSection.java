package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class HomeMovieListingsSection extends BasePage {

    private final By movieListingSection = By.id("lichChieu");
    // check ít nhất 1 card hiển thị
    private final By movieCardAny = By.xpath("//div[@id='lichChieu']//div[contains(@class,'CarouselItem')]//a[not(ancestor::a)]");

    private final By trailerModal = By.cssSelector(".modal-video");
    private final By trailerVideo = By.cssSelector(".modal-video-movie-wrap iframe");




    private WebDriver driver() {
        return DriverFactory.getDriver();
    }


    public boolean isMovieListingVisible() {
        LOG.info("isMovieListingVisible");

        return waitForVisibilityOfElementLocated(driver(), movieListingSection).isDisplayed();
    }

    public void scrollToMovieListing() {
        LOG.info("scrollToMovieListing");
        WebElement firstCard = waitForVisibilityOfElementLocated(driver(), movieCardAny);
        //  WebElement firstCard = waitForVisibilityOfElementLocated(driver(), movieListingSection);
        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", firstCard
        );
    }

    //  dynamic locator theo movie title

    private By byMovieCard(String movieTitle) {
        return By.xpath(
                "//*[contains(normalize-space(.),'" + movieTitle + "')]" +
                        "/ancestor::a[starts-with(@href,'/detail/')][1]"
        );
    }

    private By byPlayButton(String movieTitle) {
        return By.xpath(
                "//div[contains(normalize-space(.),'" + movieTitle + "')]" +
                        "/ancestor::a[1]" +
                        "//button[contains(@class,'MuiFab-root')]"
        );
    }

    private By byBuyTicketLink(String movieTitle) {

        return By.xpath(
                "//div[contains(normalize-space(.),'" + movieTitle + "')]" +
                        "/ancestor::a[1]" +
                        "//a[starts-with(@href,'/detail/')]"
        );

    }

    public void hoverMovieCard(String movieTitle) {
        LOG.info("hoverMovieCard: " + movieTitle);
        WebElement card = waitForVisibilityOfElementLocated(driver(), byMovieCard(movieTitle));
        new Actions(driver()).moveToElement(card).perform();
    }

    public boolean areActionButtonsVisible(String movieTitle) {
        LOG.info("areActionButtonsVisible: " + movieTitle);
        // button chỉ xuất hiện sau hover > nên gọi hover trước trong test
        WebElement play = waitForVisibilityOfElementLocated(driver(), byPlayButton(movieTitle));
        WebElement buy = waitForVisibilityOfElementLocated(driver(), byBuyTicketLink(movieTitle));
        return play.isDisplayed() && buy.isDisplayed();
    }

    //click nút play trailer
    public void clickPlay(String movieTitle) {
        LOG.info("clickPlay: " + movieTitle);
        click(driver(), byPlayButton(movieTitle));
    }

    public boolean isTrailerModalOpened() {
        LOG.info("isTrailerModalOpened");
        // chờ modal + video hiện
        waitForVisibilityOfElementLocated(driver(), trailerModal);
        return waitForVisibilityOfElementLocated(driver(), trailerVideo).isDisplayed();
    }

    public void clickBuyTicketNow(String movieTitle) {
        LOG.info("clickBuyTicketNow: " + movieTitle);
        click(driver(), byBuyTicketLink(movieTitle));
    }

}
