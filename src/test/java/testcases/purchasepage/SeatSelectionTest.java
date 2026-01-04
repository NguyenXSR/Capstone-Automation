package testcases.purchasepage;

import api.SeatApi;
import api.dto.SeatResponse;
import api.dto.Seats;
import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PurchasePage;
import reports.ExtentReportManager;
import utils.SeatDataProvider;

import java.util.List;

@Listeners(TestListener.class)
public class SeatSelectionTest extends BaseTest {

    private PurchasePage purchasePage;
    private LoginPage loginPage;


    // truyen showtimeID de chon lich chieu
    private final String showtimeID = "43638"; //Tenet

    //truyen seatNumber de chon ghe
//    private final String REG1 = "33";
//    private final String REG2 = "34";
//    private final String VIP1 = "35";


    // Data lấy từ API
    private SeatResponse seatResponse;
    private List<Seats> availableRegularSeats;
    private List<Seats> availableVipSeats;

    @BeforeMethod
    public void setupPages() {
        purchasePage = new PurchasePage();
        loginPage = new LoginPage();

        // Call API trước mỗi test
        seatResponse = SeatApi.getSeatData(showtimeID);

        // Chuẩn bị data ghế
        availableRegularSeats =
                SeatDataProvider.getAvailableSeats(seatResponse, "Thuong", 10);

        availableVipSeats =
                SeatDataProvider.getAvailableSeats(seatResponse, "Vip", 5);

        Assert.assertTrue(availableRegularSeats.size() >= 2,
                "Not enough available regular seats for test");

        Assert.assertTrue(availableVipSeats.size() >= 1,
                "Not enough available VIP seats for test");




    }



    @Test (description = "Verify seat selection functionality")
    public void TC01_Verify_Seat_Selection_Functionality() {

        // Lấy số ghế từ data đã chuẩn bị
        String REG1 = availableRegularSeats.get(0).getTenGhe();
        String REG2 = availableRegularSeats.get(1).getTenGhe();
        String VIP1 = availableVipSeats.get(0).getTenGhe();


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


        double expectedTotal = 0;
        expectedTotal += SeatDataProvider.getSeatPriceByType(seatResponse, REG1);
        expectedTotal += SeatDataProvider.getSeatPriceByType(seatResponse, REG2);
        expectedTotal += SeatDataProvider.getSeatPriceByType(seatResponse, VIP1);


        Assert.assertEquals(expectedTotal, purchasePage.getTotalPrice(),
                "Total price calculation is incorrect.");


        // Unselect a seat and verify updates
        ExtentReportManager.info("Click again on a selected seat to unselect and validate update");
        LOG.info("Click again on a selected seat to unselect and validate update");
        purchasePage.selectSeat(REG2);
        Assert.assertFalse(purchasePage.isSeatSelected(REG2), "Regular seat 2 is still selected after unselecting.");

        ExtentReportManager.info("Total price updated after unselecting a seat");
        LOG.info("Total price updated after unselecting a seat");
        String seatsAfter = purchasePage.getSummarySeatsTxt();
        Assert.assertFalse(seatsAfter.contains(REG2), "Summary still contains unselected Regular seat" + REG2);
        double expectedTotalAfter = SeatDataProvider.getSeatPriceByType(seatResponse, REG1)
                + SeatDataProvider.getSeatPriceByType(seatResponse, VIP1);

        Assert.assertEquals(expectedTotalAfter, purchasePage.getTotalPrice(), "Total price did not update correctly after unselecting a seat.");

        // Try to select more than 8 seats and verify error modal
//        ExtentReportManager.info("Try to select more than 8 available seats, validate modal error");
//        LOG.info("Try to select more than 8 available seats, validate modal error");
//        String[] moreSeats = {"34", "36", "37", "38", "39", "40", "41"}; // choose 7 more available seats to exceed limit
//        for (String seat : moreSeats) {
//            purchasePage.selectSeat(seat);
//        }

    }

    @Test (description = "Verify error modal when selecting more than 8 seats")
    public void TC_Verify_Select_More_Than_8_Seats_Error() {
        // Navigate to the seat selection page for the specified showtime
        ExtentReportManager.info("Navigate to purchase page");
        LOG.info("Navigate to purchase page");
        purchasePage.navigateToPurchasePage(showtimeID);

        ExtentReportManager.info("Try to select more than 8 available seats, validate modal error");
        LOG.info("Try to select more than 8 available seats, validate modal error");
        List<Seats> seatsToSelect =
                availableRegularSeats.subList(0, 9);

        ExtentReportManager.info("Select more than 8 seats");
        for (Seats seat : seatsToSelect) {
            purchasePage.selectSeat(seat.getTenGhe());
        }
        // BUG: Error modal is not displayed when selecting more than 8 seats
        int selectedSeatCount = purchasePage.getSelectedSeatCount();
        ExtentReportManager.info("Selected seats count: " + selectedSeatCount);
        LOG.info("Selected seats count: {}", selectedSeatCount);

        if (selectedSeatCount > 8) {
            ExtentReportManager.fail("BUG: User can select more than 8 seats. Actual selected: " + selectedSeatCount);
            LOG.warn("BUG: Selected seats = {}", selectedSeatCount);
            Assert.fail("BUG: User can select more than 8 seats. Actual selected: " + selectedSeatCount);
        }


    }



    @Test (description = "Verify booking requires login")
    public void TC01_Verify_Booking_Requires_Login() {

        String REG1 = availableRegularSeats.get(0).getTenGhe();


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



    @Test (description = "Verify summary section and complete booking successfully")
    public void TC_Verify_Summary_And_Booking_Success() {

        String REG1 = availableRegularSeats.get(0).getTenGhe();

        //Step 0: Login first before booking
        ExtentReportManager.info("Step0: Login first before booking");
        LOG.info("Step0: Login first before booking");
        loginPage.getTopBarNavigation().navigateLoginPage();
        loginPage.enterAccount("Alo_456832167"); //exist account
        loginPage.enterPassword("StrongPass123");
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
        Assert.assertFalse(purchasePage.getSummaryCinema().isEmpty(), "Cinema name is not displayed in summary.");
        Assert.assertFalse(purchasePage.getSummaryAddress().isEmpty(), "Cinema address is not displayed in summary.");
        Assert.assertFalse(purchasePage.getSummaryScreenNumber().isEmpty(), "Screen number is not displayed in summary.");
        Assert.assertFalse(purchasePage.getSummaryDateTime().isEmpty(), "Show date/time is not displayed in summary.");
        Assert.assertFalse(purchasePage.getSummaryMovie().isEmpty(), "Movie name is not displayed in summary.");


        // Step 4 Select seats
        ExtentReportManager.info("Step4: Select seat " + REG1 +" for booking");
        LOG.info("Step4: Select seats for booking");
        purchasePage.selectSeat(REG1);
        // purchasePage.selectSeat(VIP1);

        // Step 5 Verify total price is correct when selecting seats
        ExtentReportManager.info("Step5: Verify total price is correct");
        LOG.info("Step5: Verify total price is correct");


        double expectedTotal = 0;
        expectedTotal +=
                SeatDataProvider.getSeatPriceByType(seatResponse, REG1);
        //expectedTotal += SeatDataProvider.getSeatPriceByType(seatResponse, VIP1);


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
