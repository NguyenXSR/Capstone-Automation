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
    private HomeMovieSearch movieSearch;


    @BeforeMethod
    public void setupPages() {
        HomePage homePage = new HomePage();
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

//    @Test
//    public void TC_Verify_movie_dropdown_lists_all_available_movies() {
//
//        ExtentReportManager.info("Check movie dropdown");
//        LOG.info("Check movie dropdown");
//        movieSearch.openMovieDropdown();
//        Assert.assertTrue(movieSearch.getMovieOptionTexts().size() > 0, "Movie dropdown has no items");
//    }
//
//    @Test
//    public void TC_Location_updates_dynamically_based_on_movie() {
//        ExtentReportManager.info("Check location update based on movie");
//        LOG.info("Check location update based on movie");
//
//        movieSearch.selectMovie("Man of Steel");
//        Assert.assertTrue(movieSearch.getLocationOptionTexts().size() > 0,
//                "Locations did not load after selecting movie");
//    }
//
//    @Test
//    public void TC_Showtime_updates_based_on_movie_and_location() {
//
//        ExtentReportManager.info("Check location update based on movie");
//        LOG.info("Check location update based on movie");
//
//        movieSearch.selectMovie("Man of Steel");
//        movieSearch.selectLocation("MegaGS - Cao Thắng");
//        Assert.assertTrue(movieSearch.getShowtimeOptionTexts().size() > 0,
//                "Showtimes did not load after selecting movie + location");
//    }

    @Test
    public void TC_Clicking_CTA_redirects_to_booking_flow() {

        ExtentReportManager.info("Check if booking flow success, will direct to purchase page");
        LOG.info("Check if booking flow success, will direct to purchase page");

        ExtentReportManager.info("Step 1: select 'Phim' ");
        LOG.info("Step 1: select movie");
        movieSearch.selectMovie("Man of Steel");

        ExtentReportManager.info("Step 2: select 'Rạp'");
        LOG.info("Step 2: select location");
        movieSearch.selectLocation("MegaGS - Cao Thắng");

        ExtentReportManager.info("Step 3: select 'Giờ chiếu'");
        LOG.info("SStep 3: select showtime");
        movieSearch.selectShowtime("03/10/2021 ~ 08:22");

        ExtentReportManager.info("Step 4: click 'Mua vé' button");
        LOG.info("SStep 3: select showtime");
        movieSearch.clickBuyTicketNow();
        Assert.assertTrue(driver.getCurrentUrl().contains("/purchase"), "Not redirected to ticket booking page");



    }





}
