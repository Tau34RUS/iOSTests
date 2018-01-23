package iOSTests;

import io.appium.java_client.TouchAction;
import org.apache.commons.io.FileUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class Methods {



    AppiumDriver<WebElement> driver;
    protected static Logger logger;
    String folder_name;


    void SetUp() throws Exception {

        logger = Logger.getLogger("MethodsTestLogger");

        /* appium setup */
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("PlatformVersion", "11.2.2");
        capabilities.setCapability("deviceName", "iPhone 6");
        capabilities.setCapability("udid", Constants.UDID);
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("app", Constants.appath);
        capabilities.setCapability("showXcodeLog", "true");
        capabilities.setCapability("XCUITest", "true");

        /* appium driver setup */
        driver = new IOSDriver<WebElement>(new URL("http://127.0.0.1:4730/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(Constants.Timeout, TimeUnit.SECONDS);
        Variables.screensize = driver.manage().window().getSize();
        Variables.devicename = driver.getCapabilities().getCapability("deviceName").toString();
        logger.info("Screen size: " + Variables.screensize);
        logger.info("Device name: " + Variables.devicename);

    }

    void SplashScreen() {

        logger.info("Splash Screen Flipping");

        Assert.assertEquals("Больше никаких потерянных животных", driver.findElementByAccessibilityId("Больше никаких потерянных животных").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Мониторинг активности вашего питомца", driver.findElementByAccessibilityId("Мониторинг активности вашего питомца").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Социальная сеть для владельцев собак", driver.findElementByAccessibilityId("Социальная сеть для владельцев собак").getText());

    }

    void Register() {

        logger.info("Registering a user");

        Random login = new Random();

        String alphabet = "1234567890";
        Variables.userlogin = "";
        for (int i = 0; i < 16; i++) Variables.userlogin += login.nextInt(alphabet.length());
        Variables.userlogin = Variables.userlogin + "@test.user";

        driver.findElementByAccessibilityId("Регистрация").click();

        WebElement username = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]");
        username.sendKeys(Variables.userlogin);

        WebElement password = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[3]/XCUIElementTypeSecureTextField");
        password.click();
        password.sendKeys(Variables.userpass);

        driver.findElementByAccessibilityId("Продолжить").click();

        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        }
        catch (Exception e){
            e.getMessage();
            logger.info("No access request found");
        }
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Добавить питомца\"]").getText());


    }

    void Login() {

        driver.findElementByAccessibilityId("Войти").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField").sendKeys(Variables.userlogin);
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField").sendKeys(Variables.userpass);
        driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").click();
        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        }
        catch (Exception e){
            e.getMessage();
            logger.info("No access request found");
        }

    }

    void Quit() {
        driver.quit();
    }

    void Restart() throws Exception{

        logger.info("App Restart");
        Quit();
        SetUp();
    }


    void AddPet() {

        logger.info("Adding new pet");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("Добавить питомца").getText());
        driver.findElementByAccessibilityId("Добавить").click();
        WebElement petname = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");
        petname.sendKeys(Variables.petname);
        driver.findElementByAccessibilityId("Девочка").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").click();

        driver.findElementByAccessibilityId("Сфотографировать").click();
        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        }
        catch (Exception e){
            e.getMessage();
            logger.info("No access request found");
        }
        driver.findElementByAccessibilityId("PhotoCapture").click();
        driver.findElementByAccessibilityId("Исп. фото").click();
        driver.findElementByAccessibilityId("Готово").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        Assert.assertEquals("addPet nextButton", driver.findElementByAccessibilityId("addPet nextButton").getText());
        driver.findElementByXPath("(//XCUIElementTypeSearchField[@name=\"Поиск\"])[1]").sendKeys("овчарка");
        driver.findElementByAccessibilityId("Азиатская овчарка").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeTextField").click();

        //Appium Magic
        TouchAction ta1 = new TouchAction(driver);
                ta1.press(273, 587);
                ta1.moveTo(-3,-61);
                ta1.release();
                ta1.perform();

        TouchAction ta2 = new TouchAction(driver);
                ta2.press(274, 534);
                ta2.moveTo(1,39);
                ta2.release();
                ta2.perform();

        //End of Magic

        driver.findElementByAccessibilityId("addPet nextButton").click();

        WebElement birthyear = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField");
        birthyear.sendKeys(Variables.birthyear);

        WebElement birthmonth = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeTextField");
        birthmonth.sendKeys(Variables.birthmonth);

        driver.findElementByAccessibilityId("addPet doneButton").click();

    }

    public void captureScreenShots() throws IOException {
        folder_name="screenshot";
        File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //create dir with given folder name
        new File(folder_name).mkdir();
        //coppy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "LastFailScreenshot.png"));
    }

    public void CheckScreens(){

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        Assert.assertEquals("Питомцы  ￼", driver.findElementByAccessibilityId("Питомцы  ￼").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();

        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        }
        catch (Exception e){
            e.getMessage();
            logger.info("No access request found");
        }

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        Assert.assertEquals("Редактировать профиль", driver.findElementByAccessibilityId("Редактировать профиль").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[4]").click();
        Assert.assertEquals("Log", driver.findElementByAccessibilityId("Log").getText());

    }

}
