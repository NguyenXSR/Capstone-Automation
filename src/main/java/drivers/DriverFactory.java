package drivers;

import org.openqa.selenium.WebDriver;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
