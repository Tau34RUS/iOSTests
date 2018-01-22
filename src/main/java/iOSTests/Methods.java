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

        driver.findElementByAccessibilityId("Разрешить").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Добавить питомца\"]").getText());


    }

    void Login() {


        Assert.assertEquals("Войти", driver.findElement(By.id("ru.averia.collars.stg:id/bt_login")).getText());
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_login")).click();

        Assert.assertEquals("Войти", driver.findElement(By.id("ru.averia.collars.stg:id/tv_title")).getText());
        Assert.assertEquals("Войти", driver.findElement(By.id("ru.averia.collars.stg:id/bt_login")).getText());

        WebElement username = driver.findElement(By.id("ru.averia.collars.stg:id/et_email"));
        username.click();
        username.sendKeys(Variables.userlogin);

        WebElement password = driver.findElement(By.id("ru.averia.collars.stg:id/et_password"));
        password.click();
        password.sendKeys(Variables.userpass);

        driver.findElement(By.id("ru.averia.collars.stg:id/bt_login")).click();

        Assert.assertEquals("Добавить", driver.findElement(By.id("ru.averia.collars.stg:id/bt_add_pet")).getText());

    }

    void Quit() {
        driver.quit();
    }

    void Restart() throws Exception{
        Quit();
        SetUp();
    }


    void AddPet() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("Добавить питомца").getText());
        driver.findElementByAccessibilityId("Добавить").click();
        WebElement petname = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");
        petname.sendKeys(Variables.petname);
        driver.findElementByAccessibilityId("Девочка").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").click();
        driver.findElementByAccessibilityId("Сфотографировать").click();
        driver.findElementByAccessibilityId("PhotoCapture").click();
        driver.findElementByAccessibilityId("Исп. фото").click();
        driver.findElementByAccessibilityId("Готово").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("addPet nextButton").getText());
        driver.findElementByXPath("(//XCUIElementTypeSearchField[@name=\"Поиск\"])[1]").sendKeys("овчарка");
        driver.findElementByAccessibilityId("Азиатская овчарка").click();
        driver.findElementByAccessibilityId("addPet nextButton").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[5]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[1]").click();

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

        Assert.assertEquals("Возраст и дата рождения", driver.findElement(By.id("ru.averia.collars.stg:id/tv_cap")).getText());
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
        Assert.assertEquals("Возраст и дата рождения", driver.findElementByAccessibilityId("Питомцы  ￼").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();

        driver.findElementByAccessibilityId("Разрешить").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
        Assert.assertEquals("Возраст и дата рождения", driver.findElementByAccessibilityId("Редактировать профиль").getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[4]").click();
        Assert.assertEquals("Возраст и дата рождения", driver.findElementByAccessibilityId("Log").getText());






    }

}
