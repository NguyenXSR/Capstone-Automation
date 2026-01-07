package pages.components;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogOutConfirmModal extends BasePage {

    private final By byLogOutConfirmModal = By.cssSelector(".swal2-popup.swal2-modal.swal2-icon-question.swal2-show");
    private final By byLogOutModalTxt = By.xpath("//h2[normalize-space()='Bạn có muốn đăng xuất ?']");
    private final By byModalBtnOk = By.xpath("//button[normalize-space()='Đồng ý']");
    private final By byModalBtnCancel = By.cssSelector(".swal2-icon-question .swal2-cancel");

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    public void waitLogoutConfirmModalVisible() {
        waitForVisibilityOfElementLocated(driver(), byLogOutConfirmModal);
    }

    public boolean isVisible() {
        return waitForVisibilityOfElementLocated(DriverFactory.getDriver(), byLogOutConfirmModal).isDisplayed();
    }

    public String getModalText() {
        LOG.info("getModalText");
        return waitForVisibilityOfElementLocated(driver(), byLogOutModalTxt).getText();
    }

    public void clickOkButton() {
        LOG.info("clickOkButton");
        click(driver(), byModalBtnOk);
    }

    public void clickCancelButton() {
        LOG.info("clickCancelButton");
        click(driver(), byModalBtnCancel);
    }

    public void waitConfirmModalClosed() {
        waitForInvisibilityOfElementLocated(driver(), byLogOutConfirmModal);
    }


    public void waitOkModalClosed() {
        waitForVisibilityOfElementLocated(driver(), By.xpath(
                "//div[@id='swal2-content' and contains(normalize-space(.),'Cảm ơn bạn đã sử dụng TIX!')]"));
        By button = By.xpath("//button[normalize-space()='OK']");
        click(driver(), button);
    }
}
