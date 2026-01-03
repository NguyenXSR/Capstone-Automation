package testcases.homepage;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.components.HomeMovieListingsSection;
import reports.ExtentReportManager;

@Listeners(TestListener.class)
public class HomeMovieListingsTest extends BaseTest {
    private HomeMovieListingsSection movieListing;
    private HomePage homePage;
    private final String movieTitle = "Man of Steel";

    @BeforeMethod
    public void setUpPages() {
        homePage = new HomePage();
        movieListing = homePage.movieListings();
    }


    @Test
    public void TC01_Verify_MovieListing_Displays() {
        ExtentReportManager.info("Verify movie listing displays");
        LOG.info("Verify movie listing displays");

        homePage.getTopBarNavigation().clickShowtimes();
        // movieListing.scrollToMovieListing();
        Assert.assertTrue(movieListing.isMovieListingVisible(), "Movie listing section is not visible");
    }

    @Test
    public void TC02_Verify_Hover_Shows_ActionButtons() {
        ExtentReportManager.info("Hover shows action buttons");
        LOG.info("Hover shows action buttons");

        //movieListing.scrollToMovieListing();
        homePage.getTopBarNavigation().clickShowtimes();

        movieListing.hoverMovieCard(movieTitle);

        Assert.assertTrue(movieListing.areActionButtonsVisible(movieTitle),
                "Play / Buy Ticket Now buttons are not visible after hover");
    }

    @Test
    public void TC03_Verify_PlayButton_Opens_TrailerModal() {
        ExtentReportManager.info("Play button opens trailer modal");
        LOG.info("Play button opens trailer modal");

        movieListing.scrollToMovieListing();
        // homePage.getTopBarNavigation().clickShowtimes();

        movieListing.hoverMovieCard(movieTitle);
        movieListing.clickPlay(movieTitle);

        Assert.assertTrue(movieListing.isTrailerModalOpened(), "Trailer modal is not opened or video is missing");
    }

    @Test
    public void TC04_Verify_BuyButton_Redirects_To_Detail() {
        ExtentReportManager.info("Buy button redirects to detail");
        LOG.info("Buy button redirects to detail");

        movieListing.scrollToMovieListing();
        //  homePage.getTopBarNavigation().clickShowtimes();

        movieListing.hoverMovieCard(movieTitle);
        movieListing.clickBuyTicketNow(movieTitle);

        Assert.assertTrue(driver.getCurrentUrl().contains("/detail/"),
                "Not redirected to /detail URL");
    }
}






    //-----------------------------------------------






