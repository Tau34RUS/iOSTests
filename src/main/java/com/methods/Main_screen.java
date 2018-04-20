package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.TokenFilter;
import org.apache.tools.ant.taskdefs.condition.Contains;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.vars.vars.petname;

public class Main_screen extends Common {

    protected Logger logger;

    //??проверить
    public Main_screen(AppiumDriver<MobileElement> driver)  {

        super(driver);
        logger = Logger.getLogger("iOSTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    //всё проверить - нет таких локаторов
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

        logger.info(device + ": - Achievements");
        swipeUp();
        //alternative
        // scrollUp("//XCUIElementTypeApplication[@name=\\\"Averia Collar\\\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]");
        //driver.findElementById("ru.averia.tracker:id/container_goto_all_tracks").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[6]").click();
        //--first track --driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.view.ViewGroup[2]").click();
        driver.findElementByXPath("//XCUIElementTypeApplication[@name=\"Averia Collar\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTable/XCUIElementTypeCell[1]").click();
        //--return to previous screen?-- driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.ImageButton").click();
        driver.findElementByAccessibilityId("Все прогулки").click();
        //--return to previous screen?--driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.ImageButton").click();
        driver.findElementByAccessibilityId(petname).click();
        //--go to the first track from recent block-- driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.support.v4.view.ViewPager/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.support.v7.widget.RecyclerView/android.view.ViewGroup[1]/android.widget.ImageView").click();
        driver.findElementByXPath("(//XCUIElementTypeButton[@name=\"Подробнее\"])[2]").click();
        //--???--driver.findElementById("ru.averia.tracker:id/iv_img").clear();
        //--иконка - приложение отслеживает геопозицию над навбаром - нет в iOS--driver.findElementById("ru.averia.tracker:id/iv_back").click();

    }

}
