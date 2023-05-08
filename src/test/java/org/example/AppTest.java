package org.example;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;


public class AppTest {
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void openPage() {
        open("https://www.ltu.se/");
        sleep(5000);

    }


}




