package com.methods;

import io.appium.java_client.*;
import io.appium.java_client.pagefactory.*;
import org.apache.log4j.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.testng.*;

import java.util.*;
import java.util.concurrent.*;

import static com.vars.consts.*;
import static com.vars.vars.*;

public class Start_screen extends Common{

    protected Logger logger;

    public Start_screen(AppiumDriver<WebElement> driver)  {
        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Timeout, TimeUnit.SECONDS), this);
    }

    public void SplashScreen() {

        elementVisibilityWait(By.id("Больше никаких потерянных животных"));
        Assert.assertEquals("Больше никаких потерянных животных", driver.findElementByAccessibilityId("Больше никаких потерянных животных").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Мониторинг активности вашего питомца", driver.findElementByAccessibilityId("Мониторинг активности вашего питомца").getText());
        driver.findElementByAccessibilityId("Далее").click();
        Assert.assertEquals("Социальная сеть для владельцев собак", driver.findElementByAccessibilityId("Социальная сеть для владельцев собак").getText());

    }

    public void Register(String device) {

        logger.info(device + ": Starting Registration");

        Random login = new Random();

        String alphabet = "1234567890";
        userlogin = "";
        for (int i = 0; i < 16; i++) userlogin += login.nextInt(alphabet.length());
        userlogin = userlogin + "@test.user";

        Assert.assertEquals("Регистрация",driver.findElementByAccessibilityId("Регистрация").getText());

        driver.findElementByAccessibilityId("Регистрация").click();

        elementPresenceWait(By.id("Продолжить"));

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField").sendKeys(userlogin);

        //плохой вариант - селектор меняется в зависимости от auth password secureButton, но других нет
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField").sendKeys(userpass);

        logger.info(device + ": Userlogin: " + userlogin);
        logger.info(device + ": Userpass:  " + userpass);

        driver.findElementByAccessibilityId("Продолжить").click();

        iOSAllowAccess();
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeStaticText[@name=\"Добавить питомца\"]").getAttribute("name"));

        logger.info(device + ": Registration done");

    }

    public void Login(String device) {

        logger.info(device + ": Logging with just registered user");

        Assert.assertEquals("Войти", driver.findElementByAccessibilityId("Войти").getText());
        driver.findElementByAccessibilityId("Войти").click();

        Assert.assertEquals("Войти", driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").getAttribute("name"));

        elementPresenceWait(By.xpath("//XCUIElementTypeButton[@name=\"Войти\"]"));
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField").sendKeys(userlogin);
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField").sendKeys(userpass);
        driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").click();

        iOSAllowAccess();

        try {
            Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("Добавить питомца").getText());
        }catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info(device + ": No Add Pet button, already added?");
        }

            driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").clear();

    }

    public void Login_old(String device) {

        logger.info(device + ": Logging with existing user");

        Assert.assertEquals("Войти", driver.findElementByAccessibilityId("Войти").getText());
        driver.findElementByAccessibilityId("Войти").click();

        Assert.assertEquals("Войти", driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").getAttribute("name"));

        elementPresenceWait(By.xpath("//XCUIElementTypeButton[@name=\"Войти\"]"));
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeTextField").sendKeys(old_user);
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeSecureTextField").sendKeys(old_pass);
        driver.findElementByXPath("//XCUIElementTypeButton[@name=\"Войти\"]").click();
        iOSAllowAccess();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").clear();

    }

}