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
    private final String movieName = "Man of Steel";
    private final String locationName = "MegaGS - Cao Thắng";
    private final String showtime = "03/10/2021 ~ 08:22";


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

    @Test
    public void TC_Verify_movie_dropdown_lists_movies() {

        ExtentReportManager.info("Check movie dropdown");
        LOG.info("Check movie dropdown");
        movieSearch.openMovieDropdown();
        Assert.assertTrue(movieSearch.getMovieOptionTexts().size() > 0, "Movie dropdown has no items");
    }

    @Test
    public void TC_Location_loads_successfully_based_on_movie() {

        ExtentReportManager.info("Check location load successfully based on movie");
        LOG.info("Check location load successfully based on movie");

        movieSearch.selectMovie(movieName);
        Assert.assertFalse(movieSearch.getLocationOptionTexts().isEmpty(),
                "Locations did not load after selecting movie");
    }


    @Test
    public void TC_Showtime_loads_successfully_based_on_movie_and_location() {

        ExtentReportManager.info("Check location load successfully based on movie");
        LOG.info("Check location load successfully based on movie");

        movieSearch.selectMovie(movieName);
        movieSearch.selectLocation(locationName);
        Assert.assertFalse(movieSearch.getShowtimeOptionTexts().isEmpty(),
                "Showtimes did not load after selecting movie + location");
    }

    @Test (description = "Verify clicking CTA redirects to booking flow")
    public void TC_Clicking_CTA_redirects_to_booking_flow() {

        ExtentReportManager.info("Check if booking flow success, will direct to purchase page");
        LOG.info("Check if booking flow success, will direct to purchase page");

        ExtentReportManager.info("Step 1: select 'Phim' ");
        LOG.info("Step 1: select movie");
        movieSearch.selectMovie(movieName);

        ExtentReportManager.info("Step 2: select 'Rạp'");
        LOG.info("Step 2: select location");
        movieSearch.selectLocation(locationName);

        ExtentReportManager.info("Step 3: select 'Giờ chiếu'");
        LOG.info("SStep 3: select showtime");
        movieSearch.selectShowtime(showtime);

        ExtentReportManager.info("Step 4: click 'Mua vé' button");
        LOG.info("SStep 3: select showtime");
        movieSearch.clickBuyTicketNow();
        Assert.assertTrue(driver.getCurrentUrl().contains("/purchase"), "Not redirected to ticket booking page");
    }
}




