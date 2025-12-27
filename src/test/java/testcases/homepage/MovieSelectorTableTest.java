package testcases.homepage;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PurchasePage;
import pages.components.HomeMovieSelectorTable;
import pages.components.MainHomeSection;
import reports.ExtentReportManager;

public class MovieSelectorTableTest extends BaseTest {


@Test
public void TC01_Verify_MovieSelectorTable_Functionality() {
    HomePage homePage = new HomePage();
    HomeMovieSelectorTable movieSelectorTable = new HomeMovieSelectorTable();
    MainHomeSection homeSection = new MainHomeSection();
    PurchasePage purchasePage = new PurchasePage();

    String brand = "cgv";
    String location = "CGV - Golden Plaza";
    String movieTitle = "Fast And Furious 9";

    // Scroll to movie selector table and verify it's visible
    ExtentReportManager.info("Scroll to movie selector table and verify it's visible");
    LOG.info("Scroll to movie selector table and verify it's visible");
    homePage.getTopBarNavigation().clickCinemas();
    Assert.assertTrue(homeSection.isCinemasVisible(), "Movie selector table is not visible");

    //wait for table to load
    ExtentReportManager.info("Wait for movie selector table to load");
    LOG.info("Wait for movie selector table to load");
    movieSelectorTable.waitMovieSelectorTableLoaded();

    // Select brand
    ExtentReportManager.info("Select brand: " + brand);
    LOG.info("Select brand: " + brand);

    movieSelectorTable.selectBrand(brand);


    // Select location
    ExtentReportManager.info("Select location: " + location);
    LOG.info("Select location: " + location);
    movieSelectorTable.selectLocation(location);


    //choose movie by title
    ExtentReportManager.info("Choose movie by title: " + movieTitle);
    LOG.info("Choose movie by title: " + movieTitle);
    movieSelectorTable.chooseMovie(movieTitle);

    // Verify movie is visible
    ExtentReportManager.info("Verify movie is visible: " + movieTitle);
    LOG.info("Verify movie is visible: " + movieTitle);
    Assert.assertTrue(movieSelectorTable.isMovieVisible(movieTitle), "Movie '" + movieTitle + "' is not visible after selection");

    //Select showtime and get ID
    ExtentReportManager.info("Select showtimes and get ID");
    LOG.info("Select showtimes and get ID");
    String expectedPurchasePageId = movieSelectorTable.clickFirstAvailableShowtime(movieTitle);


    ExtentReportManager.info("Validate redirect to booking page /purchase/"+expectedPurchasePageId);
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains("/purchase/"), "Not redirected to purchase page. Actual: " + currentUrl);
    Assert.assertTrue(currentUrl.contains("/purchase/" + expectedPurchasePageId),
            "Purchase ID mismatch. Expected: " + expectedPurchasePageId + " | Actual URL: " + currentUrl);

    ExtentReportManager.info("Validate movie title "+ movieTitle+" on purchase page matches selected movie");
    String purchaseMovieTitle = purchasePage.getSummaryMovie().trim();
    Assert.assertTrue(purchaseMovieTitle.contains(movieTitle),
            "Movie title mismatch. Expected contains: " + movieTitle + " | Actual: " + purchaseMovieTitle);
}


}


