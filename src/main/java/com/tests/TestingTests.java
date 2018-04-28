package com.tests;

/* Smoke Tests */

import com.methods.*;
import com.utils.GetDeviceInfo;
import com.utils.Screenshot;
import com.vars.consts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.vars.consts.*;

public class TestingTests {

    Logger logger = Logger.getLogger("AndroidTestLogger");

    String port;
    public String device;
    public String testName;

    public Start_screen start;
    public Screenshot screenshot;
    public GetDeviceInfo deviceinfo;
    public Common common;
    public Profile_screen profile_screen;
    public Main_screen main_screen;
    public Pet_screen pet_screen;
    public Socials social;
    static AppiumDriver<WebElement> driver;

    DesiredCapabilities caps = new DesiredCapabilities();

    @Parameters({"server_port","device"})
    public TestingTests(@Optional("4723") String port, @Optional("default") String device)
    {
        this.port = port;
        this.device = device;
    }

    public void StartUp()
    {

        logger.info(device + ": Starting app");

        caps.setCapability("platformName", "iOS");
        caps.setCapability("PlatformVersion", "11.3");
        caps.setCapability("deviceName", "iPhone 5s");
        caps.setCapability("udid", UDID);
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("app", appath);
        caps.setCapability("showXcodeLog", "true");
        caps.setCapability("XCUITest", "true");
        //caps.setCapability("bundleId", "com.averia.collar.test");

        try {
            driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), caps);
            //Thread.sleep(1000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Adding all needed methods and utils
        start = new Start_screen(driver);
        screenshot = new Screenshot(driver);
        deviceinfo = new GetDeviceInfo(driver);
        common = new Common(driver);
        profile_screen = new Profile_screen(driver);
        main_screen = new Main_screen(driver);
        pet_screen = new Pet_screen(driver);
        social = new Socials(driver);

        driver.manage().timeouts().implicitlyWait(consts.Timeout, TimeUnit.SECONDS);

        logger.info(device + ": App launched");

    }

    public void Exit() {

        logger.info(device + ": Closing app");
        driver.quit();

    }

    @BeforeTest(alwaysRun = true)
    void BeforeSuite()
    {

        logger.info("-----");
        logger.info(device+": "+"Initial Settings and App Startup");

        StartUp();

        logger.info(device+": "+"Settings Applied");

        deviceinfo.getDeviceInfo(device);

    }

    @AfterTest
    void AfterSuite() {
        Exit();
    }

    @AfterMethod
    void afterMethod(ITestResult result)
    {

        testName = result.getName();

        try
        {
            if(result.getStatus() == ITestResult.SUCCESS)
            {
                logger.info(device + ": Passed " + testName);
            }

            else if(result.getStatus() == ITestResult.FAILURE)
            {
                logger.info(device + ": Failed " + testName);
                screenshot.captureScreenShots(device, testName);
            }

            else if(result.getStatus() == ITestResult.SKIP )
            {
                logger.info(device + ": Skiped " + testName);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
        void Register()
        {

            start.SplashScreen();
            start.Register(device);


        }
/*    @Test(dependsOnMethods = "Register")
        void Login()
        {
            Exit();
            StartUp();
            start.SplashScreen();
            start.Login(device);

        }*/
    @Test(dependsOnMethods = "Register")
        void AddPet()
        {

            pet_screen.addPet(device);
            common.gotoProfileScreen(device);
            pet_screen.petEdit(device);

        }
/*
    @Test(dependsOnMethods = "AddPet")
        void MainActivity()
        {

            common.ScreensShuffle();
            common.gotoMainScreen(device);


        }

    @Test(dependsOnMethods = "AddPet")
        void UserProfile()
        {

            common.gotoProfileScreen(device);
            profile_screen.userProfileEdit(device);
            common.gotoMainScreen(device);

        }

    @Test(dependsOnMethods = "UserProfile")
        void PetProfile()
        {

            common.gotoProfileScreen(device);
            pet_screen.petEdit(device);

        }

    @Test(dependsOnMethods = "PetProfile")
        void Restart(){

            Exit();
            StartUp();

        }

    @Test(dependsOnMethods = "Restart")
    void LoginExistingUser(){

        start.SplashScreen();
        start.Login_old(device);

    }

    @Test(dependsOnMethods = "LoginExistingUser")
    void ChekingStatistic(){

        common.gotoMainScreen(device);
        main_screen.walkStats(device);

    }


    @Test(dependsOnMethods = "LoginExistingUser")
    void Achievements(){

        common.gotoMainScreen(device);
        social.share_Achievement(device);

    } */

}
