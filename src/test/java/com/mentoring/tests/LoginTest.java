package com.mentoring.tests;

import com.mentoring.builder.LoginCredentialsBuilder;
import com.mentoring.dto.LoginCredentials;
import com.mentoring.driver.DriverManager;
import com.mentoring.pages.LoginPage;
import com.mentoring.pages.SecureAreaPage;
import com.mentoring.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    private DriverManager driverManager;
    private PropertiesReader propertiesReader;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driverManager = DriverManager.getInstance();
        WebDriver driver = driverManager.getDriver();
        propertiesReader = PropertiesReader.getInstance();

        String loginUrl = propertiesReader.getProperty("login.url");
        driver.get(loginUrl);
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driverManager.quitDriver();
    }

    @Test
    public void testSuccessfulLogin() {
        LoginCredentials credentials = new LoginCredentialsBuilder()
                .withValidCredentials()
                .build();

        SecureAreaPage secureAreaPage = loginPage.login(
                credentials.username(),
                credentials.password()
        );

        String expectedMessage = propertiesReader.getProperty("success.message");
        String actualMessage = secureAreaPage.getFlashMessage();

        Assert.assertTrue(secureAreaPage.isSecureAreaDisplayed(), "Secure area should be displayed");
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Flash message should contain: " + expectedMessage);
    }

    @Test
    public void testFailedLogin() {
        LoginCredentials credentials = new LoginCredentialsBuilder()
                .withInvalidCredentials()
                .build();

        loginPage.login(credentials.username(), credentials.password());

        String expectedMessage = propertiesReader.getProperty("error.message");
        String actualMessage = loginPage.getFlashMessage();

        Assert.assertTrue(loginPage.isFlashMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Flash message should contain: " + expectedMessage);
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        LoginCredentials credentials = new LoginCredentials("", "");

        loginPage.login(credentials.username(), credentials.password());

        Assert.assertTrue(loginPage.isFlashMessageDisplayed(), "Error message should be displayed");
    }

    @Test
    public void testLoginWithValidUsernameInvalidPassword() {
        String validUsername = propertiesReader.getProperty("valid.username");
        String invalidPassword = propertiesReader.getProperty("invalid.password");

        LoginCredentials credentials = new LoginCredentials(validUsername, invalidPassword);

        loginPage.login(credentials.username(), credentials.password());

        Assert.assertTrue(loginPage.isFlashMessageDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getFlashMessage().contains("invalid"), "Flash message should indicate invalid credentials");
    }
}
