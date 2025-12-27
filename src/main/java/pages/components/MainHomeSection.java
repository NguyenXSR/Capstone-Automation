package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;

public class MainHomeSection extends BasePage {
    private final By showtimesSection = By.id("lichChieu");
    private final By cinemasSection   = By.id("cumRap");
    private final By newsSection      = By.id("tinTuc");
    private final By mobileAppSection = By.id("ungDung");

    public boolean isShowtimesVisible() {
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(), showtimesSection).isDisplayed();
    }

    public boolean isCinemasVisible() {
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(), cinemasSection).isDisplayed();
    }

    public boolean isNewsVisible() {
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(), newsSection).isDisplayed();
    }

    public boolean isMobileAppVisible() {
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(), mobileAppSection).isDisplayed();
    }

}
