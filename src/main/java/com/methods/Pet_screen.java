package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

    /*public void addCollar() {

        Assert.assertEquals("Добавьте ошейник" ,driver.findElementById("ru.averia.tracker:id/tv_add_collar_title1").getText());
        driver.findElementById("ru.averia.tracker:id/tv_add_collar").click();

        driver.findElementById("ru.averia.tracker:id/bt_search").click();
        try{driver.findElementById("ru.averia.tracker:id/container_list").clear();}
        catch (NoSuchElementException e) {
            logger.info("No BLE Devices List");
        }

        try{driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.view.ViewGroup[2]/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]").clear();}
        catch (NoSuchElementException e) {
            logger.info("No BLE Devices Page");
        }

        driver.findElementById("ru.averia.tracker:id/iv_back").click();

    }*/

    //проверить новую реализацию (old public void AddCollar())
    //уточнить у Влада насчёт No BLE Devices List и No BLE Devices Page
    public void addCollar() {

        Assert.assertEquals("Добавьте ошейник", driver.findElementByAccessibilityId("Добавьте ошейник").getText());
        driver.findElementByAccessibilityId("Добавить").click();

        driver.findElementByAccessibilityId("Найти устройство").click();
        try{driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell").clear();}
        catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No BLE Devices List");
        }

        try{driver.findElementByXPath("//XCUIElementTypeTable[@name=\"Идет выполнение операции\"]").clear();}
        catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No BLE Devices Page");
        }

        driver.findElementByAccessibilityId("Добавить ошейник").click();
        driver.findElementByAccessibilityId("addPet close btn").click();
    }

    //проверить, что за элемент
    public void petEdit(String device) {

            swipeUpToElementId("ru.averia.tracker:id/tv_about");

    }

    //public void AddPet()
    public void addPet (String device){

        logger.info(device + ": Adding Pet");

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
        //проверить элемент expected
        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.tracker:id/tv_description_large")).getText());
        driver.findElementByAccessibilityId("Добавить").click();
        //нет проверки - добавить
        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.tracker:id/tv_title")).getText());

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").sendKeys(petname);

        //нет локатора (был на след.экране) - добавить
        driver.findElement(By.id("ru.averia.tracker:id/iv_pet_ava")).click();

        phonePhoto();

        driver.findElement(By.id("ru.averia.tracker:id/crop_image_menu_crop")).click();

        driver.navigate().back();
        driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();

        Assert.assertEquals("Добавить питомца", driver.findElement(By.id("ru.averia.tracker:id/tv_title")).getText());
        driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();
/*
        //Shitty Magic
        (new TouchAction(driver)).tap(462, 710).perform();
        (new TouchAction(driver)).tap(400, 400).perform();

        try {
            driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[4]").click();
        }
        catch (org.openqa.selenium.NoSuchElementException e) {
            logger.warn("No List Element 'Breed' Found!");
        }
*/
        driver.navigate().back();

        driver.findElement(By.id("ru.averia.tracker:id/bt_next")).click();

        //driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.view.View/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.View[2]").click();

        driver.findElement(By.xpath("//*[@text='Австралийская келпи']")).click();
        driver.findElementById("ru.averia.tracker:id/bt_next").click();

        driver.findElementById("ru.averia.tracker:id/til_age").sendKeys("2");
        driver.navigate().back();
        driver.findElementById("ru.averia.tracker:id/bt_next").click();

        driver.findElementById("ru.averia.tracker:id/til_weight").sendKeys("31");

        driver.findElementById("ru.averia.tracker:id/til_height").sendKeys("22");

        driver.navigate().back();

        driver.findElementById("ru.averia.tracker:id/bt_next").click();
    }

}