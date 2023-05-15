package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;


public class LoginTest {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @BeforeAll
    public static void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
    }

    @Test
    public void LoginLTU() {

        // Get credentials
        String filePath = "C:\\Temp\\LTU.json";
        File jsonFile = new File(filePath);

        String username = null;
        String password = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            username = jsonNode.get("LTUCredentials").get("username").asText();
            password = jsonNode.get("LTUCredentials").get("password").asText();
            logger.info("Got the LTU credentials");
        } catch (IOException e) {
            logger.error("An error occurred when getting the LTU credentials: ", e);
        }

        // Test case
        logger.info("*** START LOGIN TEST ***");
        try {
            open("https://www.ltu.se/");
            logger.info("LTU page opened");
        } catch (Exception e) {
            logger.error("LTU page not opened", e);
        }

        // Handle cookies
        try {
            SelenideElement allowCookies = $(By.cssSelector("button[class='CybotCookiebotDialogBodyButton']"));
            allowCookies.click();
        } catch (Exception e) {
            logger.error("Error occurred when trying to accept cookies: ", e);
        }

        // Get to login page
        try {
            SelenideElement studentButton = $x("(//a[text()='Student'])[1]");
            studentButton.click();
            logger.info("Student button clicked");
        } catch (Exception e) {
            logger.error("Student button not found", e);
        }

        try {
            SelenideElement mittLTUButton = $x("(//a[text()='Mitt LTU'])[1]");
            mittLTUButton.click();
            logger.info("Mitt LTU button clicked");
        } catch (Exception e) {
            logger.error("Mitt LTU button not found", e);
        }

        try {
            SelenideElement usernameField = $x("//input[@id='username']");
            usernameField.setValue(username);
            logger.info("Email entered");
        } catch (Exception e) {
            logger.error("Email field not found", e);
        }

        try {
            SelenideElement passwordField = $x("//input[@id='password']");
            passwordField.setValue(password);
            logger.info("Password entered");
        } catch (Exception e) {
            logger.error("Password field not found", e);
        }

        try {
            SelenideElement loginButton = $x("//input[@tabindex='6']");
            loginButton.click();
            logger.info("Login button clicked");
        } catch (Exception e) {
            logger.error("Login button not found", e);
        }

        // Validate login successful
        try {
            String currentURL = url();
            String expectedURL = "https://portal.ltu.se/group/student/start"; // Change to correct expected URL if needed
            if (currentURL.equals(expectedURL)) {
                logger.info("Login successful");
            } else {
                throw new Exception("Login failed, expected URL: " + expectedURL + ", actual URL: " + currentURL);
            }
        } catch (Exception e) {
            logger.error("Error when logging in, ", e);
        }

        sleep(5000);
    }
}




