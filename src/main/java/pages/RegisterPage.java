package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


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
    private By byPasswordEyeIcon =
            By.xpath("(//button[@type='button' and contains(@class, 'MuiIconButton-edgeEnd')])[1]");
    private By byConfirmPasswordEyeIcon =
            By.xpath("(//button[@type='button' and contains(@class, 'MuiIconButton-edgeEnd')])[2]");

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

    public String getGlobalErrorMessage() {
        LOG.info("getGlobalErrorMessage");
        By globalMsg = By.cssSelector("div[role='alert']");

        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(10));
        try {
            return wait.until(d -> {
                List<WebElement> els = d.findElements(globalMsg);
                if (els.isEmpty()) return null;
                String txt = els.get(0).getText().trim();
                return txt.isEmpty() ? null : txt;
            });
        } catch (TimeoutException e) {
            return "";
        }
    };
}

