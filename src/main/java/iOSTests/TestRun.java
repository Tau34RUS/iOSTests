package iOSTests;

import com.sun.jna.platform.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class TestRun {

    private static Logger logger;

    private final Methods App = new Methods();

    @BeforeTest
    public void Before() throws Exception {

        /* log4j setup */
        logger = Logger.getLogger("iOSTestLogger");


        logger.info("-----");
        logger.info("Initial Settings and App Startup");
        App.SetUp();
        logger.info("Settings Applied");

    }

    @AfterTest
    public void After() throws IOException {
        logger.info("-----");
        logger.info("Userlogin: " + Variables.userlogin);
        logger.info("Userpass:  " + Variables.userpass);
        logger.info("-----");
        logger.info("Closing Application");
        App.Quit();
    }

    @AfterMethod
    public void AfterMethod(ITestResult result) throws IOException {
        if (result.getStatus()==2) {
            logger.info("Test Failed!");
            logger.info("Taking Screenshot...");
            App.captureScreenShots();
        }
        logger.warn("Result is: " + result.getStatus());
    }

    @Test
    public void AndroidTestRegister() {

        logger.info("-----");
        logger.info("Starting AppiumBased Tests");
        logger.info("- - -");

        App.SplashScreen();

        App.Register();

    }

}
