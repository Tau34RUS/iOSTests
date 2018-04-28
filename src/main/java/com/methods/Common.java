package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

import static com.vars.consts.*;
import static com.vars.vars.devicename;
import static com.vars.vars.petname;
import static com.vars.vars.screensize;

public class Common {

    public AppiumDriver driver;

    Logger logger = Logger.getLogger(Common.class);

    public Common(AppiumDriver<WebElement> driver) {
        this.driver = driver;
    }

    public void iOSAllowAccess() {
        try {
            driver.findElementByAccessibilityId("Разрешить").click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.info("No Permissions requested");
        }
    }

    public void sleep (Integer seconds) {

        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void swipeUp() {

        logger.info("Swipe Up");
        int starty = (int) (screensize.height * 0.50);
        int endy = (int) (screensize.height * 0.20);
        int startx = screensize.width / 2;
        driver.swipe(startx,starty,startx,endy,300);
        driver.swipe(startx,starty,startx,endy,300);
        sleep(2);

    }

    public void swipeDown() {

        logger.info("Swipe Down");
        int starty = (int) (screensize.height * 0.20);
        int endy = (int) (screensize.height * 0.70);
        int startx = screensize.width / 2;
        driver.swipe(startx,starty,startx,endy,300);
        driver.swipe(startx,starty,startx,endy,300);
        sleep(2);

    }

    public void gotoMainScreen(String device) {

        logger.info(device + ": GOTO Main Screen");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();

    }

    public void gotoMapScreen(String device) {

        logger.info(device + ": GOTO Map Screen");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();

    }

    public void gotoProfileScreen(String device) {

        logger.info(device + ": GOTO Profile Screen");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();

    }

    public void ScreensShuffle() {

        String tabBar = "//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar";
        String navBar = "//XCUIElementTypeApplication[@name=\\\"Averia Collar\\\"]/XCUIElementTypeWindow[3]/XCUIElementTypeStatusBar/XCUIElementTypeOther[2]";

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();
        iOSAllowAccess();
        sleep(1);
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[3]").click();
            sleep(1);
            scrollUp(tabBar);
            scrollDown(navBar);
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[1]").click();
            sleep(1);
            scrollUp(tabBar);
            scrollDown(navBar);
    }

    public void phonePhoto () {
        driver.findElementByAccessibilityId("Сфотографировать").click();
        iOSAllowAccess();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther").click();
        driver.findElementByAccessibilityId("Исп. фото").click();
        driver.findElementByAccessibilityId("Готово").click();
    }

    public void swipeUpToElementId(String elementId) {

        boolean isFoundTheElement = driver.findElements(By.id(elementId)).size() > 0;
        while (!isFoundTheElement){
            swipeUp();
            isFoundTheElement  = driver.findElements(By.id(elementId)).size() > 0;
        }

    }

    public void swipeDownToElementId(String elementId){

        boolean isFoundTheElement = driver.findElements(By.id(elementId)).size() > 0;
        while (!isFoundTheElement){
            swipeDown();
            isFoundTheElement  = driver.findElements(By.id(elementId)).size() > 0;
        }

    }

    public void scrollUp(String elementXpath){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "up");
        scrollObject.put("xpath", elementXpath);
        js.executeScript("mobile: swipe", scrollObject);
    }

    public void scrollDown(String elementXpath){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap scrollObject = new HashMap();
        scrollObject.put("direction", "down");
        scrollObject.put("xpath", elementXpath);
        js.executeScript("mobile: swipe", scrollObject);
    }

    public boolean isElementPresent(By by){
        try {
            driver.findElements(by);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

}
