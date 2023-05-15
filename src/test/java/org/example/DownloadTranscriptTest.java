package org.example;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class DownloadTranscriptTest {

    Logger logger = LoggerFactory.getLogger(DownloadTranscriptTest.class);

    @BeforeEach
    public void before() {
        TestSetup.setUp();
        TestSetup.setUpDownloadFolder();
    }

    @Test
    public void DownloadTranscript() {
        // Login
        LoginTest login = new LoginTest();
        login.LoginLTU();
        logger.info("Login successful");

        logger.info("*** STARTING TEST CASE: DownloadTranscriptTest ***");
        // Get to transcript page
        try {
            SelenideElement utbildningButton = $$(By.cssSelector("a[class='toggleTopMenu']")).get(1);
            utbildningButton.click();
            logger.info("Utbildning button clicked");
        } catch (Exception e) {
            logger.error("Utbildning button not found", e);
        }

        try {
            SelenideElement studentButton = $x("(//a[text()='Student'])[1]");
            studentButton.click();
            logger.info("Student button clicked");
        } catch (Exception e) {
            logger.error("Student button not found", e);
        }

        // Get to registeruttag page
        try {
            SelenideElement registerButton = $(("a[href='https://www.student.ladok.se/student/#/intyg']"));
            registerButton.click();
            logger.info("Registry button clicked");
        } catch (Exception e) {
            logger.error("Registry button not found", e);
        }

        // Handle LADOK cookies
        try {
            SelenideElement button = $x("(//button)[1]");
            button.click();
            logger.info("LADOK cookies accepted");
        } catch (Exception e) {
            logger.error("Error accepting LADOK cookies", e);
        }

        // Press link to Ladok
        try {
            SelenideElement linkButton = $(("a[href='/student/login?ret=/app/studentwebb']"));
            linkButton.click();
            logger.info("Link to Ladok button clicked");
        } catch (Exception e) {
            logger.error("Link to Ladok button not found", e);
        }


        // Press on right university to login
        // If LTU appears directly:
        /*try {
            SelenideElement LTULogin = $x("(//a)[1]");
            LTULogin.click();
            logger.info("LTU login button clicked");
        } catch (Exception e) {
            logger.error("LTU login button not found", e);
        }*/

        // Else:
        // Enter Luleå University of Technology
        try {
            SelenideElement searchInput = $x("(//input)[1]");
            searchInput.setValue("Luleå Tekniska").pressEnter();
            logger.info("Search for LTU");

            SelenideElement LTULogin = $(("a[data-href='https://idp.ltu.se/idp/shibboleth']"));
            LTULogin.click();

            logger.info("LTU login button clicked");
        } catch (Exception e) {
            logger.error("Search error occurred", e);
        }

        // Go to menu and registration
        try {
            SelenideElement menyButton = $x("(//button)[1]");
            menyButton.click();
            logger.info("Meny button clicked");
        } catch (Exception e) {
            logger.error("Meny button not found, try adjust screen size", e);
        }

        try {
            SelenideElement intyg = $x("//a[text()=' Intyg']");
            intyg.click();
            logger.info("Meny button clicked");
        } catch (Exception e) {
            logger.error("Meny button not found", e);
        }

        // Test case; download transcript
        try {
            SelenideElement registerTranscriptButton = $x("(//a[text()='Registreringsintyg'])[1]");
            File registerTranscriptFile = registerTranscriptButton.download();
            logger.info("Register transcript button clicked");
        } catch (Exception e) {
            logger.error("Register transcript button not found", e);
        }

        sleep(5000);
    }
}
