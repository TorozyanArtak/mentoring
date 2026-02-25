package com.mentoring.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SecureAreaPage {

    @FindBy(id = "flash")
    private WebElement flashMessage;

    @FindBy(css = "a.button.secondary")
    private WebElement logoutButton;

    @FindBy(css = "h2")
    private WebElement pageHeader;

    public SecureAreaPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String getFlashMessage() {
        return flashMessage.getText();
    }

    public boolean isSecureAreaDisplayed() {
        return pageHeader.isDisplayed() && logoutButton.isDisplayed();
    }
}

