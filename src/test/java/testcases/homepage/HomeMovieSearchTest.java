package testcases.homepage;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.components.HomeMovieSearch;
import reports.ExtentReportManager;

@Listeners(TestListener.class)
public class HomeMovieSearchTest extends BaseTest {
    private HomePage homePage;
    private HomeMovieSearch movieSearch;


    @BeforeMethod
    public void setupPages() {
        homePage = new HomePage();
        movieSearch = homePage.getMovieSearch();
    }

    @Test
    public void TC_Verify_search_bar_visibility_on_homepage() {
        ExtentReportManager.info("Check all inputs of search bar are visible");
        LOG.info("Check all inputs of search bar are visible");

        Assert.assertTrue(movieSearch.isVisible(), "Search bar is not visible");
        Assert.assertTrue(movieSearch.isMovieVisible(), "Movie input is not visible");
        Assert.assertTrue(movieSearch.isLocationVisible(), "Location input is not visible");
        Assert.assertTrue(movieSearch.isShowtimeVisible(), "Showtime input is not visible");
    }

    @Test
    public void TC_Verify_movie_dropdown_lists_all_available_movies() {

        ExtentReportManager.info("Check movie dropdown");
        LOG.info("Check movie dropdown");
        movieSearch.openMovieDropdown();
        Assert.assertTrue(movieSearch.getMovieOptionTexts().size() > 0, "Movie dropdown has no items");
    }

    @Test
    public void TC_Location_updates_dynamically_based_on_movie() {
        ExtentReportManager.info("Check location update based on movie");
        LOG.info("Check location update based on movie");

        movieSearch.selectMovie("Man of Steel");
        Assert.assertTrue(movieSearch.getLocationOptionTexts().size() > 0,
                "Locations did not load after selecting movie");
    }

    @Test
    public void TC_Showtime_updates_based_on_movie_and_location() {

        ExtentReportManager.info("Check location update based on movie");
        LOG.info("Check location update based on movie");

        movieSearch.selectMovie("Man of Steel");
        movieSearch.selectLocation("MegaGS - Cao Thắng");
        Assert.assertTrue(movieSearch.getShowtimeOptionTexts().size() > 0,
                "Showtimes did not load after selecting movie + location");
    }

    @Test
    public void TC_Clicking_CTA_redirects_to_booking_flow() {

        ExtentReportManager.info("Check if booking flow success, will direct to purchase page");
        LOG.info("Check if booking flow success, will direct to purchase page");

        movieSearch.selectMovie("Man of Steel");
        movieSearch.selectLocation("MegaGS - Cao Thắng");
        movieSearch.selectShowtime("03/10/2021 ~ 08:22");

        movieSearch.clickBuyTicketNow();
        Assert.assertTrue(driver.getCurrentUrl().contains("/purchase"), "Not redirected to ticket booking page");



    }





}
