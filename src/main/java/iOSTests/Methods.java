package iOSTests;

import io.appium.java_client.TouchAction;
import org.apache.commons.io.FileUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static iOSTests.Variables.petage;

@SuppressWarnings("WeakerAccess")
public class Methods {


    AppiumDriver<WebElement> driver;
    protected static Logger logger;
    String folder_name;


    public void SetUp() throws Exception {

        logger = Logger.getLogger("MethodsTestLogger");
        /* appium setup */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("PlatformVersion", "11.3");
        capabilities.setCapability("deviceName", "iPhone 5s");
        capabilities.setCapability("udid", Constants.UDID);
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("app", Constants.appath);
        capabilities.setCapability("showXcodeLog", "true");
        capabilities.setCapability("XCUITest", "true");
        //capabilities.setCapability("bundleId", "com.averia.collar.test");

        /* appium driver setup */
        driver = new IOSDriver<WebElement>(new URL("http://127.0.0.1:4730/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(Constants.Timeout, TimeUnit.SECONDS);

        Variables.screensize = driver.manage().window().getSize();
        Variables.devicename = driver.getCapabilities().getCapability("deviceName").toString();
        logger.info("Screen size: " + Variables.screensize);
        logger.info("Device name: " + Variables.devicename);

    }

    public void Restart() throws Exception {

        logger.info("App Restart");
        Quit();
        SetUp();
    }

    public void SplashScreen() {

        logger.info("Splash Screen Flipping");

        Assert.assertEquals("Больше никаких потерянных животных", driver.findElementByAccessibilityId("Больше никаких потерянных животных").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Мониторинг активности вашего питомца", driver.findElementByAccessibilityId("Мониторинг активности вашего питомца").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Социальная сеть для владельцев собак", driver.findElementByAccessibilityId("Социальная сеть для владельцев собак").getText());

    }

    public void Register() {

        Random login = new Random();

        String alphabet = "1234567890";
        Variables.userlogin = "";
        for (int i = 0; i < 16; i++) Variables.userlogin += login.nextInt(alphabet.length());
        Variables.userlogin = Variables.userlogin + "@test.user";

        Assert.assertEquals("Регистрация", driver.findElementByAccessibilityId("Регистрация").getText());
        driver.findElementByAccessibilityId("Регистрация").click();

        WebElement username = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField");
        username.sendKeys(Variables.userlogin);

        //плохой вариант - селектор меняется в зависимости от auth password secureButton, но других нет
        WebElement password = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField");
        password.sendKeys(Variables.userpass);

        logger.info("Userlogin: " + Variables.userlogin);
        logger.info("Userpass:  " + Variables.userpass);

        driver.findElementByAccessibilityId("Продолжить").click();

        iOSAllowAccess();
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Добавить питомца\"]").getAttribute("name"));

        logger.info("Registration done");

    }

    public void Login() {

        Assert.assertEquals("Войти", driver.findElementByAccessibilityId("Войти").getText());
        driver.findElementByAccessibilityId("Войти").click();
        Assert.assertEquals("Войти", driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").getAttribute("name"));
        WebElement username = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField");
        username.click();
        username.sendKeys(Variables.userlogin);

        WebElement password = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField");
        password.click();
        password.sendKeys(Variables.userpass);
        driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").click();

        iOSAllowAccess();

        try {
            Assert.assertEquals("Добавить", driver.findElementByAccessibilityId("Добавить питомца").getText());
        } catch (Exception e) {
            e.getMessage();
            logger.info("No Add Pet button, already added?");

        }
    }

    public void Quit() {
        Sleep(15);
        driver.quit();
    }

    public void AddPet() {

        //logger.info("Adding new pet");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("Добавить питомца").getText());
        driver.findElementByAccessibilityId("Добавить").click();
        WebElement petname = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField");
        petname.sendKeys(Variables.petname);
        driver.findElementByAccessibilityId("Девочка").click();

        SwipeDown();
        driver.findElementByAccessibilityId("addPet addPhoto placeholder").click();

        PhonePhoto();

        driver.findElementByAccessibilityId("next screen button enabled").click();

        try {
            driver.findElementByAccessibilityId("Алабай").click();
        } catch (Exception e) {
            e.getMessage();
            logger.warn("No List Element 'Breed' Found!");
        }


        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField").sendKeys("овчарка");
        driver.findElementByAccessibilityId("Азиатская овчарка").click();
        driver.findElementByAccessibilityId("next screen button enabled").click();

        Assert.assertEquals("Возраст", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Возраст\"]").getAttribute("name"));

        //set age
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField").sendKeys(petage);
        new TouchAction(driver).tap(156, 492).perform();

        driver.findElementByAccessibilityId("next screen button enabled").click();


        Assert.assertEquals("Вес и высота", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Вес и высота\"]").getAttribute("name"));
        WebElement petweight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
        petweight.sendKeys(Variables.petweight);

        WebElement petwheight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
        petwheight.sendKeys(Variables.petheight);

        driver.findElementByAccessibilityId("finalize process enabled").click();
        Assert.assertEquals("Не сейчас", driver.findElementByAccessibilityId("Не сейчас").getText());
        driver.findElementByAccessibilityId("Не сейчас").click();

    }

    public void captureScreenShots() throws IOException {
        folder_name = "screenshot";
        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //create dir with given folder name
        new File(folder_name).mkdir();
        //coppy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "LastFailScreenshot.png"));
    }

    public void CheckScreens() {

        Assert.assertEquals("Питомцы  ￼", driver.findElementByAccessibilityId("Питомцы  ￼").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();

        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        } catch (Exception e) {
            e.getMessage();
            logger.info("No access request found");
        }

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        Assert.assertEquals("Редактировать профиль", driver.findElementByAccessibilityId("Редактировать профиль").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[4]").click();
        Assert.assertEquals("Log", driver.findElementByAccessibilityId("Log").getText());
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();

    }

    public void Sleep(Integer seconds) {

        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iOSAllowAccess() {
        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No Permissions requested");
        }
    }

    public void AddCollar() {

        Assert.assertEquals("Добавьте ошейник", driver.findElementByAccessibilityId("Добавьте ошейник").getText());
        driver.findElementByAccessibilityId("Добавить").click();

        driver.findElementByAccessibilityId("Найти устройство").click();
        try{driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").clear();}
        catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No BLE Devices List");
        }

        try{driver.findElementByXPath("ic_progress_indicator").clear();}
        catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No BLE Devices Page");
        }

        driver.findElementByAccessibilityId("Добавить ошейник").click();
        Assert.assertEquals("Добавить ошейник", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Добавить ошейник\"]").getAttribute("name"));
        driver.findElementByAccessibilityId("addPet dismiss btn").click();
        Assert.assertEquals("Добавьте ошейник", driver.findElementByAccessibilityId("Добавьте ошейник").getText());

    }

    public void ScreensShuffle() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();
        iOSAllowAccess();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();

    }

    public void Map() {
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();
        driver.findElementByAccessibilityId("petsMap petsCenter btn").click();
        driver.findElementByAccessibilityId("petsMap userCenter btn").click();
        driver.findElementByAccessibilityId("petsMap nearbyPets btn normal").click();

    }

    public void UserProfile() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeOther").clear();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeOther[1]").clear();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeOther[2]").clear();
        driver.findElementByAccessibilityId("Редактировать профиль").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[3]/XCUIElementTypeTextField").sendKeys("Tester");
        driver.hideKeyboard();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[5]/XCUIElementTypeTextField").sendKeys("averia@test.com");
        driver.hideKeyboard();

       String Photo = "//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeOther";
       logger.info("Scroll down");
       ScrollDown(Photo);
       //SwipeDown();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeOther").click();

        PhonePhoto();

        logger.info("Saving user profile changes");
        try {driver.findElementByAccessibilityId("userProfileEditing save").click();}
        catch (Exception e) {
            e.getMessage();
            logger.info("Already saved?");
        }
    }

    public void PetEdit() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();

        driver.findElementByAccessibilityId("Подробнее").click();
        String EditButton = "Редактировать профиль";

        logger.info("Scroll up");
        ScrollUp(EditButton);
        //SwipeUp();

        driver.findElementByAccessibilityId("Редактировать профиль").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeTextField").sendKeys("1");

        driver.hideKeyboard();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]/XCUIElementTypeTextField").clear();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]/XCUIElementTypeTextField").sendKeys("33");

        driver.findElementByAccessibilityId("Toolbar Done Button").click();

        String target = "//XCUIElementTypeApplication[@name=\\\"Averia Collar\\\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[7]/XCUIElementTypeTextField";
        ScrollUp(target);

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[7]/XCUIElementTypeTextField").clear();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[7]/XCUIElementTypeTextField").sendKeys("33");

        driver.findElementByAccessibilityId("userProfileEditing save").click();

        String LastPoint = "//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[9]";

        logger.info("Scroll up");
        ScrollUp(LastPoint);

        driver.findElementByAccessibilityId("Основной хозяин").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
    }

    public void SwipeUp() {

        int starty = (int) (Variables.screensize.height * 0.50);
        int endy = (int) (Variables.screensize.height * 0.20);
        int startx = Variables.screensize.width / 2;
        driver.swipe(startx,starty,startx,endy,300);
        driver.swipe(startx,starty,startx,endy,300);
        Sleep(1);
    }

    public void ScrollUp(String elementXpath){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "up");
        scrollObject.put("xpath", elementXpath);
        js.executeScript("mobile: swipe", scrollObject);
    }

    public void ScrollDown(String elementXpath){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "down");
        scrollObject.put("xpath", elementXpath);
        js.executeScript("mobile: swipe", scrollObject);
    }

    public void SwipeDown() {

        int starty = (int) (Variables.screensize.height * 0.50);
        int endy = (int) (Variables.screensize.height * 0.80);
        int startx = Variables.screensize.width / 2;
        driver.swipe(startx,starty,startx,endy,300);
        driver.swipe(startx,starty,startx,endy,300);
        Sleep(1);
    }

    public void PhonePhoto () {
        driver.findElementByAccessibilityId("Сфотографировать").click();
        iOSAllowAccess();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther").click();
        driver.findElementByAccessibilityId("Исп. фото").click();
        driver.findElementByAccessibilityId("Готово").click();
    }

    public void Logout() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        String logout = "Выйти";
        ScrollUp(logout);
        driver.findElementByAccessibilityId("Выйти").click();
        driver.findElementByAccessibilityId("Да").click();
        driver.findElementByAccessibilityId("Больше никаких потерянных животных").clear();

    }

    public void DeletePet() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        driver.findElementByAccessibilityId("Подробнее").click();
        String deletePet = "Удалить питомца";
        ScrollUp(deletePet);
        driver.findElementByAccessibilityId("Удалить питомца").click();
        driver.findElementByAccessibilityId("Да").click();

    }

    public void ShowAppStats() throws IOException {

        logger.info(Runtime.getRuntime().exec("adb shell \"dumpsys meminfo 'ru.averia.tracker'| grep TOTAL \"").getOutputStream());

    }
}