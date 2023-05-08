package org.example;

import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;


public class LoginTest {

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

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
        open("https://www.ltu.se/");
        logger.info("Page opened");

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

        sleep(5000);

    }


}




