package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.TokenFilter;
import org.apache.tools.ant.taskdefs.condition.Contains;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.vars.vars.petname;

public class Main_screen extends Common {

    protected Logger logger;

    //??проверить
    public Main_screen(AppiumDriver<MobileElement> driver) {

        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    public void checkScreen(String device) {

        logger.info(device + ": Checking Main screen objects:");

        logger.info(device + ": - Top bar elements");
        driver.findElementByAccessibilityId(petname + " ").clear();

        driver.findElementsByPartialLinkText("%");

        //driver.findElementById("ru.averia.tracker:id/tv_date").clear(); - дата на главном экране
        driver.findElementsByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[2]/XCUIElementTypeButton[2]").clear();

        driver.findElementsByAccessibilityId("pets days next").clear();
        driver.findElementsByAccessibilityId("pets days next").clear();
        driver.findElementsByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]");

        logger.info(device + ": - Stats");
        driver.findElementByAccessibilityId("Шаг").click();
        driver.findElementsByXPath("//XCUIElementTypeOther[@name=\"Ходьба\"]").clear();
        driver.findElementByAccessibilityId(petname).click();
        driver.findElementByAccessibilityId("Бег").click();
        driver.findElementsByXPath("//XCUIElementTypeOther[@name=\"Бег и быстрый бег\"]").clear();
        driver.findElementByAccessibilityId(petname).click();
        driver.findElementByAccessibilityId("Отдых").click();
        driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Отдых\"]").clear();
        driver.findElementByAccessibilityId(petname).click();
        driver.findElementByAccessibilityId("Расход калорий").click();
        driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Расход калорий\"]").clear();
        driver.findElementByAccessibilityId(petname).click();

        logger.info(device + ": - Tracks and Achievements");
        swipeUp();
        //alternative
        // scrollUp("//XCUIElementTypeApplication[@name=\\\"Averia Collar\\\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]");
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]").click(); //Недавние прогулки
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").click();//первый трек
        driver.findElementByAccessibilityId("Все прогулки").click();
        driver.findElementByAccessibilityId(petname).click();
        //может потребоваться доп.скролл - не видит элемент
        driver.findElementByAccessibilityId("Добавлен ошейник").click();

        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[2]").clear();//картинка
        driver.findElementByAccessibilityId("addPet close btn").click();

    }

    public void walkStats(String device) {

        logger.info(device + ": Checking Main screen stats");
        swipeDown();
        driver.findElementByAccessibilityId("Шаг").click();
        sleep(5);
        Assert.assertEquals("Ходьба", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Ходьба\"]").getAttribute("name"));
        if (isElementPresent(By.id("stats_noActivity"))) {
            logger.error("Stats");
        } else {
            logger.error("No stats");
        }
        driver.findElementByAccessibilityId("Бег").click();
        sleep(5);
        Assert.assertEquals("Бег и быстрый бег", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Бег и быстрый бег\"]").getAttribute("name"));
        if (isElementPresent(By.id("stats_noActivity"))) {
            logger.error("Stats");
        } else {
            logger.error("No stats");
        }
        driver.findElementByAccessibilityId("Отдых").click();
        sleep(5);
        Assert.assertEquals("Отдых", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Отдых\"]").getAttribute("name"));
        if (isElementPresent(By.id("stats_noActivity"))) {
            logger.error("Stats");
        } else {
            logger.error("No stats");
        }
        driver.findElementByAccessibilityId("Расход калорий").click();
        sleep(5);
        Assert.assertEquals("Расход калорий", driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Расход калорий\"]").getAttribute("name"));
        if (isElementPresent(By.id("stats_noActivity"))) {
            logger.error("Stats");
        } else {
            logger.error("No stats");
        }
    }
}