package org.example;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestSetup {

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        Configuration.browserCapabilities = options;
    }

    @BeforeAll
    public static void setUpDownloadFolder() {
        Configuration.browser = "chrome";
        String downloadPath = System.getProperty("user.dir") + "/target/downloads";
        Configuration.downloadsFolder = downloadPath;
    }

    public static void setUpScreenshotFolder() {
        Configuration.browser = "chrome";
        String screenshotPath = System.getProperty("user.dir") + "/target/screenshots";
        //Configuration.screenshots = false;
        Configuration.reportsFolder = screenshotPath;
    }

}
