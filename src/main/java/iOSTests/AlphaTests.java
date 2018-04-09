package iOSTests;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class AlphaTests {

    private static Logger logger;
    private final Methods App = new Methods();

    @BeforeTest(alwaysRun = true)
    public void Before() throws Exception {

        /* log4j setup */
        logger = Logger.getLogger("iOSTestLogger");

        logger.info("-----");
        logger.info("Initial Settings and App Startup");
        App.SetUp();
        logger.info("Settings Applied");

    }

    @AfterTest
    public void After() {
        logger.info("-----");
        logger.info("Userlogin: " + Variables.userlogin);
        logger.info("Userpass:  " + Variables.userpass);

        logger.info("-----");
        logger.info("Closing Application");
        App.Quit();
    } //throws IOException

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
    public void iOSTestRegister() {

        logger.info("-----");
        logger.info("Starting AppiumBased Tests");
        logger.info("-   -");

        App.SplashScreen();

        logger.info("Registering a user");

        App.Register();

    } //throws Exception

    @Test(dependsOnMethods = "iOSTestRegister")
    public void iOSTestAddPet() {

        logger.info("- - -");
        logger.info("Adding a pet");
        App.AddPet();

    } //throws Exception

    @Test(dependsOnMethods = "iOSTestAddPet")
    public void iOSTestAddCollar() {

        logger.info("- - -");
        logger.info("Adding the Collar");
        App.AddCollar();

    }

    @Test(dependsOnMethods = "iOSTestRegister")
    public void iOSTestCheckScreens() {

        logger.info("- - -");
        logger.info("Checking screens");
        App.ScreensShuffle();

    }

    @Test(dependsOnMethods = "iOSTestCheckScreens")
    public void iOSTestMap() {

        logger.info("- - -");
        logger.info("Checking Map");
        App.Map();

    }

    @Test(dependsOnMethods = "iOSTestCheckScreens")
    public void iOSUserProfile() {

        logger.info("- - -");
        logger.info("Checking User Profile");
        App.UserProfile();

    }

    @Test(dependsOnMethods = "iOSTestRegister")
    public void iOSUserLogin() throws Exception {

        logger.info("- - -");
        logger.info("Checking User Login:");
        logger.info("Restarting...");
        App.Quit();
        App.SetUp();
        logger.info("Login");
        App.SplashScreen();
        App.Login();

    }

    @Test(dependsOnMethods = "iOSTestAddPet")
    public void iOSEditPet() {

        logger.info("- - -");
        logger.info("Settings screen");
        logger.info("Pet Edit and Save");

        App.PetEdit();

        logger.info("Finished PetEdit");

    }

}