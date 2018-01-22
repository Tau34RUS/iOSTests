package iOSTests;

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
    DateFormat df;


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

    void SplashScreen1() {

        logger.info("Splash Screen Flipping");

        Assert.assertEquals("Больше никаких потерянных животных", driver.findElementByAccessibilityId("Больше никаких потерянных животных").getText());

    }

    void Register() {

        logger.info("Registering a user");

        Random login = new Random();

        String alphabet = "1234567890";
        Variables.userlogin = "";
        for (int i = 0; i < 16; i++) Variables.userlogin += login.nextInt(alphabet.length());
        Variables.userlogin = Variables.userlogin + "@test.user";

        Assert.assertEquals("Регистрация", driver.findElement(By.id("ru.averia.collars.stg:id/bt_register")).getText());

        driver.findElement(By.id("ru.averia.collars.stg:id/bt_register")).click();

        Assert.assertEquals("Регистрация", driver.findElement(By.id("ru.averia.collars.stg:id/tv_title")).getText());

        WebElement username = driver.findElement(By.id("ru.averia.collars.stg:id/et_email"));
        username.click();
        username.sendKeys(Variables.userlogin);

        WebElement password = driver.findElement(By.id("ru.averia.collars.stg:id/et_password"));
        password.click();
        password.sendKeys(Variables.userpass);

        driver.findElement(By.id("ru.averia.collars.stg:id/bt_register")).click();

        Assert.assertEquals("Добавить", driver.findElement(By.id("ru.averia.collars.stg:id/bt_add_pet")).getText());

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
        driver.findElement(By.id("ru.averia.collars.stg:id/maim_menu_action_pet")).click();
        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.collars.stg:id/tv_description_large")).getText());
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_add_pet")).click();
        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.collars.stg:id/tv_title")).getText());
        WebElement petname = driver.findElement(By.id("ru.averia.collars.stg:id/et_name"));
        petname.click();
        petname.sendKeys(Variables.petname);
        //TODO add gender random selection
        driver.navigate().back();
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

        driver.findElement(By.id("ru.averia.collars.stg:id/iv_pet_ava")).click();

        Assert.assertEquals("Выбрать источник", driver.findElement(By.id("android:id/title")).getText());

        List<WebElement> choiseslist = driver.findElements(By.className("android.widget.LinearLayout"));

        logger.info("List: " + choiseslist);

        choiseslist.get(1).click();


        //TODO check cameras on different brands - try -> catch

        //Asus
        driver.findElement(By.id("com.asus.camera:id/button_capture")).click();

        driver.findElement(By.id("com.asus.camera:id/button_used")).click();

        //Xiomi

        //HTC

        //LG


        driver.findElement(By.id("ru.averia.collars.stg:id/crop_image_menu_crop")).click();

        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.collars.stg:id/tv_title")).getText());
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

 /*     TODO dig out text input issue, Antons shitty code?
        WebElement breed = driver.findElement(By.id("ru.averia.collars.stg:id/et_breed_title"));
        breed.click();
        breed.sendKeys(Variables.breed);

        driver.findElements(By.className("android.widget.EditText")).get(0).sendKeys(Variables.breed);

        ru.averia.collars.stg:id/et_breed_title
 */
        //Appium Magic

        driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.View[1]")).click();
//        MobileElement el1 = (MobileElement) adriver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.View[1]");
//        el1.click();
//        adriver.navigate().back();

        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

        Assert.assertEquals("Возраст и дата рождения", driver.findElement(By.id("ru.averia.collars.stg:id/tv_cap")).getText());
        WebElement birthyear = driver.findElement(By.id("ru.averia.collars.stg:id/et_year"));
        birthyear.click();
        birthyear.sendKeys(Variables.birthyear);

        WebElement birthmonth = driver.findElement(By.id("ru.averia.collars.stg:id/et_month"));
        birthmonth.click();
        birthmonth.sendKeys(Variables.birthmonth);

        driver.navigate().back();
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

        WebElement petweight = driver.findElement(By.id("ru.averia.collars.stg:id/til_weight"));
        petweight.click();
        petweight.sendKeys(Variables.petweight);

        WebElement petwheight = driver.findElement(By.id("ru.averia.collars.stg:id/et_height"));
        petwheight.click();
        petwheight.sendKeys(Variables.petheight);

        driver.navigate().back();
        driver.findElement(By.id("ru.averia.collars.stg:id/bt_next")).click();

        Assert.assertEquals("Добавьте ошейник", driver.findElement(By.id("ru.averia.collars.stg:id/tv_add_collar_title1")).getText());
        Assert.assertEquals(Variables.petname, driver.findElement(By.id("ru.averia.collars.stg:id/tv_pet_name")).getText());
    }

    public void captureScreenShots() throws IOException {
        folder_name="screenshot";
        File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //create dir with given folder name
        new File(folder_name).mkdir();
        //coppy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + "LastFailScreenshot.png"));
    }

}
