package pages;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PurchasePage extends BasePage {


    private final By seatMapContainer = By.cssSelector(".MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-8");

    // Seat items can be clickable
    private final By seatItems = By.xpath("//div[@class='App']/div[2]/div/div[1]/div/div[1]/button");

    // Summary area
    private final By summaryContainer = By.cssSelector(".MuiGrid-grid-xs-4");
    private final By summaryPriceTotal = By.cssSelector(".MuiGrid-grid-xs-4 p.MuiTypography-body1");
    private final By summaryCinema = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(3)>h3:nth-child(2)");
    private final By summaryAddress = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(5)>h3:nth-child(2)");
    private final By summaryScreenNumber = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(7)>h3:nth-child(2)");
    private final By summaryDateTime = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(9)>h3:nth-child(2)");
    private final By summaryMovie = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(11)>h3:nth-child(2)");
    private final By summarySeats = By.cssSelector(".MuiGrid-grid-xs-4 div:nth-child(13)>h3:nth-child(2)");

    // btn đặt vé
    private final By btnBuyTicket = By.xpath("//span[contains(text(),'ĐẶT VÉ')]");

    // modal elements
    private final By modalContainer = By.cssSelector("div[role='dialog']");
    private final By modalContent = By.cssSelector("h2#swal2-title");
    private final By modalConfirmBtn = By.xpath("//button[contains(text(),'Đồng ý')]");
    private final By btnDenyLogin = By.xpath("//button[contains(text(),'Không')]");



    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    // open purchase page based on showtimeId
    public void navigateToPurchasePage(String showtimeId) {
        driver().get("https://demo1.cybersoft.edu.vn/purchase/" + showtimeId);
    }

    public boolean isSeatMapVisible() {
        return waitForVisibilityOfElementLocated(driver(), seatMapContainer).isDisplayed();
    }

    public boolean isSummaryVisible() {
        return waitForVisibilityOfElementLocated(driver(), summaryContainer).isDisplayed();
    }


    private By seatByNumber(String seatNumber) {
        return By.xpath("//button[.='" + seatNumber + "']");
    }

    public void selectSeat(String seatNumber) {
        click(driver(), seatByNumber(seatNumber));
    }

    public boolean isSeatSelected(String seatNumber) {
        WebElement seatElement = waitForVisibilityOfElementLocated(driver(), seatByNumber(seatNumber));
        String styleAttr = seatElement.getAttribute("style");
        return styleAttr != null && styleAttr.contains("background-color: green");
    }

    public boolean isSeatBooked(String seatNumber) {
        WebElement seatElement = waitForVisibilityOfElementLocated(driver(), seatByNumber(seatNumber));
        String classAttr = seatElement.getAttribute("class");
        return classAttr != null && classAttr.contains("Mui-disabled");
    }



    public String getSummaryCinema() {
        return waitForVisibilityOfElementLocated(driver(), summaryCinema).getText().trim();
    }

    public String getSummaryAddress() {
        return waitForVisibilityOfElementLocated(driver(), summaryAddress).getText().trim();
    }

    public String getSummaryScreenNumber() {
        return waitForVisibilityOfElementLocated(driver(), summaryScreenNumber).getText().trim();
    }

    public String getSummaryDateTime() {
        return waitForVisibilityOfElementLocated(driver(), summaryDateTime).getText().trim();
    }

    public String getSummaryMovie() {
        return waitForVisibilityOfElementLocated(driver(), summaryMovie).getText().trim();
    }

    public String getSummarySeatsTxt() {
        return waitForVisibilityOfElementLocated(driver(), summarySeats).getText().trim();
    }


    // Modal methods

    public boolean isModalVisible() {
        return waitForVisibilityOfElementLocated(driver(), modalContainer).isDisplayed();
    }

    public String getModalMessage() {
        return waitForVisibilityOfElementLocated(driver(), modalContent).getText().trim();
    }


    public void clickModalConfirmButton() {
        try {
            click(driver(), modalConfirmBtn);
        } catch (Exception e) {
            throw new RuntimeException("Modal Confirm button not found or not clickable!", e);
        }
    }

    public void clickModalDenyLoginButton() {
        try {
            click(driver(), btnDenyLogin);
        } catch (Exception e) {
            throw new RuntimeException("Modal Deny Login button not found or not clickable!", e);
        }
    }

    //booking

    public void clickBuyTicketButton() {
        click(driver(), btnBuyTicket);
    }


    public boolean isBookingSuccessful() {
        // check modal visible and message contains "Đặt vé thành công"
        if (isModalVisible()) {
            String message = getModalMessage();
            return message.contains("Đặt vé thành công");
        }
        return false;
    }


//    public double getSeatPrice(String seatNumber) {
//        // fixed price for VIP seat is 90000
//        //if seat number from 35 to 126 is VIP
//        int seatNum = Integer.parseInt(seatNumber);
//        if (seatNum >= 35 && seatNum <= 126) {
//            return 90000;
//        } else {
//            return 75000;
//        }
//    }

    public double getSeatPrice(String seatNumber) {
        int seatNum;
        try {
            seatNum = Integer.parseInt(seatNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid seat number: " + seatNumber, e);
        }
        if ((seatNum >= 35 && seatNum <= 46) ||
                (seatNum >= 51 && seatNum <= 62) ||
                (seatNum >= 67 && seatNum <= 78) ||
                (seatNum >= 83 && seatNum <= 94) ||
                (seatNum >= 99 && seatNum <= 110) ||
                (seatNum >= 115 && seatNum <= 126)) {
            return 90000.0;
        } else {
            return 75000.0;
        }
    }


    public double getTotalPrice() {
        String totalText = waitForVisibilityOfElementLocated(driver(), summaryPriceTotal).getText().trim();
        // Remove any non-numeric characters (like currency symbols) and parse to double
        String numericText = totalText.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numericText);
    }


}
