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
import static com.vars.vars.petname;

public class Pet_screen extends Common{

    protected Logger logger;

    //проверить
    public Pet_screen(AppiumDriver<MobileElement> driver)  {
        super(driver);
        logger = Logger.getLogger("AndroidTestLogger");
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

    //проверить, что за элемент
    public void petEdit(String device) {

            //--определение геолокации в навбаре?--swipeUpToElementId("ru.averia.tracker:id/tv_about");

    }

    //public void AddPet()
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
        /*stop*/

        //driver.navigate().back();
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
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField").sendKeys(vars.petage);
        new TouchAction(driver).tap(156, 492).perform();

        driver.findElementByAccessibilityId("next screen button enabled").click();


        Assert.assertEquals("Вес и высота", driver.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Вес и высота\"]").getAttribute("name"));
        WebElement petweight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
        petweight.sendKeys(vars.petweight);

        WebElement petwheight = driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeTextField");
        petwheight.sendKeys(vars.petheight);

        driver.findElementByAccessibilityId("finalize process enabled").click();
        Assert.assertEquals("Не сейчас", driver.findElementByAccessibilityId("Не сейчас").getText());
        driver.findElementByAccessibilityId("Не сейчас").click();
        /*driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();

        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.tracker:id/tv_title")).getText());
        driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();

        driver.navigate().back();

        driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();

        driver.findElement(By.xpath("//*[@text='Австралийская келпи']")).click();
        driver.findElementById("ru.averia.tracker:id/bt_next").click();

        driver.findElementById("ru.averia.tracker:id/til_age").sendKeys("2");
        driver.navigate().back();
        driver.findElementById("ru.averia.tracker:id/bt_next").click();

        driver.findElementById("ru.averia.tracker:id/til_weight").sendKeys("31");

        driver.findElementById("ru.averia.tracker:id/til_height").sendKeys("22");

        driver.navigate().back();

        driver.findElementById("ru.averia.tracker:id/bt_next").click();*/

    }

}