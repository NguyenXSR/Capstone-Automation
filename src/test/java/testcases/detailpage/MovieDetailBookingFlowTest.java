package testcases.detailpage;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.MovieDetailPage;
import reports.ExtentReportManager;


@Listeners(TestListener.class)
public class MovieDetailBookingFlowTest extends BaseTest {

    private MovieDetailPage movieDetailPage;

    @BeforeMethod
    public void setUpPages() {
        movieDetailPage = new MovieDetailPage();
    }


@Test(description = "Verify booking flow from movie detail page to purchase page")
public void TC_MD_01_Verify_Booking_Flow_From_Detail_To_Purchase() {

    String movieId = "9390";

    //step1: Navigate to movie detail page
    ExtentReportManager.info("Navigate to detail page");
    LOG.info("Navigate to detail page");
    movieDetailPage.openDetailPage(movieId);

    ExtentReportManager.info("Wait for movie details to load");
    movieDetailPage.waitMovieDetailsLoaded();

    //step2: Click "Mua Vé " button
    ExtentReportManager.info("Step 2: Click 'Mua Vé' button");
    LOG.info("Step 2: Click 'Mua Vé' button");
    movieDetailPage.clickBuyTicketButton();

    //step3: Verify showtime section is visible
    ExtentReportManager.info("Step 3: Verify showtime section is visible");
    LOG.info("Step 3: Verify showtime section is visible");
    Assert.assertTrue(movieDetailPage.isShowtimeSectionVisible(),
            "Showtime section is not visible after clicking 'Mua Vé' button");


    //step4: Select cinema brand
    ExtentReportManager.info("Step 4: Select cinema brand");
    LOG.info("Step 4: Select cinema brand ");
    movieDetailPage.selectBrand("cgv");

    //step5: Select showtime and verify redirection to purchase page
    ExtentReportManager.info("Step 5: Click first available showtime and verify redirection to purchase page");
    LOG.info("Step 5: Click first available showtime");
    movieDetailPage.clickFirstAvailableShowtime();
    Assert.assertTrue(driver.getCurrentUrl().contains("/purchase"), "Not redirected to ticket booking page");







}


}
