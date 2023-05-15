package org.example;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public class DownloadCourseSyllabusTest {

    Logger logger = LoggerFactory.getLogger(DownloadCourseSyllabusTest.class);

    @BeforeEach
    public void before() {
        TestSetup.setUp();
        TestSetup.setUpDownloadFolder();
    }

    @Test
    public void DownloadCourseSyllabus() {
        logger.info("Starting DownloadCourseSyllabus test");

        // Get to LTU.se
        try {
            open("https://www.ltu.se/");
            logger.info("Opened LTU.se");
        } catch (Exception e) {
            logger.error("Could not open LTU.se", e);
        }

        // Handle cookies
        try {
            SelenideElement allowCookies = $(By.cssSelector("button[class='CybotCookiebotDialogBodyButton']"));
            allowCookies.click();
        } catch (Exception e) {
            logger.error("Error occurred when trying to accept cookies", e);
        }

        // Search for course
        try {
            SelenideElement searchButton = $x("//button[@onclick='showTopInput()']");
            searchButton.click();
            logger.info("Search button found");
        } catch (Exception e) {
            logger.error("Search button not found", e);
        }

        try {
            SelenideElement searchField = $x("//*[@id='cludo-search-bar-input']");
            searchField.sendKeys("I0015N");
            searchField.pressEnter();
            logger.info("Search field found and entered");
        } catch (Exception e) {
            logger.error("Entering search field error", e);
        }

        // Go to course page
        try {
            SelenideElement courseButton = $x("//a[@href='https://www.ltu.se/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?termin=V24']");
            courseButton.click();
            logger.info("Course button found");
        } catch (Exception e) {
            logger.error("Course button not found", e);
        }

        // Choose 2023
        try {
            SelenideElement yearButton = $x("//a[@href='/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?termin=V23']");
            yearButton.click();
            logger.info("Year button found");
        } catch (Exception e) {
            logger.error("Year button not found", e);
        }

        // Click on plan
        try {
            SelenideElement coursePlanButton = $x("//a[@href='/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?termin=V23&kursView=kursplan']");
            coursePlanButton.click();
            logger.info("Course plan clicked");
        } catch (Exception e) {
            logger.error("Course plan not found", e);
        }

        // Download course plan
        try {
            SelenideElement downloadButton = $x("//a[@class='utbplan-pdf-link']");
            File coursePlan = downloadButton.download();
            logger.info("Course plan downloaded");
        } catch (Exception e) {
            logger.error("Error occurred when downloading course plan", e);
        }

    }
}
