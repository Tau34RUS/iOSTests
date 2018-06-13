package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Main_screen extends Common {

    protected Logger logger;

    public Main_screen(AppiumDriver<WebElement> driver) {

        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    public void checkScreen(String device) {
        String tabBar = "//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeTabBar";
        String navBar = "//XCUIElementTypeApplication[@name=\\\"Averia Collar\\\"]/XCUIElementTypeWindow[3]/XCUIElementTypeStatusBar/XCUIElementTypeOther[2]";

        logger.info(device + ": Checking Main screen objects:");

        logger.info(device + ": - Top bar elements");

        driver.findElement(By.xpath("//XCUIElementTypeButton[contains(@name,'%')]")).clear();

        // - date on main screen: open date picker >> close >> previouse date >> naxt date
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton[2]").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton[2]").click();
        driver.findElementByAccessibilityId("pets days previous").click();
        driver.findElementByAccessibilityId("pets days next").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").clear();//status

        logger.info(device + ": - Tracks and Achievements");

        logger.info("scrollUp");

        switch (device) {
            case "iPhone Remi": {
                swipeUp();
                break;
            }
            case "iPhone 6":{
                scrollUp(tabBar);
                break;
            }
        }
        driver.findElementByAccessibilityId("Недавние прогулки").click(); //Недавние прогулки
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").click();//первый трек
        driver.findElementByAccessibilityId("Все прогулки").click();
        driver.navigate().back();

        scrollUp(tabBar);

        driver.findElementByAccessibilityId("Добавлен ошейник").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[2]").clear();//картинка
        driver.findElementByAccessibilityId("addPet close btn").click();

        logger.info(device + ": - Stats");
        scrollDown(navBar);
        driver.findElementByAccessibilityId("Шаг").click();
        driver.findElementsByXPath("//XCUIElementTypeOther[@name=\"Ходьба\"]").clear();
        driver.navigate().back();
        driver.findElementByAccessibilityId("Бег").click();
        driver.findElementsByXPath("//XCUIElementTypeOther[@name=\"Бег и быстрый бег\"]").clear();
        driver.navigate().back();
        driver.findElementByAccessibilityId("Отдых").click();
        driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Отдых\"]").clear();
        driver.navigate().back();
        driver.findElementByAccessibilityId("Расход калорий").click();
        driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Расход калорий\"]").clear();
        driver.navigate().back();

    }

    public void walkStats(String device) {

        String currentPetName = driver.findElementByXPath("//*[@type='XCUIElementTypeNavigationBar']").getAttribute("name");

        logger.info(device + ": Checking Main screen stats");
        swipeDown();
        driver.findElementByAccessibilityId("Шаг").click();

        elementVisibilityWait(By.xpath("//XCUIElementTypeOther[@name=\"Ходьба\"]"));
        Assert.assertEquals("Ходьба", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Ходьба\"]").getAttribute("name"));
        if (isElementPresent(By.id("Активности за сегодня нет"))) {
            logger.info("No stats");
        } else {
            logger.info("Stats not null");
        }

        driver.findElementByAccessibilityId(currentPetName).click();

        driver.findElementByAccessibilityId("Бег").click();

        elementVisibilityWait(By.xpath("//XCUIElementTypeOther[@name=\"Бег и быстрый бег\"]"));
        Assert.assertEquals("Бег и быстрый бег", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Бег и быстрый бег\"]").getAttribute("name"));
        if (isElementPresent(By.id("Активности за сегодня нет"))) {
            logger.info("No stats");
        } else {
            logger.info("Stats not null");
        }

        driver.findElementByAccessibilityId(currentPetName).click();

        driver.findElementByAccessibilityId("Отдых").click();

        elementVisibilityWait(By.xpath("//XCUIElementTypeOther[@name=\"Отдых\"]"));
        Assert.assertEquals("Отдых", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Отдых\"]").getAttribute("name"));
        if (isElementPresent(By.id("Активности за сегодня нет"))) {
            logger.info("No stats");
        } else {
            logger.info("Stats not null");
        }

        driver.findElementByAccessibilityId(currentPetName).click();

        driver.findElementByAccessibilityId("Расход калорий").click();

        elementVisibilityWait(By.xpath("//XCUIElementTypeOther[@name=\"Расход калорий\"]"));
        Assert.assertEquals("Расход калорий", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Расход калорий\"]").getAttribute("name"));
        if (isElementPresent(By.id("Активности за сегодня нет"))) {
            logger.info("No stats");
        } else {
            logger.info("Stats not null");
        }

        driver.findElementByAccessibilityId(currentPetName).click();
    }
}