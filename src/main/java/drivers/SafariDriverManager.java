package drivers;

import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverManager extends DriverManager{
    @Override
    public void createDriver() {
        this.driver = new SafariDriver();
    }
}
