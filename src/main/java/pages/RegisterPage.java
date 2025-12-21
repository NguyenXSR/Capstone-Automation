package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;


public class RegisterPage extends CommonPage{

    //private > day la tinh dong goi (trong oop)
    private By byTxtAccount = By.id("taiKhoan");
    private By byTxtPassword = By.id("matKhau");
    private By byTxtConfirmPassword = By.id("confirmPassWord");
    private By byTxtName = By.id("hoTen");
    private By byTxtEmail = By.id("email");
    private By byBtnRegisterNewAcc = By.xpath("//button[.='Đăng ký']");
    private By byLblRegisterMsg = By.id("swal2-title");

    public void enterAccount(String account) {
        LOG.info(String.format("enterAccount: %s", account));
        sendKeys(DriverFactory.getDriver(), byTxtAccount, account);
    }

    public void enterPassword(String password) {
        LOG.info(String.format("enterPassword: %s", password));
        sendKeys(DriverFactory.getDriver(), byTxtPassword, password);
    }

    public void enterConfirmPassword(String password) {
        LOG.info(String.format("enterConfirmPassword: %s", password));
        sendKeys(DriverFactory.getDriver(), byTxtConfirmPassword, password);
    }

    public void enterName(String name) {
        LOG.info(String.format("enterName: %s", name));
        sendKeys(DriverFactory.getDriver(), byTxtName, name);
    }

    public void enterEmail(String email) {
        LOG.info(String.format("enterEmail: %s", email));
        sendKeys(DriverFactory.getDriver(), byTxtEmail, email);
    }

    public void clickRegister() {
        LOG.info("clickRegister");
        click(DriverFactory.getDriver(), byBtnRegisterNewAcc);
    }

    public String getRegisterMessage() {
        LOG.info("getRegisterMessage");
        return getText(DriverFactory.getDriver(), byLblRegisterMsg);
    }

    public void waitRegisterMessageDisappear() {
        LOG.info("waitRegisterMessageDisappear");
        waitForInvisibilityOfElementLocated(DriverFactory.getDriver(), byLblRegisterMsg);
    }
    }

