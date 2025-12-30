package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegisterPage extends CommonPage{

    //private > day la tinh dong goi (trong oop)
    private By byTxtAccount = By.id("taiKhoan");
    private By byTxtPassword = By.id("matKhau");
    private By byTxtConfirmPassword = By.id("confirmPassWord");
    private By byTxtName = By.id("hoTen");
    private By byTxtEmail = By.id("email");
    private By byBtnRegisterNewAcc = By.xpath("//button[.='Đăng ký']");
    private By byLblRegisterMsg = By.id("swal2-title");

    //pw eye icon
    private By byPasswordEyeIcon = By.cssSelector("button#matKhau");
    private By byConfirmPasswordEyeIcon = By.cssSelector("button#confirmPassWord");

    //already have account link
    private By alreadyHaveAccountLn = By.xpath("//h3[.='Bạn đã có tài khoản? Đăng nhập']");


    private WebDriver driver() {
        return DriverFactory.getDriver();
    }



    public void enterAccount(String account) {
        LOG.info(String.format("enterAccount: %s", account));
        sendKeys(driver(), byTxtAccount, account);
    }

    public void enterPassword(String password) {
        LOG.info(String.format("enterPassword: %s", password));
        sendKeys(driver(), byTxtPassword, password);
    }

    public void enterConfirmPassword(String password) {
        LOG.info(String.format("enterConfirmPassword: %s", password));
        sendKeys(driver(), byTxtConfirmPassword, password);
    }

    public void enterName(String name) {
        LOG.info(String.format("enterName: %s", name));
        sendKeys(driver(), byTxtName, name);
    }

    public void enterEmail(String email) {
        LOG.info(String.format("enterEmail: %s", email));
        sendKeys(driver(), byTxtEmail, email);
    }

    public void fillForm(String username, String email, String pass, String confirmPass, String name) {
        enterAccount(username);
        enterEmail(email);
        enterPassword(pass);
        enterConfirmPassword(confirmPass);
        enterName(name);
    }

    public void clickRegister() {
        LOG.info("clickRegister");
        click(driver(), byBtnRegisterNewAcc);
    }

    public String getRegisterMessage() {
        LOG.info("getRegisterMessage");
        return getText(driver(), byLblRegisterMsg);
    }

    public void waitRegisterMessageDisappear() {
        LOG.info("waitRegisterMessageDisappear");
        waitForInvisibilityOfElementLocated(driver(), byLblRegisterMsg);
    }

    public String getPasswordType() {
        return waitForVisibilityOfElementLocated(driver(),byTxtPassword).getAttribute("type");

    }
    public String getConfirmPasswordType() {
        return waitForVisibilityOfElementLocated(driver(),byTxtConfirmPassword ).getAttribute("type");
    }

    public void togglePassword() {
        click(driver(), byPasswordEyeIcon);
    }
    public void toggleConfirmPassword() {
        click(driver(), byConfirmPasswordEyeIcon);
    }


    public void clickAlreadyHaveAccount() {
        click(driver(), alreadyHaveAccountLn);
    }


    public String getFieldErrorMessage(String expectedField) {
        By fieldErrorMsg = By.id(expectedField + "-helper-text");
        LOG.info("getFieldErrorMessage");
        return getText(driver(), fieldErrorMsg);
    }

    public String getGlobalMessage() {
        By globalMsg = By.cssSelector("div[role='alert']");
        LOG.info("getGlobalMessage");
        return getText(driver(), globalMsg);
    }
}

