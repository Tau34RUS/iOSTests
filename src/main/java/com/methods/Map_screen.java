package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static com.vars.consts.Timeout;

public class Map_screen extends Common {

    protected Logger logger;

    //проверить
    public Map_screen(AppiumDriver<MobileElement> driver)  {
        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Timeout, TimeUnit.SECONDS), this);
    }

    //public void Map()
    public void mapActions() {

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar/XCUIElementTypeButton[2]").click();
        driver.findElementByAccessibilityId("petsMap petsCenter btn").click();
        driver.findElementByAccessibilityId("petsMap userCenter btn").click();
        driver.findElementByAccessibilityId("petsMap nearbyPets btn normal").click();

    }

}