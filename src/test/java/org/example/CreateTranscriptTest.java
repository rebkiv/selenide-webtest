package org.example;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.codeborne.selenide.Selenide.*;

public class CreateTranscriptTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @BeforeEach
    public void before() {
        TestSetup.setUp();
    }

    //@Disabled
    @Test
    public void CreateTranscript() {

        //Login
        LoginTest login = new LoginTest();
        login.LoginLTU();
        logger.info("Login successful");


        // Get to student page
        logger.info("*** STARTING TEST CASE: CreateTranscriptTest ***");
        try {
            SelenideElement utbildningButton = $$(By.cssSelector("a[class='toggleTopMenu']")).get(1);
            utbildningButton.click();
            logger.info("Education button clicked");
        } catch (Exception e) {
            logger.error("Education button not found", e);
        }

        try {
            SelenideElement studentButton = $x("(//a[text()='Student'])[1]");
            studentButton.click();
            logger.info("Student button clicked");
        } catch (Exception e) {
            logger.error("Student button not found", e);
        }

        // Get to transcript page (LADOK)
        try {
            SelenideElement registerButton = $(("a[href='https://www.student.ladok.se/student/#/intyg']"));
            registerButton.click();
            logger.info("Registry button clicked");
        } catch (Exception e) {
            logger.error("Registry button not found", e);
        }

        // Handle cookies
        try {
            SelenideElement button = $x("(//button)[1]");
            button.click();
            logger.info("Cookies accepted");
        } catch (Exception e) {
            logger.error("Error with accepting cookies", e);
        }

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

        // In LADOK
        // Go to menu and registration
        try {
            SelenideElement menyButton = $x("(//button)[1]");
            menyButton.click();
            logger.info("Menu button clicked");
        } catch (Exception e) {
            logger.error("Menu button not found", e);
        }

        try {
            SelenideElement intyg = $x("//a[text()=' Intyg']");
            intyg.click();
            logger.info("Transcript button clicked");
        } catch (Exception e) {
            logger.error("Transcript button not found", e);
        }

        try {
            SelenideElement skapaButton = $x("(//button)[3]");
            skapaButton.click();
            logger.info("Create button clicked");
        } catch (Exception e) {
            logger.error("Create button not found", e);
        }

        // Choose type of transcript
        SelenideElement dropdown = $x("//select"); // locate the dropdown element
        dropdown.selectOption("Registreringsintyg");

        // Create transcript
        try {
            SelenideElement skapaButton = $x("(//button)[5]");
            skapaButton.click();
            logger.info("Transcript created");
        } catch (Exception e) {
            logger.error("Create button not found", e);
        }

        sleep(5000);

    }
}
