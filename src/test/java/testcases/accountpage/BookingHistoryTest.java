//package testcases.accountpage;
//
//import base.BaseTest;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.AccountPage;
//import pages.components.BookingHistorySection;
//
//public class BookingHistoryTest extends BaseTest {
//    private AccountPage account;
//    private BookingHistorySection history;
//
//
//
//    @BeforeMethod
//    public void setUpPages() {
//         account = new AccountPage();
//         history = new BookingHistorySection();
//
//    }
//
//    @Test
//    public void TC_ACC_06_Verify_Booking_History_Displays_Required_Fields() {
//
//        account.openAccountPage();
//        history.waitForBookingHistorySectionVisible();
//
//        Assert.assertTrue(history.getBookingCountOnCurrentPage() >= 0, "Booking history should load");
//        // nếu có booking thì verify fields
//        if (history.getBookingCountOnCurrentPage() > 0) {
////            Assert.assertTrue(history.firstBookingHasRequiredFields(),
//                    "First booking should show Date/Time, Title, Duration+Price, Cinema+Branch, Screen+Seats");
//        }
//    }
//
//    @Test
//    public void TC_ACC_07_Verify_Pagination_Available_When_More_Than_6_Bookings() {
//        AccountPage account = new AccountPage();
//        BookingHistorySection history = new BookingHistorySection();
//
//        account.openAccountPage();
//        history.waitForBookingHistorySectionVisible();
//
//        // Rule: > 6 bookings => pagination visible
//        // Bạn sẽ cần dữ liệu môi trường phù hợp (account có >6 bookings).
//        if (history.getBookingCountOnCurrentPage() >= 6) {
//            Assert.assertTrue(history.isPaginationVisible(),
//                    "Pagination should be visible when bookings > 6");
//        }
//    }
//
//
//}
