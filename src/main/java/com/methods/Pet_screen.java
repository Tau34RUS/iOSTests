package com.methods;

import com.vars.vars;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.vars.consts.Timeout;
import static com.vars.vars.*;

public class Pet_screen extends Common{

    protected Logger logger;

    public Pet_screen(AppiumDriver<WebElement> driver)  {
        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Timeout, TimeUnit.SECONDS), this);
    }

    public void addCollar() {

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

    public void petEdit(String device) {

        scrollUp("//XCUIElementTypeStaticText[@name="+ vars.petname + "]");

    }

    public void addPet (String device){

        logger.info(device + ": Adding Pet");

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByAccessibilityId("Добавить питомца").getText());
        driver.findElementByAccessibilityId("Добавить").click();
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Добавить питомца\"]").getAttribute("name"));

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField").sendKeys(vars.petname);
        swipeDown();
        driver.findElementByAccessibilityId("addPet addPhoto placeholder").click();

        phonePhoto();

        driver.findElementByAccessibilityId("next screen button enabled").click();

        try {
            driver.findElementByAccessibilityId("Алабай").clear();
        } catch (Exception e) {
            e.getMessage();
            logger.warn("No List Element 'Breed' Found!");
        }

        driver.findElementByXPath("//*[@type='XCUIElementTypeTextField']").sendKeys("овчарка");
        sleep(5);
        driver.findElementByAccessibilityId("Азиатская овчарка").click();

        driver.findElementByAccessibilityId("next screen button enabled").click();

        Assert.assertEquals("Возраст", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Возраст\"]").getAttribute("name"));

        //set age
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField").sendKeys(vars.petage);
        sleep(3);

        driver.findElementByAccessibilityId("next screen button enabled").click();

        Assert.assertEquals("Вес и высота", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Вес и высота\"]").getAttribute("name"));
        WebElement petweight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
        petweight.sendKeys(vars.petweight);

        WebElement petheight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
        petheight.sendKeys(vars.petheight);

        driver.findElementByAccessibilityId("finalize process enabled").click();
        sleep(10);
        Assert.assertEquals("Не сейчас", driver.findElementByAccessibilityId("Не сейчас").getText());
        driver.findElementByAccessibilityId("Не сейчас").click();

    }


    public void addPetProfileScreen (String device){

        logger.info(device + ": Adding Temp Pet");

        //+Добавить питомца

        //checking for existing temp pet

        if (isElementPresent(By.id(temp_petname))) {

            logger.error(device + ": Temp Pet already present!");
            
            driver.findElementByAccessibilityId(temp_petname).click();

            scrollUp("Удалить питомца");

            driver.findElementByAccessibilityId("Удалиь питомца").click();

            driver.findElementByAccessibilityId("Удалить").click();
            sleep (5);
        }


        scrollUp("Добавить питомца");
        //adding new one - добавляем нового - Добавить питомца
        driver.findElementByAccessibilityId("Добавить питомца").click();
        //повтор кейса на добавление питомца
        Assert.assertEquals("Добавить питомца", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Добавить питомца\"]").getAttribute("name"));

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField").sendKeys(temp_petname);

        driver.findElementByAccessibilityId("next screen button enabled").click();

        driver.findElementByAccessibilityId("Австралийская келпи").click();

        driver.findElementByAccessibilityId("next screen button enabled").click();

        logger.info (" => Strings:");
        logger.info ("Age: " + petage);
        logger.info ("Weight: " + petweight);
        logger.info ("Height: " + petheight);

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField").sendKeys(vars.petage);
        sleep(3);

        driver.findElementByAccessibilityId("next screen button enabled").click();

        WebElement petweight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
        petweight.sendKeys(vars.petweight);

        WebElement petheight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
        petheight.sendKeys(vars.petheight);

        driver.findElementByAccessibilityId("finalize process enabled").click();
        sleep(7);

        if (isElementPresent(By.id(temp_petname))) { logger.error(device + ": Temp Pet Added!"); }

    }

    public void deletePetProfileScreen (String device){

        logger.info(device + ": Deleting Temp Pet");

        //checking for existing temp pet and deleting

        driver.findElementByAccessibilityId(temp_petname).click();
        scrollUp("Удалить питомца");
        driver.findElementByAccessibilityId("Удалить питомца").click();
        driver.findElementByAccessibilityId("Да").click();
        sleep (5);
        if (isElementPresent(By.id(temp_petname))) { logger.error(device + ": Temp Pet Still Present!"); }

    }
}