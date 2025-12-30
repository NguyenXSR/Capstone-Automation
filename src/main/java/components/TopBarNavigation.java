package components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class TopBarNavigation extends BasePage {
    private By byBtnRegisterLink = By.xpath("//h3[text()='Đăng Ký']");
    private By byBtnLoginLink = By.xpath("//h3[text()='Đăng Nhập']/parent::a");

// header navigation components
    private By byLogo = By.xpath("//img[@alt='Logo']");
    private By byShowtimesLink = By.xpath("//header//h4[contains(text(),'Lịch Chiếu')]");
    private By byCinemasLink = By.xpath("//header//h4[contains(text(),'Cụm Rạp')]");
    private By byNewsLink = By.xpath("//header//h4[contains(text(),'Tin Tức')]");
    private By byMobileAppLink = By.xpath("//header//h4[contains(text(),'Ứng Dụng')]");
    private By byLoggedInUser = By.xpath("//a[@href='/account']/h3");
    private By byLoggedOutBtn = By.xpath("//a[@href='/']/h3[text()='Đăng xuất']");


    private WebDriver driver() {
        return DriverFactory.getDriver();
    }


    public boolean isLogoVisible (){
        LOG.info("CheckLogoDisplayed");
        return waitForVisibilityOfElementLocated(driver(),byLogo).isDisplayed();
    };


    public void clickLogo() {
        LOG.info("clickLogo");
        click(driver(), byLogo);
    }

    public void clickShowtimes() {
        LOG.info("clickShowtimes");
        click(driver(), byShowtimesLink);
    }

    public void clickCinemas() {
        LOG.info("clickCinemas");
        click(driver(), byCinemasLink);
    }

    public void clickNews() {
        LOG.info("clickNews");
        click(driver(), byNewsLink);
    }

    public void clickMobileApp() {
        LOG.info("clickMobileApp");
        click(driver(), byMobileAppLink);
    }

    public void navigateRegisterPage() {
        LOG.info("navigateRegisterPage");
        click(driver(), byBtnRegisterLink);
    }

    public void navigateLoginPage() {
        LOG.info("navigateLoginPage");
        click(driver(), byBtnLoginLink);
    }


    public boolean isLogoutLinkDisplayed() {
        LOG.info("isLogoutLinkDisplayed");
        return waitForVisibilityOfElementLocated(driver(),byLoggedOutBtn ).isDisplayed();
    }

    public String getUserProfileName() {
        LOG.info("getUserProfileName");
        return waitForVisibilityOfElementLocated(driver(),byLoggedInUser ).getText().trim();
    }
}
