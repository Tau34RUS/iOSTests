package com.tests;

/* Smoke Tests */

import com.methods.Common;
import com.methods.Main_screen;
import com.methods.Profile_screen;
import com.methods.Start_screen;
import com.utils.GetDeviceInfo;
import com.utils.Screenshot;
import com.vars.consts;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.vars.consts.*;

public class Smoke {

    Logger logger = Logger.getLogger("iOSTestLogger");

    String port;
    public String device;
    public String testName;

    public Start_screen start;
    public Screenshot screenshot;
    public GetDeviceInfo deviceinfo;
    public Common common;
    public Profile_screen profile_screen;
    public Main_screen main_screen;

    static AppiumDriver<WebElement> driver;

    DesiredCapabilities caps = new DesiredCapabilities();

    @Parameters({"server_port","device"})
    public Smoke(@Optional("4723") String port, @Optional("default") String device)
    {
        this.port = port;
        this.device = device;
    }

    public void StartUp()
    {

        caps.setCapability("platformName", "iOS");
        caps.setCapability("PlatformVersion", "11.3");
        caps.setCapability("deviceName", "iPhone 6");
        caps.setCapability("udid", UDID1);
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("app", appath);
        caps.setCapability("showXcodeLog", "true");
        caps.setCapability("XCUITest", "true");
        //caps.setCapability("bundleId", "com.averia.collar.test");


        try {
            driver = new IOSDriver<WebElement>(new URL("http://127.0.0.1:4730/wd/hub"), caps);
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

        driver.manage().timeouts().implicitlyWait(consts.Timeout, TimeUnit.SECONDS);

    }

    public void Exit() {

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
    void Login()
    {

        start.SplashScreen();
        start.Login_old(device);

    }

    @Test(dependsOnMethods = "Login")
    void MainActivity()
    {

        common.ScreensShuffle();

    }

    @Test(dependsOnMethods = "MainActivity")
    void ProfileScreenElements()
    {

        common.gotoProfileScreen(device);
        profile_screen.userProfileView(device);

    }

    @Test(dependsOnMethods = "Login")
    void MainScreenElements()
    {

        common.gotoMainScreen(device);
        main_screen.checkScreen(device);
    }

}
