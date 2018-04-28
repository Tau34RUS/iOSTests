package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static com.vars.consts.Timeout;

public class Socials extends Common {

    protected Logger logger;

    public Socials(AppiumDriver<WebElement> driver)  {
        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Timeout, TimeUnit.SECONDS), this);
    }

    public void share_Achievement(String device) {

        scrollUp("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[7]");

        logger.info(device + ": Sharing achievement");

        driver.findElementByAccessibilityId("Добавлен ошейник").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[2]").clear();
        driver.findElementByAccessibilityId("Поделиться").click();
        iOSAllowAccess();
        driver.findElementByAccessibilityId("Далее").click();
        driver.findElementByAccessibilityId("Поделиться").click();
        driver.findElementByAccessibilityId("Отменить").click();
        driver.findElementByAccessibilityId("Библиотека").click();
        driver.findElementByAccessibilityId("addPet close btn").click();
        driver.findElementByAccessibilityId("addPet close btn").click();

    }

}
