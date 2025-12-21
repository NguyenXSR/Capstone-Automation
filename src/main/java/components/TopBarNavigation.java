package components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;


public class TopBarNavigation extends BasePage {
    private By byBtnRegisterLink = By.xpath("//h3[text()='Đăng Ký']");
    private By byBtnLoginLink = By.xpath("//h3[text()='Đăng Nhập']/parent::a");

// header navigation components
    private By byLogo = By.xpath("//img[@alt='Logo']");
    private By byShowtimesLink = By.xpath("//header//h4[contains(text(),'Lịch Chiếu')]");
    private By byCinemasLink = By.xpath("//header//h4[contains(text(),'Cụm Rạp')]");
    private By byNewsLink = By.xpath("//header//h4[contains(text(),'Tin Tức')]");
    private By byMobileAppLink = By.xpath("//header//h4[contains(text(),'Ứng Dụng')]");
    private By byLoggedInUser = By.cssSelector("a[href='/account']");


    public boolean isLogoVisible (){
        LOG.info("CheckLogoDisplayed");
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(),byLogo).isDisplayed();
    };


    public void clickLogo() {
        LOG.info("clickLogo");
        click(DriverFactory.getDriver(), byLogo);
    }

    public void clickShowtimes() {
        LOG.info("clickShowtimes");
        click(DriverFactory.getDriver(), byShowtimesLink);
    }

    public void clickCinemas() {
        LOG.info("clickCinemas");
        click(DriverFactory.getDriver(), byCinemasLink);
    }

    public void clickNews() {
        LOG.info("clickNews");
        click(DriverFactory.getDriver(), byNewsLink);
    }

    public void clickMobileApp() {
        LOG.info("clickMobileApp");
        click(DriverFactory.getDriver(), byMobileAppLink);
    }

    public void navigateRegisterPage() {
        LOG.info("navigateRegisterPage");
        click(DriverFactory.getDriver(), byBtnRegisterLink);
    }

    public void navigateLoginPage() {
        LOG.info("navigateLoginPage");
        click(DriverFactory.getDriver(), byBtnLoginLink);
    }





}
