package org.example;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import static com.codeborne.selenide.Selenide.*;

public class CheckFinalExaminationTest {

    Logger logger = LoggerFactory.getLogger(CheckFinalExaminationTest.class);

    @BeforeEach
    public void before() {
        TestSetup.setUp();
        TestSetup.setUpScreenshotFolder();
        System.setProperty("selenide.reports.format", "jpeg");

    }

    @Test
    public void CheckFinalExamination() {
        //Login
        LoginTest login = new LoginTest();
        login.LoginLTU();
        logger.info("Login successful");

        logger.info("*** STARTING TEST: Check final examination ***");
        // Get to Ladok to check examination date
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

        // Get to registeruttag page
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
            logger.error("Error when accepting cookies", e);
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

        // Get examination date from Ladok
        try {
            SelenideElement examinationDate = $(".ladok-card-body-sub-rubrik ladok-examinationstillfalle-kort-underrubrik");
            String examinationDateText = examinationDate.getText();
            System.out.println(examinationDateText);
            logger.info("Examination date: " + examinationDateText);
        } catch (Exception e) {
            logger.error("Examination date not found", e);
        }

        // Do a printscreen
        try {
            String screenshotAsBase64 = screenshot(OutputType.BASE64);
            // Decode the base64 string into a byte array
            byte[] decoded = Base64.getDecoder().decode(screenshotAsBase64);

            // Save the screenshot as a JPEG file
            try {
                FileOutputStream fos = new FileOutputStream("target/screenshots/screenshot.jpeg");
                fos.write(decoded);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("Printscreen taken");
        } catch (Exception e) {
            logger.error("Printscreen not taken", e);
        }

    }

}
