package testcases.purchasepage;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PurchasePage;
import reports.ExtentReportManager;

public class SeatSelectionTest extends BaseTest {

    private PurchasePage purchasePage;

    // truyen showtimeID de chon lich chieu
    private final String showtimeID = "46016";

    //truyen seatNumber de chon ghe
    private final String REG1 = "20";
    private final String REG2 = "34";
    private final String VIP1 = "124";
    private LoginPage loginPage;

    @BeforeMethod
    public void setupPages() {
        purchasePage = new PurchasePage();
        loginPage = new LoginPage();

    }

    @Test
    public void TC01_Verify_Seat_Selection_Functionality() {
        // Navigate to the seat selection page for the specified showtime

        ExtentReportManager.info("Navigate to purchase page");
        LOG.info("Navigate to purchase page");
        purchasePage.navigateToPurchasePage(showtimeID);

        // Select multiple seats
        ExtentReportManager.info("Select multiple seats");
        LOG.info("Select multiple seats");
        purchasePage.selectSeat(REG1);
        purchasePage.selectSeat(REG2);
        purchasePage.selectSeat(VIP1);


        // Verify selected seats are highlighted
        ExtentReportManager.info("Confirm selected seats turn green");
        LOG.info("Confirm selected seats turn green");
        Assert.assertTrue(purchasePage.isSeatSelected(REG1), "Regular seat 1 is not selected.");
        Assert.assertTrue(purchasePage.isSeatSelected(REG2), "Regular seat 2 is not selected.");
        Assert.assertTrue(purchasePage.isSeatSelected(VIP1), "VIP seat is not selected.");

        //verify selected seats are not marked as booked
        ExtentReportManager.info("Confirm selected seats are not marked as booked");
        LOG.info("Confirm selected seats are not marked as booked");
        Assert.assertFalse(purchasePage.isSeatBooked(REG1), "Regular seat 1 is marked as booked.");
        Assert.assertFalse(purchasePage.isSeatBooked(REG2), "Regular seat 2 is marked as booked.");
        Assert.assertFalse(purchasePage.isSeatBooked(VIP1), "VIP seat is marked as booked.");

//        //Verify click behavior on already booked seat
          // can try using API to test this case
//        ExtentReportManager.info("Verify click behavior on already booked seat");
//        LOG.info("Verify click behavior on already booked seat");
//        String bookedSeat = "90"; //  seat 90 is booked in test data
//        // If seat is already booked, not clicking
//        if (purchasePage.isSeatBooked(bookedSeat)) {
//            Assert.assertFalse(purchasePage.isSeatSelected(bookedSeat), "Booked seat " + bookedSeat + " is selected despite being booked.");
//        } else {
//            purchasePage.selectSeat(bookedSeat);
//            Assert.assertFalse(purchasePage.isSeatSelected(bookedSeat), "Booked seat " + bookedSeat + " was selected after clicking.");
//        }


        // Verify summary section updates with selected seats
        ExtentReportManager.info("Validate summary seats updated");
        LOG.info("Validate summary seats updated");
        String seatTxt = purchasePage.getSummarySeatsTxt();
        Assert.assertTrue(seatTxt.contains(REG1), "Summary does not contain Regular seat" + REG1);
        Assert.assertTrue(seatTxt.contains(REG2), "Summary does not contain Regular seat" + REG2);
        Assert.assertTrue(seatTxt.contains(VIP1), "Summary does not contain VIP seat" + VIP1);


        // Verify total price calculation is correct
        ExtentReportManager.info("Verify total price calculation");
        LOG.info("Verify total price calculation");
        double expectedTotal = purchasePage.getSeatPrice(REG1) + purchasePage.getSeatPrice(REG2) + purchasePage.getSeatPrice(VIP1);
        Assert.assertEquals(expectedTotal, purchasePage.getTotalPrice(), "Total price calculation is incorrect.");


        // Unselect a seat and verify updates
        ExtentReportManager.info("Click again on a selected seat to unselect and validate update");
        LOG.info("Click again on a selected seat to unselect and validate update");
        purchasePage.selectSeat(REG2);
        Assert.assertFalse(purchasePage.isSeatSelected(REG2), "Regular seat 2 is still selected after unselecting.");

        String seatsAfter = purchasePage.getSummarySeatsTxt();
        Assert.assertFalse(seatsAfter.contains(REG2), "Summary still contains unselected Regular seat" + REG2);
        double expectedTotalAfter = purchasePage.getSeatPrice(REG1) + purchasePage.getSeatPrice(VIP1);
        Assert.assertEquals(expectedTotalAfter, purchasePage.getTotalPrice(), "Total price did not update correctly after unselecting a seat.");

        // Try to select more than 8 seats and verify error modal
        ExtentReportManager.info("Try to select more than 8 available seats, validate modal error");
        LOG.info("Try to select more than 8 available seats, validate modal error");
        String[] moreSeats = {"34", "21", "35", "36", "37", "33", "31"}; // choose 7 more available seats to exceed limit
        for (String seat : moreSeats) {
            purchasePage.selectSeat(seat);
        }
        // expect error modal to appear
        //This is supposed to be a bug
        Assert.assertTrue(purchasePage.isModalVisible(), "Error modal not shown when selecting >8 seats");
        purchasePage.clickModalConfirmButton();

    }


    @Test
    public void TC01_Verify_Booking_Requires_Login() {

        // Step 1 Navigate to the seat selection page for the specified showtime
        ExtentReportManager.info("Step1: Navigate to purchase page");
        LOG.info("Step1: Navigate to purchase page");
        purchasePage.navigateToPurchasePage(showtimeID);

        // Step 2: Verify seat map is visible

        ExtentReportManager.info("step2: Verify seat map is visible");
        LOG.info("step2: Verify seat map is visible");
        purchasePage.isSeatMapVisible();

        // Step 3 Select seats
        ExtentReportManager.info("Step3: Select seats for booking");
        LOG.info("Step3: Select seats for booking");
        purchasePage.selectSeat(REG1);
        purchasePage.selectSeat(VIP1);

        // Step 4 Click Buy Ticket button
        ExtentReportManager.info("Step4: Click Buy Ticket button");
        LOG.info("Step4: Click Buy Ticket button");
        purchasePage.clickBuyTicketButton();

        // Step 5 Verify login required modal
        ExtentReportManager.info("Step5: Verify login required modal");
        LOG.info("Step5: Verify login required modal");
        Assert.assertTrue(purchasePage.isModalVisible(), "Login required modal not shown.");
        String modalMsg = purchasePage.getModalMessage();
        Assert.assertTrue(modalMsg.contains("Bạn chưa đăng nhập"), "Modal message is not correct.");
        purchasePage.clickModalDenyLoginButton();

    }



    @Test
    public void TC02_Verify_Summary_And_Booking_Success() {

        //Step 0: Login first before booking
        ExtentReportManager.info("Step0: Login first before booking");
        LOG.info("Step0: Login first before booking");
        loginPage.getTopBarNavigation().navigateLoginPage();
        loginPage.enterAccount("a68cf217-d33b-4132-b180-864697ac8427"); //exist account
        loginPage.enterPassword("Test123456@");
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.getLoginMsg(), "Đăng nhập thành công", "Login message failed!");



        // Step 1 Navigate to the seat selection page for the specified showtime

        ExtentReportManager.info("Step1: Navigate to purchase page");
        LOG.info("Step1: Navigate to purchase page");
        purchasePage.navigateToPurchasePage(showtimeID);

        // Step 2: Verify seat map is visible

        ExtentReportManager.info("step2: Verify seat map is visible");
        LOG.info("step2: Verify seat map is visible");
        purchasePage.isSeatMapVisible();

        //Step 3: Verify summary section is visible
        ExtentReportManager.info("Step3: Verify ticket summary fields are displayed (movie/cinema/datetime)");
        LOG.info("Step3: Verify ticket summary fields are displayed (movie/cinema/datetime)");
        purchasePage.isSummaryVisible();
        Assert.assertTrue(purchasePage.getSummaryCinema().trim().length() > 0, "Cinema name is not displayed in summary.");
        Assert.assertTrue(purchasePage.getSummaryAddress().length() > 0, "Cinema address is not displayed in summary.");
        Assert.assertTrue(purchasePage.getSummaryScreenNumber().length() > 0, "Screen number is not displayed in summary.");
        Assert.assertTrue(purchasePage.getSummaryDateTime().length() > 0, "Show date/time is not displayed in summary.");
        Assert.assertTrue(purchasePage.getSummaryMovie().length() > 0, "Movie name is not displayed in summary.");


        // Step 4 Select seats
        ExtentReportManager.info("Step4: Select seats for booking");
        LOG.info("Step4: Select seats for booking");
        purchasePage.selectSeat("160");
       // purchasePage.selectSeat(VIP1);

        // Step 5 Verify total price is correct when selecting seats
        ExtentReportManager.info("Step5: Verify total price is correct");
        LOG.info("Step5: Verify total price is correct");
        double expectedTotal = 0;
        expectedTotal += purchasePage.getSeatPrice("160");
        //expectedTotal += purchasePage.getSeatPrice(VIP1);
        Assert.assertEquals(expectedTotal, purchasePage.getTotalPrice(), "Total price calculation is incorrect");


        // Step 6 Click Buy Ticket button
        ExtentReportManager.info("Step6: Click Buy Ticket button");
        LOG.info("Step6: Click Buy Ticket button");
        purchasePage.clickBuyTicketButton();

        // Step 7 Verify booking success modal
        ExtentReportManager.info("Step7: Verify booking success modal");
        LOG.info("Step7: Verify booking success modal");
        Assert.assertTrue(purchasePage.isBookingSuccessful(), "Booking was not successful.");

    }




}
