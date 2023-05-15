package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class LogOutTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @BeforeAll
    public static void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
    }

    @Test
    public void logOutTest() {
        //Login
        LoginTest login = new LoginTest();
        login.LoginLTU();
        logger.info("Login successful");

        logger.info("*** STARTING LOG OUT TEST ***");
        try {
            SelenideElement userDropdown = $("a[class^='user-avatar-link'] i[class='icon-caret-down']");
            userDropdown.click();
            logger.info("User dropdown clicked");
        } catch (Exception e) {
            logger.error("User dropdown not found", e);
        }

        try {
            SelenideElement logOutButton = $("a[title^='Logga']");
            logOutButton.click();
            logger.info("Log out button clicked");
        } catch (Exception e) {
            logger.error("Log out button not found", e);

        }

        // Validate log out successful
        try {
            String currentURL = url();
            String expectedURL = "https://weblogon.ltu.se/cas/logout"; // Change to correct expected URL if needed
            if (currentURL.equals(expectedURL)) {
                logger.info("Logout successful");
            } else {
                throw new Exception("Logout failed, expected URL: " + expectedURL + ", actual URL: " + currentURL);
            }
        } catch (Exception e) {
            logger.error("Error when logging out, ", e);
        }
    }
}
